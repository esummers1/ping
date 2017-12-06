package main;

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
	
	private static final double BALL_START_X_COORD = 395;
	private static final double BALL_START_Y_COORD = 295;
	
	private static final double BAT_LEFT_START_X_COORD = 20;
	private static final double BAT_LEFT_START_Y_COORD = 270;
	
	private static final double BAT_RIGHT_START_X_COORD = 770;
	private static final double BAT_RIGHT_START_Y_COORD = 270;
	
	private static final double GOAL_LEFT_X_COORD = 0;
	private static final double GOAL_RIGHT_X_COORD = 800;
	
	private static final double BOUNDARY_TOP_Y_COORD = 0;
	private static final double BOUNDARY_BOTTOM_Y_COORD = 600;
	
	private int leftScore;
	private int rightScore;
	
	public Game() {
		
		display = new Display();
		
		ball = new Ball(BALL_START_X_COORD, BALL_START_Y_COORD);
		ball.setVelocity(3, 0);
		
		batLeft = new Bat(BAT_LEFT_START_X_COORD, BAT_LEFT_START_Y_COORD);
		batRight = new Bat(BAT_RIGHT_START_X_COORD, BAT_RIGHT_START_Y_COORD);
		
		goalLeft = new Goal(GOAL_LEFT_X_COORD);
		goalRight = new Goal(GOAL_RIGHT_X_COORD);
		
		boundaryTop = new Boundary(BOUNDARY_TOP_Y_COORD);
		boundaryBottom = new Boundary(BOUNDARY_BOTTOM_Y_COORD);
		
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
			
			//** poll input
			
			//** calculate physics
			
			// impel bats with momentum based on inputs
			
			// Calculate bat movements		
			Vertex[] batLeftProjectedPosition = projectPosition(batLeft);
			Vertex[] batRightProjectedPosition = projectPosition(batRight);
			
			// test for collisions
			
			// decide on correct new positions and velocities
			
			
			// Calculate ball movement
			Vertex[] ballProjectedPosition = projectPosition(ball);
			
			// test for collisions
			
			// decide on correct new positions and velocities
			
			
			//** handle point scored condition
			
			// update these with actual goal detection
			boolean leftGoal = false;
			boolean rightGoal = false;
			
			if (leftGoal) {
				updateScore(true);
			}
			
			if (rightGoal) {
				updateScore(false);
			}
			
			/** Render next frame
			 * 
			 * e.g. 
			 * display.renderBat(batLeft),
			 * display.renderBall(ball) etc.
			 */
			
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
			
			double xVelocity = gameObject.getXVelocity();
			double yVelocity = gameObject.getYVelocity();
			Vertex currentVertex = currentVertices[i];
			
			Vertex newVertex = new Vertex(
					(currentVertex.getXCoordinate() + xVelocity),
					(currentVertex.getYCoordinate() + yVelocity));
			
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
		
		ball.setPosition(BALL_START_X_COORD, BALL_START_Y_COORD);
		batLeft.setPosition(
				BAT_LEFT_START_X_COORD, BAT_LEFT_START_Y_COORD);
		batRight.setPosition(
				BAT_RIGHT_START_X_COORD, BAT_RIGHT_START_Y_COORD);
		
		// Update score, set ball moving away from side which just scored
		if (isLeft) {
			leftScore++;
			ball.setVelocity(3, 0);
		} else {
			rightScore++;
			ball.setVelocity(-3, 0);
		}
	}
}
