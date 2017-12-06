package gameObjects;

import main.Vertex;

public class GameObject {
	
	private int xSize;
	private int ySize;
	
	private double xPosition;
	private double yPosition;
	
	private double xVelocity;
	private double yVelocity;
	
	public GameObject(int xSize,
			int ySize,
			double xPosition,
			double yPosition,
			double xVelocity,
			double yVelocity) {
		
		this.xSize = xSize;
		this.ySize = ySize;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
	}
	
	public double getXPosition() {
		return xPosition;
	}
	
	public double getYPosition() {
		return yPosition;
	}
	
	public double getXVelocity() {
		return xVelocity;
	}
	
	public double getYVelocity() {
		return yVelocity;
	}
	
	public void setVelocity(double xVelocity, double yVelocity) {
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}
	
	public void setPosition(double newXPosition, double newYPosition) {
		this.xPosition = newXPosition;
		this.yPosition = newYPosition;
	}
	
	/**
	 * Return an array of Vertex objects describing the current corners of the
	 * game object.
	 * @return Vertex[]
	 */
	public Vertex[] getVertices() {
		
		Vertex[] vertices = new Vertex[]{
				new Vertex(xPosition, yPosition),
				new Vertex((xPosition + xSize), yPosition),
				new Vertex((xPosition + xSize), (yPosition + ySize)),
				new Vertex(xPosition, (yPosition + ySize))
		};
		
		return vertices;
	}
}
