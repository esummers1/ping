package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import gameObjects.GameObject;

public class MyPanel extends JPanel {
	
    // not sure what this is for
	private static final long serialVersionUID = 1L;
	private Graphics g;
    private List<GameObject> objects;

	public MyPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
    }
	
	@Override
    protected void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        for (GameObject object : objects) {
        	object.draw(g2d);
        }
    }
    
    /**
     * Accept a list of GameObjects to draw.
     * @param objects
     */
    public void setObjects(List<GameObject> objects) {
    	this.objects = objects;
    }
}
