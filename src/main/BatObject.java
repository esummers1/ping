package main;

public class BatObject extends GameObject {
	
	private static final int X_SIZE = 10;
	private static final int Y_SIZE = 60;
	
	public BatObject(double xPosition, 
			double yPosition, 
			double xVelocity, 
			double yVelocity) {
		
		super(X_SIZE, Y_SIZE, xPosition, yPosition, xVelocity, yVelocity);
	}

}
