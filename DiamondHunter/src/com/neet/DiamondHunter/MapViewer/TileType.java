package com.neet.DiamondHunter.MapViewer;

import javafx.scene.image.WritableImage;

public class TileType {
	
	private WritableImage image;
	private int type;
	
	public static final int INVALID	= 0;
	public static final int VALID	= 1;
	
	public TileType(WritableImage image, int type){
		this.image	= image;
		this.type	= type;
	}
	
	public WritableImage getImage() { return image;}
	public int getType() { return type;}
}
