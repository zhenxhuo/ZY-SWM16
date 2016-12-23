package com.neet.DiamondHunter.MapViewer;

/**
 * Contains all information of a single tile.
 */

public class TileType {

	private int tileImageType;
	private boolean isObject;
	private boolean isNormal;
	private int hasObject;

	//Tile coordinates
	private int row,col;

	//Tile placeable
	public static final boolean INVALID	= false;
	public static final boolean VALID	= true;

	//Obstacles tile value
	public static final int TREE	= 20;
	public static final int BRANCH	= 21;
	public static final int WATER	= 22;

	//Walkable tile value
	public static final int GREENS	= 1;
	public static final int GRASS	= 2;
	public static final int FLOWER	= 3;

	//Object on tile's value
	public static final int AXE		= 1;
	public static final int BOAT	= 2;
	public static final int DIAMOND	= 3;
	public static final int PLAYER	= 4;
	public static final int NONE	= 0;

	public TileType(int tileImageType, int row, int col){
		this.tileImageType = tileImageType;
		this.row = row;
		this.col = col;
		this.isNormal = (tileImageType == GREENS || tileImageType == GRASS || tileImageType == FLOWER) ? true : false;
		this.hasObject = NONE;
		this.isObject = false;
	}

	public TileType(int tileImageType, int row, int col, int entityType) {
		this.tileImageType = tileImageType;
		this.isNormal = (tileImageType == GREENS || tileImageType == GRASS || tileImageType == FLOWER) ? true : false;
		this.row = row;
		this.col = col;
		this.hasObject = entityType;
		this.isObject = (entityType != 0) ? true : false;
	}
	private void setEntity(boolean isEntity) {
		this.isObject = isEntity;
	}

	public void setEntityType(int entityType) {
		this.hasObject = entityType;
		setEntity((this.hasObject != 0) ? true : false);
	}

	public boolean isObject() {return isObject;}
	public int getTileImageType() {return tileImageType;}
	public boolean isNormal() {return isNormal;}
	public int getRow() {return row;}
	public int getCol() {return col;}
	public int getEntityType() {return hasObject;}
}
