package main;

public class Game {
	
	private Display display;
	private BallObject ball;
	private BatObject batLeft;
	private BatObject batRight;
	
	private int leftScore;
	private int rightScore;
	
	public Game() {
		
		display = new Display();
		ball = new BallObject(395, 295, 3, 0);
		batLeft = new BatObject(20, 270, 0, 0);
		batRight = new BatObject(770, 270, 0, 0);
		
	}
	
	/**
	 * The game loop.
	 */
	public void run() {
		
		// introduce game
		
		while (true) {
			
			//** poll input
			
			//** calculate physics
			
			// impel bats with momentum based on inputs
			
			// Calculate bat movements			
			batLeft.setFutureVertices(projectVertices(batLeft));
			batRight.setFutureVertices(projectVertices(batRight));
			
			// test for collisions
			
			// decide on correct new positions and velocities
			
			
			// Calculate ball movement
			ball.setFutureVertices(projectVertices(ball));
			
			// test for collisions
			
			// decide on correct new positions and velocities
			
			
			//** handle point scored condition
			
			/** Render next frame
			 * 
			 * e.g. 
			 * display.renderBat(batLeft),
			 * display.renderBall(ball) etc.
			 */
			
		}
		
	}
	
	/**
	 * Give an array of vertices describing the new position of an object
	 * after calculating its movement without collision detection.
	 * @param object
	 * @return
	 */
	private Vertex[] projectVertices(GameObject object) {
		
		Vertex[] newVertices = new Vertex[4];
		
		for (int i = 0; i < 4; i++) {
			Vertex thisVertex = object.getCurrentVertices()[i];
			
			double newXCoordinate = thisVertex.getXCoordinate() + 
					object.getXVelocity();
			double newYCoordinate = thisVertex.getYCoordinate() + 
					object.getYVelocity();
			
			Vertex newVertex = new Vertex(newXCoordinate, newYCoordinate);
			newVertices[i] = newVertex;
		}
		
		return newVertices;
	}
	
	/**
	 * Updates the game score and resets the game objects for the next round.
	 * @param isLeft
	 */
	private void updateScore(boolean isLeft) {
	}

}
