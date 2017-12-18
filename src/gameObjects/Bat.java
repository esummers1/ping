package gameObjects;

public class Bat extends GameObject {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 60;
	
	private boolean acceleratingDown;
	private boolean acceleratingUp;
	
	public Bat(double x, double y) {
		super(WIDTH, HEIGHT, x, y, 0, 0);
	}
	
	public boolean isAcceleratingDown() {
		return this.acceleratingDown;
	}
	
	public boolean isAcceleratingUp() {
		return this.acceleratingUp;
	}
	
	public void setAcceleratingDown() {
		this.acceleratingDown = true;
	}
	
	public void setAcceleratingUp() {
		this.acceleratingUp = true;
	}
	
	public void resetAcceleration() {
		this.acceleratingDown = false;
		this.acceleratingUp = false;
	}
}
