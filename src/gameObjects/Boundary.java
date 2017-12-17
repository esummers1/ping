package gameObjects;

public class Boundary extends GameObject {
	
	private static final double X = 0;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 0;
	
	public Boundary(double y) {
		super(WIDTH, HEIGHT, X, y, 0, 0);
	}
}
