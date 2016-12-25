// Only contains two types of tiles:
// Blocked and non-blocked.

package com.neet.DiamondHunter.Main;

import javafx.scene.image.Image;

public class newTile {
	
	private Image image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public newTile(Image image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public Image getImage() { return image; }
	public int getType() { return type; }
	
}
