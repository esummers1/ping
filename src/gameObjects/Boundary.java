package gameObjects;

public class Boundary extends GameObject {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 0;
	
	public Boundary(double yPos) {
		super(WIDTH, HEIGHT, 0, yPos, 0, 0);
	}
	
}
