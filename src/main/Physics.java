package main;

import java.awt.geom.Line2D;

import gameObjects.GameObject;

public abstract class Physics {
	
	/**
	 * Assign to an object's nextPosition fields its projected next position.
	 * @param gameObject
	 * @return
	 */
	public static void project(GameObject object) {
		object.setXNext(object.getXPos() + object.getXVel());
		object.setYNext(object.getYPos() + object.getYVel());
	}
	
	/**
	 * Return the location of the intersection of two Line2D objects as a Vertex
	 * (using y = mx + c notation within).
	 * @param line1
	 * @param line2
	 * @return
	 */
	public static Vertex locateIntercept(Line2D line1, Line2D line2) {
		
		// Find gradients
		double m1 = (line1.getY2() - line1.getY1()) /
				(line1.getX2() - line1.getX1());
		
		double m2 = (line2.getY2() - line2.getY1()) /
				(line2.getX2() - line2.getX1());
		
		// Approximate horizontal lines
		if (line1.getY2() - line1.getY1() == 0) {
			m1 = 0.0001;
		}
		
		if (line2.getY2() - line2.getY1() == 0) {
			m2 = 0.0001;
		}
		
		// Approximate vertical lines
		if (line1.getX2() - line1.getX1() == 0) {
			m1 = 10000;
		}
		
		if (line2.getX2() - line2.getX1() == 0) {
			m2 = 10000;
		}
		
		// Find y-intercepts
		double c1 = (line1.getY1() - m1 * line1.getX1());
		double c2 = (line2.getY1() - m2 * line1.getX1());
		
		// Calculate intercept coordinates
		double x = (c2 - c1) / (m1 - m2);
		double y = m1 * x + c1;
		
		Vertex intercept = new Vertex(x, y);
		return intercept;
	}
	
	/**
	 * Return an array of vertices describing an object's current or next
	 * location.
	 * @param gameObject
	 * @param current
	 * @return
	 */
	public static Vertex[] locate(GameObject object, boolean current) {
		
		double width = object.getWidth();
		double height = object.getHeight();
		double x;
		double y;
		
		if (current) {
			x = object.getXPos();
			y = object.getYPos();
		} else {
			x = object.getXNext();
			y = object.getYNext();
		}
		
		Vertex[] location = new Vertex[]{
				new Vertex(x, y),
				new Vertex(x + width, y),
				new Vertex(x + width, y + height),
				new Vertex(x, y + height)
		};
		
		return location;
	}
	
}
