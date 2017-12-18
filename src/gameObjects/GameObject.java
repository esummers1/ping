package gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Vertex;

public class GameObject {
	
	private double width;
	private double height;
	
	private double x;
	private double y;
	
	private double xVel;
	private double yVel;
	
	public GameObject(double width,
			double height,
			double x,
			double y,
			double xVel,
			double yVel) {
		
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getXVel() {
		return xVel;
	}
	
	public double getYVel() {
		return yVel;
	}
	
	public void setVelocity(double xVel, double yVel) {
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Vertex vertex) {
		this.x = vertex.getX();
		this.y = vertex.getY();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) x, (int) y, (int) width, (int) height);
	}
	
	/**
	 * Return an array of Vertex objects describing the current corners of the
	 * game object.
	 * @return Vertex[]
	 */
	public Vertex[] getVertices() {
		
		Vertex[] vertices = new Vertex[]{
				new Vertex(x, y),
				new Vertex((x + width), y),
				new Vertex((x + width), (y + height)),
				new Vertex(x, (y + height))
		};
		
		return vertices;
	}
}
