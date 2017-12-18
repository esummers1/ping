package main;

import java.util.List;
import java.util.ArrayList;

import gameObjects.Ball;
import gameObjects.Bat;
import gameObjects.GameObject;

public class Game {
	
	private Display display;
	private Ball ball;
	private Bat batLeft;
	private Bat batRight;
	
	private static final double BALL_INIT_X = 395;
	private static final double BALL_INIT_Y = 295;
	
	private static final double BAT_LEFT_INIT_X = 20;
	private static final double BAT_LEFT_INIT_Y = 270;
	
	private static final double BAT_RIGHT_INIT_X = 770;
	private static final double BAT_RIGHT_INIT_Y = 270;
	
	private static final double GOAL_LEFT_X = 0;
	private static final double GOAL_RIGHT_X = 800;
	
	private static final double BOUNDARY_TOP_Y = 0;
	private static final double BOUNDARY_BOTTOM_Y = 600;
	
	private static final double BAT_ACCELERATION = 0.1;
	
	// Set game timing to 60 FPS
	private static final double TIME_STEP = (double) (1000 / 60);
	
	private int leftScore;
	private int rightScore;
	
	public Game() {
		
		display = new Display();
		
		ball = new Ball(BALL_INIT_X, BALL_INIT_Y);
		ball.setVelocity(1, -5);
		
		batLeft = new Bat(BAT_LEFT_INIT_X, BAT_LEFT_INIT_Y);
		batRight = new Bat(BAT_RIGHT_INIT_X, BAT_RIGHT_INIT_Y);
		
		leftScore = 0;
		rightScore = 0;
	}
	
	/**
	 * The game loop.
	 */
	public void run() {
		
		//*** this somehow needs to happen once for each frame???
		while (true) {
			
			long startTime = System.currentTimeMillis();
			
			sampleInput();
			
			// Calculate motion of objects and return scoring if necessary
			boolean[] whoScored = calculatePhysics();
			
			if (whoScored[0]) {
				updateScore(true);
			}
			
			if (whoScored[1]) {
				updateScore(false);
			}
			
			render();
			
			long endTime = System.currentTimeMillis();
			long deltaTime = endTime - startTime;
			
			// Wait until full time step has elapsed
			try {
				Thread.sleep((long) (TIME_STEP - deltaTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Sample player input for this game loop.
	 */
	private void sampleInput() {
		
		// Get rid of acceleration input from last loop
		batLeft.resetAcceleration();
		batRight.resetAcceleration();
		
		// @TODO
		
		// test purposes
		batLeft.setAcceleratingUp();
		batRight.setAcceleratingDown();
	}
	
	
	private boolean[] calculatePhysics() {
		
		boolean leftScored = false;
		boolean rightScored = false;
		
		// Impel bats with acceleration from inputs
		accelerateBat(batLeft);
		accelerateBat(batRight);
		
		// Project bat movements
		Vertex[] batLeftProjectedPosition = projectPosition(batLeft);
		Vertex[] batRightProjectedPosition = projectPosition(batRight);
		
		// Test for boundary collisions
		Vertex[] batLeftNextPosition = handleCollisions(batLeft, 
				batLeftProjectedPosition);
		Vertex[] batRightNextPosition = handleCollisions(batRight, 
				batRightProjectedPosition);
		
		// Assign new positions to bat objects
		batLeft.setPosition(batLeftNextPosition[0]);
		batRight.setPosition(batRightNextPosition[0]);
		
		// Project ball movement
		Vertex[] ballProjectedPosition = projectPosition(ball);
		
		// Test for collisions
		Vertex[] ballNextPosition = handleCollisions(ball, 
				ballProjectedPosition);
		
		// Assign new positions to ball object
		ball.setPosition(ballNextPosition[0]);
		
		// @TODO: this needs to get passed out somehow
		boolean[] whoScored = new boolean[]{leftScored, rightScored};
		return whoScored;
	}
	
	/**
	 * Apply input acceleration to bat object.
	 * @param bat
	 */
	private void accelerateBat(Bat bat) {
		
		double yVel = bat.getYVel();
		
		if (bat.isAcceleratingDown()) {
			bat.setVelocity(0, yVel + BAT_ACCELERATION);
		}
		
		if (bat.isAcceleratingUp()) {
			bat.setVelocity(0, yVel - BAT_ACCELERATION);
		}
	}
	
	/**
	 * Give an array of vertices describing the projected position of an object
	 * after calculating its movement without collision detection.
	 * @param gameObject
	 * @return
	 */
	private Vertex[] projectPosition(GameObject gameObject) {
		
		Vertex[] currentVertices = gameObject.getVertices();
		Vertex[] newVertices = new Vertex[4];
		
		for (int i = 0; i < 4; i++) {
			
			Vertex newVertex = new Vertex(
					(currentVertices[i].getX() + gameObject.getXVel()),
					(currentVertices[i].getY() + gameObject.getYVel()));
			
			newVertices[i] = newVertex;
		}
		
		return newVertices;
	}
	
	/**
	 * Return an array of vertices describing the position of an object after
	 * collisions have been computed.
	 * @param gameObject
	 * @param vertices
	 * @return
	 */
	private Vertex[] handleCollisions(GameObject gameObject, 
			Vertex[] vertices) {
		
		// Create working array (for multiple evaluation)
		Vertex[] nextPosition = vertices;
		
		// Test for top boundary collision
		if (nextPosition[0].getY() <= BOUNDARY_TOP_Y) {
			double penetrationDepth = BOUNDARY_TOP_Y - nextPosition[0].getY();
			
			nextPosition[0] = new Vertex(nextPosition[0].getX(),
					BOUNDARY_TOP_Y + penetrationDepth);
			
			nextPosition[1] = new Vertex(nextPosition[0].getX() + 
					gameObject.getWidth(), nextPosition[0].getY());
			
			nextPosition[2] = new Vertex(nextPosition[1].getX(),
					nextPosition[1].getY() + gameObject.getHeight());
			
			nextPosition[3] = new Vertex(nextPosition[0].getX(),
					nextPosition[2].getY());
			
			// Reverse object vertical velocity
			gameObject.setVelocity(gameObject.getXVel(), 
					(-1) * gameObject.getYVel());
		}
		
		// Test for bottom boundary collision
		if (nextPosition[2].getY() >= BOUNDARY_BOTTOM_Y) {
			double penetrationDepth = nextPosition[2].getY() - 
					BOUNDARY_BOTTOM_Y;
			
			nextPosition[2] = new Vertex(nextPosition[2].getX(),
					BOUNDARY_BOTTOM_Y - penetrationDepth);
			
			nextPosition[3] = new Vertex(nextPosition[2].getX() - 
					gameObject.getWidth(), nextPosition[2].getY());
			
			nextPosition[0] = new Vertex(nextPosition[3].getX(),
					nextPosition[3].getY() + gameObject.getWidth());
			
			nextPosition[1] = new Vertex(nextPosition[2].getX(),
					nextPosition[0].getY());
			
			// Reverse object vertical velocity
			gameObject.setVelocity(gameObject.getXVel(), 
					(-1) * gameObject.getYVel());
		}
		
		if (gameObject instanceof Ball) {
			
			// Do the other checks, i.e. goal collision and bat collision
			
		}
		
		return nextPosition;
	}
	
	/**
	 * Update the game score and reset the game objects for the next round.
	 * @param isLeft
	 */
	private void updateScore(boolean isLeft) {
		
		batLeft.setVelocity(0, 0);
		batRight.setVelocity(0, 0);
		
		ball.setPosition(BALL_INIT_X, BALL_INIT_Y);
		batLeft.setPosition(BAT_LEFT_INIT_X, BAT_LEFT_INIT_Y);
		batRight.setPosition(BAT_RIGHT_INIT_X, BAT_RIGHT_INIT_Y);
		
		// Update score, set ball moving away from side which just scored
		if (isLeft) {
			leftScore++;
			ball.setVelocity(3, 0);
		} else {
			rightScore++;
			ball.setVelocity(-3, 0);
		}
		
		// Update title to reflect score
		display.getFrame().setTitle("Ping | " + leftScore + " - " + rightScore);
	}
	
	/**
	 * Update display to show new positions of game objects.
	 */
	private void render() {
		List<GameObject> objectsToDraw = new ArrayList<>();
		
		objectsToDraw.add(batLeft);
		objectsToDraw.add(batRight);
		objectsToDraw.add(ball);
		
		display.getPanel().setObjects(objectsToDraw);
		display.getPanel().repaint();
	}
}
