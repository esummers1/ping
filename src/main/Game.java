package main;

import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import gameObjects.Ball;
import gameObjects.Bat;
import gameObjects.Boundary;
import gameObjects.GameObject;
import gameObjects.Goal;

public class Game implements KeyListener {
	
	private Display display;
	private Ball ball;
	private Bat batL;
	private Bat batR;
	private Boundary boundT;
	private Boundary boundB;
	private Goal goalL;
	private Goal goalR;
	
	private char currentKey;
	
	private boolean leftScored;
	private boolean rightScored;
	
	private static final double BALL_INIT_X = 395;
	private static final double BALL_INIT_Y = 295;
	
	private static final double BAT_LEFT_X = 20;
	private static final double BAT_LEFT_INIT_Y = 270;
	
	private static final double BAT_RIGHT_X = 770;
	private static final double BAT_RIGHT_INIT_Y = 270;
	
	private static final double GOAL_LEFT_X = 0;
	private static final double GOAL_RIGHT_X = 800;
	
	private static final double BOUNDARY_TOP_Y = 0;
	private static final double BOUNDARY_BOTTOM_Y = 600;
	
	private static final double BAT_FRICTION = 0.35;
	private static final double BAT_PROPULSION = 1.1;
	
	private static final double BAT_BRAKING_RATIO = 0.985;
	private static final double BAT_ACCELERATION = 0.2;
	
	private static final double PIXEL = 1;
	
	// Set game timing to 60 FPS
	private static final double TIME_STEP = (double) (1000 / 60);
	
	private int leftScore;
	private int rightScore;
	
	public Game() {
		
		display = new Display(this);
		
		ball = new Ball(BALL_INIT_X, BALL_INIT_Y);
		ball.setVel(-3, 1);
		
		batL = new Bat(BAT_LEFT_X, BAT_LEFT_INIT_Y);
		batR = new Bat(BAT_RIGHT_X, BAT_RIGHT_INIT_Y);
		
		boundT = new Boundary(BOUNDARY_TOP_Y, true);
		boundB = new Boundary(BOUNDARY_BOTTOM_Y, false);
		goalL = new Goal(GOAL_LEFT_X, true);
		goalR = new Goal(GOAL_RIGHT_X, false);
		
		leftScore = 0;
		rightScore = 0;
	}
	
	/**
	 * The game loop.
	 */
	public void run() {
		
		while (true) {
			
			long startTime = System.currentTimeMillis();
			leftScored = false;
			rightScored = false;
			
			sampleInput();
			
			// Calculate motion of objects and return scoring if necessary
			calculatePhysics();
			
			if (leftScored) {
				updateScore(true);
			}
			
			if (rightScored) {
				updateScore(false);
			}
			
			render();
			
			// Regulate time step
			long endTime = System.currentTimeMillis();
			long deltaTime = endTime - startTime;
			
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
		batL.resetAcc();
		
		// Detect key input - ?? need to test this
		if (currentKey == 'w') {
			batL.setAccUp();
		}
		
		if (currentKey == 's') {
			batL.setAccDown();
		}
	}
	
	/**
	 * Calculate the movements of the mobile game objects.
	 */
	private void calculatePhysics() {
		
		// Calculate AI intent
		decideAIAction();
		
		// Impel bats with acceleration from inputs
		accelerateBat(batL);
		accelerateBat(batR);
		
		// Project bat movements
		Physics.project(batL);
		Physics.project(batR);
		
		// Test for boundary collisions
		List<GameObject> batTest = new ArrayList<>();
		batTest.add(boundT);
		batTest.add(boundB);
		
		handleCollisions(batTest, batL);
		handleCollisions(batTest, batR);
		
		// Assign new positions to bat objects
		batL.updatePos();
		batR.updatePos();
		
		// Project ball movement
		Physics.project(ball);
		
		// Test for collisions
		List<GameObject> ballTest = new ArrayList<>();
		ballTest.add(boundB);
		ballTest.add(boundT);
		ballTest.add(batL);
		ballTest.add(batR);
		ballTest.add(goalL);
		ballTest.add(goalR);
		
		System.out.println("Calculating collisions");
		handleCollisions(ballTest, ball);
		System.out.println("V: " + ball.getXVel() + ", " + ball.getYVel());
		System.out.println("X: " + ball.getXPos() + "->" + ball.getXNext());
		System.out.println("Y: " + ball.getYPos() + "->" + ball.getYNext());
		
		// Assign new positions to ball object
		ball.updatePos();		
	}
	
	/**
	 * Decide what the AI bat will do this step.
	 */
	private void decideAIAction() {
		
		// Get rid of acceleration from last round
		batR.resetAcc();
		
		double ballCentreY = ball.getYPos() + ball.getHeight() / 2;
		double batCentreY = batR.getYPos() + batR.getHeight() / 2;
		
		if (ballCentreY >= batCentreY) {
			batR.setAccDown();
		} else {
			batR.setAccUp();
		}
	}
	
	/**
	 * Apply input acceleration to Bat object.
	 * @param bat
	 */
	private void accelerateBat(Bat bat) {
		
		double yVel = bat.getYVel();
		
		if (bat.isAccDown()) {
			bat.setYVel(yVel + BAT_ACCELERATION);
		} else if (bat.isAccUp()) {
			bat.setYVel(yVel - BAT_ACCELERATION);
		} else {
			// Slow bat down if not accelerating
			bat.setYVel(yVel * BAT_BRAKING_RATIO);
		}
	}
	
	/**
	 * Test whether a given object collides with any of the objects in a list
	 * of objects during its movement to its projected next position; if so,
	 * alter its next position to simulate bouncing.
	 * @param gameObjects
	 * @param object
	 */
	private void handleCollisions(List<GameObject> gameObjects, 
			GameObject object) {
		
		Vertex[] current = Physics.locate(object, true);
		Vertex[] next = Physics.locate(object, false);
		
		boolean collides = false;
		
		// For all gameObjects, i.e. those against which we are testing
		for (GameObject gameObject : gameObjects) {
			
			Vertex[] target = Physics.locate(gameObject, true);
			
			// Map the path of the object being tested
			Line2D[] trajectories = new Line2D[4];
			
			for (int i = 0; i < 4; i++) {
				trajectories[i] = new Line2D.Double(
						current[i].getX(),
						current[i].getY(),
						next[i].getX(),
						next[i].getY());
			}
			
			// Map the shape of the gameObject being tested against
			Line2D[] targetSides = new Line2D[4];
			int[] corners = new int[]{1, 2, 3, 0};
			
			for (int i = 0; i < 4; i++) {
				int j = corners[i];
				
				targetSides[i] = new Line2D.Double(
						target[i].getX(), target[i].getY(), 
						target[j].getX(), target[j].getY());
			}
			
			// Test intersection
			here:
			
			for (Line2D trajectory : trajectories) {
				for (Line2D side : targetSides) {
					
					// Skip zero-length lines
					if (side.getX1() == side.getX2() &&
							side.getY1() == side.getY2()) {
						continue;
					}
					
					collides = side.intersectsLine(trajectory);
					
					if (collides) {
						break here;
					}
				}
			}
			
			if (collides) {
				
				// Ball hitting goal
				if (gameObject instanceof Goal) {
					
					if (((Goal) gameObject).isLeft()) {
						leftScored = true;
					} else {
						rightScored = true;
					}
					
					return;
				}
				
				// Apply frictional and propulsive accelerations if hitting bat
				if (gameObject instanceof Bat) {
					object.setXVel(object.getXVel() * BAT_PROPULSION);
					object.setYVel(object.getYVel() + 
							BAT_FRICTION * gameObject.getYVel());
				}
				
				// Detect main direction of travel
				boolean goingLeft = false;
				boolean goingRight = false;
				boolean goingUp = false;
				boolean goingDown = false;
				
				if (Math.abs(object.getYVel()) >= Math.abs(object.getXVel())) {
					if (object.getYVel() >= 0) {
						goingUp = true;
					} else {
						goingDown = true;
					}
				} else {
					if (object.getXVel() >= 0) {
						goingRight = true;
					} else {
						goingLeft = true;
					}
				}
				
				/*
				 * Locate intersection point, update position of object, reflect
				 * velocity as appropriate.
				 * 
				 * Handled using leading corner (e.g. moving up and right -> use
				 * top right corner).
				 * 
				 * N.B. PIXEL constant is used so that beginning of object
				 * trajectory in next game step is not found to already
				 * intersect with the gameObject it collided with last step.
				 */
				if (goingUp) {
					
					if (object.getXVel() >= 0) {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[1], targetSides[2]);
						
						object.setPosition(
								intercept.getX() - object.getWidth(), 
								intercept.getY() + PIXEL);
						
					} else {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[0], targetSides[2]);
						
						object.setPosition(intercept.getX(), 
								intercept.getY() + PIXEL);
						
					}
					
					object.setYVel(object.getYVel() * -1);
					
				} else if (goingDown) {
					
					if (object.getXVel() >= 0) {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[2], targetSides[0]);
						
						object.setPosition(
								intercept.getX() - object.getWidth(), 
								intercept.getY() - object.getHeight() - PIXEL);
						
					} else {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[3], targetSides[0]);
						
						object.setPosition(intercept.getX(), 
								intercept.getY() - object.getHeight() - PIXEL);
						
					}
					
					object.setYVel(object.getYVel() * -1);
					
				} else if (goingLeft) {
					
					if (object.getYVel() >= 0) {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[3], targetSides[1]);
						
						object.setPosition(intercept.getX() + PIXEL, 
								intercept.getY() - object.getHeight());
						
					} else {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[0], targetSides[1]);
						
						object.setPosition(intercept.getX() + PIXEL, 
								intercept.getY());
						
					}
					
					object.setXVel(object.getXVel() * -1);
					
				} else if (goingRight) {
					
					if (object.getYVel() >= 0) {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[2], targetSides[3]);
						
						object.setPosition(
								intercept.getX() - object.getWidth() - PIXEL,
								intercept.getY() - object.getHeight());
						
					} else {
						
						Vertex intercept = Physics.locateIntercept(
								trajectories[1], targetSides[3]);
						
						object.setPosition(
								intercept.getX() - object.getWidth() - PIXEL,
								intercept.getY());
						
					}
					
					object.setXVel(object.getXVel() * -1);
					
				}
			}
		}
	}
	
	/**
	 * Update the game score and reset the game objects for the next round.
	 * @param isLeft
	 */
	private void updateScore(boolean isLeft) {
		
		batL.setVel(0, 0);
		batR.setVel(0, 0);
		
		ball.setPosition(BALL_INIT_X, BALL_INIT_Y);
		ball.updatePos();
		
		batL.setPosition(BAT_LEFT_X, BAT_LEFT_INIT_Y);
		batL.updatePos();
		
		batR.setPosition(BAT_RIGHT_X, BAT_RIGHT_INIT_Y);
		batR.updatePos();
		
		// Update score, set ball moving away from side which just scored
		if (isLeft) {
			leftScore++;
			ball.setVel(3, 0);
		} else {
			rightScore++;
			ball.setVel(-3, 0);
		}
		
		// Update title to reflect score
		display.getFrame().setTitle("Ping | " + leftScore + " - " + rightScore);
	}
	
	/**
	 * Update display to show new positions of game objects.
	 */
	private void render() {
		List<GameObject> objectsToDraw = new ArrayList<>();
		
		objectsToDraw.add(batL);
		objectsToDraw.add(batR);
		objectsToDraw.add(ball);
		
		display.getPanel().setObjects(objectsToDraw);
		display.getPanel().repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		currentKey = e.getKeyChar();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentKey = '?';
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
