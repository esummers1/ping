package gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity {
	
	private double width;
	private double height;
	
	private double xPos;
	private double yPos;
	
	private double xNext;
	private double yNext;
	
	private double xVel;
	private double yVel;
	
	public Entity(double width,
			double height,
			double xPos,
			double yPos,
			double xVel,
			double yVel) {
		
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public double getXNext() {
		return xNext;
	}
	
	public double getYNext() {
		return yNext;
	}

	public double getXVel() {
		return xVel;
	}
	
	public double getYVel() {
		return yVel;
	}
	
	public void setVel(double xVel, double yVel) {
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public void setYVel(double yVel) {
		this.yVel = yVel;
	}
	
	public void setXVel(double xVel) {
		this.xVel = xVel;
	}
	
	public void updatePos() {
		xPos = xNext;
		yPos = yNext;
	}
	
	public void setXNext(double xNext) {
		this.xNext = xNext;
	}
	
	public void setYNext(double yNext) {
		this.yNext = yNext;
	}
	
	public void setPosition(double xNext, double yNext) {
		this.xNext = xNext;
		this.yNext = yNext;
	}
	
	public boolean canCollideWith(Entity object) {
		return true;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) xPos, (int) yPos, (int) width, (int) height);
	}
}
