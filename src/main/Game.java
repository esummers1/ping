package main;

public class Game {
	
	Display display;
	BallObject ball;
	BatObject batLeft;
	BatObject batRight;
	
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
			
			// poll input
			
			// calculate physics
			
			// handle point scored condition
			
			// render next frame
			
		}
		
	}

}
