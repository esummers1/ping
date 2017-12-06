package gameObjects;

public class Bat extends GameObject {
	
	private static final int X_SIZE = 10;
	private static final int Y_SIZE = 60;
	
	public Bat(double xPosition, double yPosition) {
		super(X_SIZE, Y_SIZE, xPosition, yPosition, 0, 0);
	}

}
