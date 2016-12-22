package application;

import javafx.scene.image.WritableImage;

public class Tile {
	
	private WritableImage image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(WritableImage image, int type) {
		this.image = image;
		this.type = type;
	}
	

	public WritableImage getImage() { return image; }
	public int getType() { return type; }
}
