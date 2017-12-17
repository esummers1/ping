package gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball extends GameObject {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	public Ball(double x, double y) {
		super(WIDTH, HEIGHT, x, y, 0, 0);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect((int) this.getX(), (int) this.getY(), WIDTH, HEIGHT);
	}
}
