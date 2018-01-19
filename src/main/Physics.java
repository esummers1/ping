package main;

import java.awt.geom.Line2D;

import gameObjects.Entity;

public abstract class Physics {
	
	/**
	 * Assign to an object's nextPosition fields its projected next position.
	 * @param object
	 */
	public static void project(Entity object) {
		object.setXNext(object.getXPos() + object.getXVel());
		object.setYNext(object.getYPos() + object.getYVel());
	}
	
	/**
	 * Return an array of vertices describing an object's current location.
	 * @param object
	 * @return
	 */
	public static Vertex[] locate(Entity object) {
		
		double width = object.getWidth();
		double height = object.getHeight();
		double x;
		double y;
		
		x = object.getXPos();
		y = object.getYPos();
		
		Vertex[] location = new Vertex[]{
				new Vertex(x, y),
				new Vertex(x + width, y),
				new Vertex(x + width, y + height),
				new Vertex(x, y + height)
		};
		
		return location;
	}
	
	/**
	 * Test whether any of a given set of object vertex trajectories intersects
	 * any of a set of edges of another object.
	 * @param trajectories
	 * @param sides
	 * @return
	 */
	public static boolean detectCollision(Line2D[] trajectories, 
			Line2D[] sides) {
		
		for (Line2D trajectory : trajectories) {
			
			for (Line2D side : sides) {
				
				// Skip zero-length sides
				if (side.getX1() == side.getX2() && 
						side.getY1() == side.getY2()) {
					continue;
				}
				
				if (side.intersectsLine(trajectory)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
