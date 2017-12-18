package main;

import javax.swing.JFrame;

public class Display {
	
    private JFrame frame;
    private MyPanel panel;
    
    public static void main(String[] args) {
        new Display();
    }
    
    public Display() {
        panel = new MyPanel(800, 600);
        frame = createFrame(panel);
        frame.setVisible(true);
    }

    private JFrame createFrame(MyPanel panel) {
        JFrame frame = new JFrame("Ping | 0 - 0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }
    
    public MyPanel getPanel() {
    	return panel;
    }
    
    public JFrame getFrame() {
    	return frame;
    }
    
}