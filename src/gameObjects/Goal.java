package gameObjects;

public class Goal extends GameObject {
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 600;
	
	private boolean isLeft;
	
	public Goal(double xPos, boolean isLeft) {
		super(WIDTH, HEIGHT, xPos, 0, 0, 0);
	}
	
	public boolean isLeft() {
		return isLeft;
	}
}
