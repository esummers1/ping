package gameObjects;

public class Boundary extends GameObject {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 0;
	
	private boolean isTop;
	
	public Boundary(double yPos, boolean isTop) {
		super(WIDTH, HEIGHT, 0, yPos, 0, 0);
		this.isTop = isTop;
	}
	
	public boolean isTop() {
		return isTop;
	}
	
}
