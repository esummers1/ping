package main;

public interface GameObject {
	
	public int getXSize();
	public int getYSize();
	
	public double getXPosition();
	public double getYPosition();
	
	public double getXVelocity();
	public double getYVelocity();
	
	public void setXPosition(double xPosition);
	public void setYPosition(double yPosition);
	
	public void setXVelocity(double xVelocity);
	public void setYVelocity(double yVelocity);
	
}
