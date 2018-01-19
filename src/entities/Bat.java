package entities;

public class Bat extends Entity {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 60;
	
	private boolean accDown;
	private boolean accUp;
	
	public Bat(double xPos, double yPos) {
		super(WIDTH, HEIGHT, xPos, yPos, 0, 0);
	}
	
	public boolean isAccDown() {
		return this.accDown;
	}
	
	public boolean isAccUp() {
		return this.accUp;
	}
	
	public void setAccDown() {
		this.accDown = true;
	}
	
	public void setAccUp() {
		this.accUp = true;
	}
	
	public void resetAcc() {
		this.accDown = false;
		this.accUp = false;
	}
}
