package gameObjects;

public class Ball extends Entity {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	public Ball(double xPos, double yPos) {
		super(WIDTH, HEIGHT, xPos, yPos, 0, 0);
	}
	
	/**
	 * Generate a random velocity in each direction and assign
	 */
	public void initializeVel() {
		double xVel = Math.random() * 2 + 2;
		double yVel = Math.random() * 1.5 + 1.5;
		
		boolean xDir = Math.random() < 0.5;
		boolean yDir = Math.random() < 0.5; 
		
		if (xDir) {
			xVel *= -1;
		}
		
		if (yDir) {
			yVel *= -1;
		}
		
		setVel(xVel, yVel);
	}
	
	@Override
	public boolean canCollideWith(Entity entity) {
		return (entity instanceof Ball) ? false : true;
	}
}
