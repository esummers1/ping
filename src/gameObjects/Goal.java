package gameObjects;

public class Goal extends GameObject {
	
	private static final double Y_POSITION = 0;
	private static final int X_SIZE = 0;
	private static final int Y_SIZE = 800;
	
	public Goal(double xPosition) {
		super(X_SIZE, Y_SIZE, xPosition, Y_POSITION, 0, 0);
	}
}
