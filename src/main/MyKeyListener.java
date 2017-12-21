package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	
	private Game game;
	
	public MyKeyListener(Game game) {
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		game.setCurrentKey(k.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

}
