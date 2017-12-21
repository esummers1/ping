package gameObjects;

public class Ball extends GameObject {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	public Ball(double xPos, double yPos) {
		super(WIDTH, HEIGHT, xPos, yPos, 0, 0);
	}
}
