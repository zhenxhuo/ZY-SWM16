// Only contains two types of tiles:
// Blocked and non-blocked.

package application;

import javafx.scene.image.Image;

public class Tile {
	
	private Image image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(Image image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public Image getImage() { return image; }
	public int getType() { return type; }
	
}
