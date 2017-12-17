package gameObjects;

public class Goal extends GameObject {
	
	private static final double y = 0;
	private static final int WIDTH = 0;
	private static final int HEIGHT = 800;
	
	public Goal(double x) {
		super(WIDTH, HEIGHT, x, y, 0, 0);
	}
}
