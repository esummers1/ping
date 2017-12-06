package gameObjects;

public class Boundary extends GameObject {
	
	private static final double X_POSITION = 0;
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 0;
	
	public Boundary(double yPosition) {
		super(X_SIZE, Y_SIZE, X_POSITION, yPosition, 0, 0);
	}
}
