package main;

public class BallObject implements GameObject {
	
	private static final int X_SIZE = 10;
	private static final int Y_SIZE = 10;
	
	private double xPosition;
	private double yPosition;
	
	private double xVelocity;
	private double yVelocity;
	
	public BallObject(double xPosition, 
			double yPosition, 
			double xVelocity, 
			double yVelocity) {
		
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		
	}
	
	@Override
	public int getXSize() {
		return X_SIZE;
	}
	
	@Override
	public int getYSize() {
		return Y_SIZE;
	}
	
	@Override
	public double getXPosition() {
		return xPosition;
	}
	
	@Override
	public double getYPosition() {
		return yPosition;
	}
	
	@Override
	public double getXVelocity() {
		return xVelocity;
	}

	@Override
	public double getYVelocity() {
		return yVelocity;
	}
	
	@Override
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	@Override
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
}
