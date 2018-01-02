package main;

import java.util.List;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import gameObjects.Ball;
import gameObjects.Bat;
import gameObjects.Boundary;
import gameObjects.GameObject;
import gameObjects.Goal;

public class Game {
	
	private Display display;
	private Ball ball;
	private Bat batL;
	private Bat batR;
	private Boundary boundT;
	private Boundary boundB;
	private Goal goalL;
	private Goal goalR;
	
	private MyKeyListener listener;
	
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
	
	private static final double BAT_ACCELERATION = 0.08;
	private static final double BAT_FRICTION = 0.2;
	private static final double BAT_PROPULSION = 1.1;
	
	// Set game timing to 60 FPS
	private static final double TIME_STEP = (double) (1000 / 60);
	
	private int leftScore;
	private int rightScore;
	
	public Game() {
		
		display = new Display();
		
		ball = new Ball(BALL_INIT_X, BALL_INIT_Y);
		ball.setVel(2, -5);
		
		batL = new Bat(BAT_LEFT_X, BAT_LEFT_INIT_Y);
		batR = new Bat(BAT_RIGHT_X, BAT_RIGHT_INIT_Y);
		
		boundT = new Boundary(BOUNDARY_TOP_Y, true);
		boundB = new Boundary(BOUNDARY_BOTTOM_Y, false);
		goalL = new Goal(GOAL_LEFT_X, true);
		goalR = new Goal(GOAL_RIGHT_X, false);
		
		listener = new MyKeyListener(this);
		
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
	
	
	public void setCurrentKey(char c) {
		this.currentKey = c;
	}
	
	/**
	 * Sample player input for this game loop.
	 */
	private void sampleInput() {
		
		// Get rid of acceleration input from last loop
		batL.resetAcc();
		batR.resetAcc();
		
		// Detect key input - ?? need to test this
		if (currentKey == 'a') {
			batL.setAccUp();
		}
		
		if (currentKey == 'z') {
			batL.setAccDown();
		}
		
		if (currentKey == 'k') {
			batR.setAccUp();
		}
		
		if (currentKey == 'm') {
			batR.setAccDown();
		}
	}
	
	/**
	 * Calculate the movements of the mobile game objects.
	 */
	private void calculatePhysics() {
		
		// Impel bats with acceleration from inputs
		accelerateBat(batL);
		accelerateBat(batR);
		
		// Project bat movements
		project(batL);
		project(batR);
		
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
		project(ball);
		
		// Test for collisions
		List<GameObject> ballTest = new ArrayList<>();
		ballTest.add(boundB);
		ballTest.add(boundT);
		ballTest.add(batL);
		ballTest.add(batR);
		ballTest.add(goalL);
		ballTest.add(goalR);
		
		handleCollisions(ballTest, ball);
		
		// Assign new positions to ball object
		ball.updatePos();		
	}
	
	/**
	 * Apply input acceleration to bat object.
	 * @param bat
	 */
	private void accelerateBat(Bat bat) {
		
		double yVel = bat.getYVel();
		
		if (bat.isAccDown()) {
			bat.setYVel(yVel + BAT_ACCELERATION);
		}
		
		if (bat.isAccUp()) {
			bat.setYVel(yVel - BAT_ACCELERATION);
		}
	}
	
	/**
	 * Return an array of vertices describing an object's current or next
	 * location.
	 * @param gameObject
	 * @param current
	 * @return
	 */
	private Vertex[] locate(GameObject object, boolean current) {
		
		double width = object.getWidth();
		double height = object.getHeight();
		double x;
		double y;
		
		if (current) {
			x = object.getXPos();
			y = object.getYPos();
		} else {
			x = object.getXNext();
			y = object.getYNext();
		}
		
		Vertex[] location = new Vertex[]{
				new Vertex(x, y),
				new Vertex(x + width, y),
				new Vertex(x + width, y + height),
				new Vertex(x, y + height)
		};
		
		return location;
	}
	
	/**
	 * Assign to an object's nextPosition fields its projected next position.
	 * @param gameObject
	 * @return
	 */
	private void project(GameObject object) {
		object.setXNext(object.getXPos() + object.getXVel());
		object.setYNext(object.getYPos() + object.getYVel());
	}
	
	/**
	 * Return the location of the intersection of two Line2D objects as a Vertex
	 * (using y = mx + c notation within).
	 * @param line1
	 * @param line2
	 * @return
	 */
	private Vertex locateIntercept(Line2D line1, Line2D line2) {
		
		// Find gradients
		double m1 = (line1.getY2() - line1.getY1()) /
				(line1.getX2() - line1.getX1());
		
		double m2 = (line2.getY2() - line2.getY1()) /
				(line2.getX2() - line2.getX1());
		
		// Approximate horizontal lines
		if (line1.getY2() - line1.getY1() == 0) {
			m1 = 0.000001;
		}
		
		if (line2.getY2() - line2.getY1() == 0) {
			m2 = 0.000001;
		}
		
		// Approximate vertical lines
		if (line1.getX2() - line1.getX1() == 0) {
			m1 = 1000000;
		}
		
		if (line2.getX2() - line2.getX1() == 0) {
			m2 = 1000000;
		}
		
		// Find y-intercepts
		double c1 = (line1.getY1() - m1 * line1.getX1());
		double c2 = (line2.getY1() - m2 * line1.getX1());
		
		// Calculate intercept coordinates
		double x = (c2 - c1) / (m1 - m2);
		double y = m1 * x + c1;
		
		Vertex intercept = new Vertex(x, y);
		return intercept;
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
		
		Vertex[] current = locate(object, true);
		Vertex[] next = locate(object, false);
		
		boolean collides = false;
		
		// For all gameObjects, i.e. those against which we are testing
		for (GameObject gameObject : gameObjects) {
			
			Vertex[] target = locate(gameObject, true);
			
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
					
					System.out.println("GOOALLL");
					
					if (((Goal) gameObject).isLeft()) {
						leftScored = true;
					} else {
						rightScored = true;
					}
					
					return;
				}
				
				// Apply friction acceleration if gameObject is bat
				if (gameObject instanceof Bat) {
					object.setYVel(object.getYVel() + 
							BAT_FRICTION * gameObject.getYVel());
				}
				
				// Apply propulsive acceleration if gameObject is bat
				if (gameObject instanceof Bat) {
					object.setXVel(object.getXVel() * BAT_PROPULSION);
				}
				
				// Map the shape of the object doing the colliding
				Line2D[] colliderSides = new Line2D[4];
				
				// Detect direction of travel / leading edge for colliding
				boolean travellingLeft = false;
				boolean travellingRight = false;
				boolean travellingUp = false;
				boolean travellingDown = false;
				
				if (Math.abs(object.getYVel()) >= Math.abs(object.getXVel())) {
					if (object.getYVel() >= 0) {
						travellingUp = true;
					} else {
						travellingDown = true;
					}
				} else {
					if (object.getXVel() >= 0) {
						travellingRight = true;
					} else {
						travellingLeft = true;
					}
				}
				
				/*
				 * Locate intersection point, update position of object, reflect
				 * velocity as appropriate.
				 */
				if (travellingUp) {
					
					if (object.getXVel() >= 0) {
						
						Vertex intercept = locateIntercept(
								trajectories[1], targetSides[2]);
						
						object.setPosition(
								intercept.getX() - object.getWidth(), 
								intercept.getY());
						
					} else {
						
						Vertex intercept = locateIntercept(
								trajectories[0], targetSides[2]);
						
						object.setPosition(intercept.getX(), intercept.getY());
						
					}
					
					object.setYVel(object.getYVel() * -1);
					
				} else if (travellingDown) {
					
					if (object.getXVel() >= 0) {
						
						Vertex intercept = locateIntercept(
								trajectories[2], targetSides[0]);
						
						object.setPosition(
								intercept.getX() - object.getWidth(), 
								intercept.getY() - object.getHeight());
						
					} else {
						
						Vertex intercept = locateIntercept(
								trajectories[3], targetSides[0]);
						
						object.setPosition(intercept.getX(), 
								intercept.getY() - object.getHeight());
						
					}
					
					object.setYVel(object.getYVel() * -1);
					
				} else if (travellingLeft) {
					
					if (object.getYVel() >= 0) {
						
						Vertex intercept = locateIntercept(
								trajectories[3], targetSides[1]);
						
						object.setPosition(intercept.getX(), 
								intercept.getY() - object.getHeight());
						
					} else {
						
						Vertex intercept = locateIntercept(
								trajectories[0], targetSides[1]);
						
						object.setPosition(intercept.getX(), intercept.getY());
						
					}
					
					object.setXVel(object.getXVel() * -1);
					
				} else if (travellingRight) {
					
					if (object.getYVel() >= 0) {
						
						Vertex intercept = locateIntercept(
								trajectories[2], targetSides[3]);
						
						object.setPosition(
								intercept.getX() - object.getWidth(),
								intercept.getY() - object.getHeight());
						
					} else {
						
						Vertex intercept = locateIntercept(
								trajectories[1], targetSides[3]);
						
						object.setPosition(
								intercept.getX() - object.getWidth(),
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
}
