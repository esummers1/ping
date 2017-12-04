package main;

public class GameObject {
	
	private int xSize;
	private int ySize;
	
	private double xPosition;
	private double yPosition;
	
	private double xVelocity;
	private double yVelocity;
	
	private Vertex[] currentVertices;
	private Vertex[] futureVertices;
	
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
		
		updateVertices();
	}
	
	private void updateVertices() {
		currentVertices[0] = new Vertex(xPosition, yPosition);
		currentVertices[1] = new Vertex((xPosition + xSize), yPosition);
		currentVertices[2] = new Vertex((xPosition + xSize), 
				(yPosition + ySize));
		currentVertices[3] = new Vertex(xPosition, (yPosition + ySize));
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
	
	public Vertex[] getCurrentVertices() {
		return currentVertices;
	}
	
	public Vertex[] getFutureVertices() {
		return futureVertices;
	}
	
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}
	
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public void setCurrentVertices(Vertex[] newVertices) {
		this.currentVertices = newVertices;
	}
	
	public void setFutureVertices(Vertex[] newVertices) {
		this.futureVertices = newVertices;
	}
	
	public void move(double newXPosition, double newYPosition) {
		this.xPosition = newXPosition;
		this.yPosition = newYPosition;
	}
	
}
