package gameObjects;

public class Goal extends Entity {
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 600;
	
	public Goal(double xPos) {
		super(WIDTH, HEIGHT, xPos, 0, 0, 0);
	}
	
	@Override
	public boolean canCollideWith(Entity entity) {
		return false;
	}
}
