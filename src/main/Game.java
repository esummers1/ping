package main;

import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import gameObjects.Ball;
import gameObjects.Bat;
import gameObjects.Boundary;
import gameObjects.GameObject;
import gameObjects.Goal;

public class Game {
	
	private Display display;
	private Ball ball;
	private Bat batLeft;
	private Bat batRight;
	private Goal goalLeft;
	private Goal goalRight;
	private Boundary boundaryTop;
	private Boundary boundaryBottom;
	
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
	
	private int leftScore;
	private int rightScore;
	
	public Game() {
		
		display = new Display();
		
		ball = new Ball(BALL_INIT_X, BALL_INIT_Y);
		ball.setVelocity(3, 0);
		
		batLeft = new Bat(BAT_LEFT_INIT_X, BAT_LEFT_INIT_Y);
		batRight = new Bat(BAT_RIGHT_INIT_X, BAT_RIGHT_INIT_Y);
		
		goalLeft = new Goal(GOAL_LEFT_X);
		goalRight = new Goal(GOAL_RIGHT_X);
		
		boundaryTop = new Boundary(BOUNDARY_TOP_Y);
		boundaryBottom = new Boundary(BOUNDARY_BOTTOM_Y);
		
		leftScore = 0;
		rightScore = 0;
	}
	
	/**
	 * The game loop.
	 */
	public void run() {
		
		// introduce game
		
		//*** this somehow needs to happen once for each frame???
		while (true) {
			
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
			
		}
		
	}
	
	/**
	 * Sample player input for this game loop.
	 */
	private void sampleInput() {
	}
	
	
	private boolean[] calculatePhysics() {
		
		boolean leftScored = false;
		boolean rightScored = false;
		
		// impel bats with momentum based on inputs
		
		// Project bat movements
		Vertex[] batLeftProjectedPosition = projectPosition(batLeft);
		Vertex[] batRightProjectedPosition = projectPosition(batRight);
		
		// test for collisions with boundaries
		
		// decide on correct new positions and velocities (use them)
		
		
		// Project ball movement
		Vertex[] ballProjectedPosition = projectPosition(ball);
		
		// test for collisions (incl. score)
		
		// decide on correct new positions and velocities (use them)
		
		
		
		
		boolean[] whoScored = new boolean[]{leftScored, rightScored};
		return whoScored;
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
