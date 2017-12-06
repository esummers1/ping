package gameObjects;

public class Ball extends GameObject {
	
	private static final int X_SIZE = 10;
	private static final int Y_SIZE = 10;
	
	public Ball(double xPosition, double yPosition) {
		super(X_SIZE, Y_SIZE, xPosition, yPosition, 0, 0);
	}
	
}
