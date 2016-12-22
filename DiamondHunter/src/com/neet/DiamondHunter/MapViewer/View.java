// The entry point of the game.
// This class loads up a JFrame window and
// puts a GamePanel into it.

package com.neet.DiamondHunter.MapViewer;

import javax.swing.JFrame;

public class View {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Diamond Hunter");
		
		window.add(new ViewPanel());
		window.setResizable(false);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
