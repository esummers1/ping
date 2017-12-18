package gameObjects;

public class Ball extends GameObject {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	public Ball(double x, double y) {
		super(WIDTH, HEIGHT, x, y, 0, 0);
	}
}
