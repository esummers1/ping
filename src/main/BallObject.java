package main;

public class BallObject extends GameObject {
	
	private static final int X_SIZE = 10;
	private static final int Y_SIZE = 10;
	
	public BallObject(double xPosition, 
			double yPosition, 
			double xVelocity, 
			double yVelocity) {
		
		super(X_SIZE, Y_SIZE, xPosition, yPosition, xVelocity, yVelocity);
	}
	
}
