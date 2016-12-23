//Handles Axe and Boat on Map Viewer

package com.neet.DiamondHunter.MapViewer;

import com.neet.DiamondHunter.Manager.Content;

import javafx.scene.image.WritableImage;

public class ShowAxeBoat {

	private int[] coordinates;

	//items
	public static final int AXE = 1;
	public static final int BOAT = 2;

	public ShowAxeBoat() {
		coordinates = new int[4];
		getObjectPosition();
	}

	public void getObjectPosition(){
		coordinates = ObjectLocation.getLocation(1);
	}

	public WritableImage getObject(int type){
		if(type == AXE){
			return new ImageConvert(Content.ITEMS[1][1]).getWrImg();
		}
		else if(type == BOAT){
			return new ImageConvert(Content.ITEMS[1][0]).getWrImg();
		}
		return null;
	}

	public boolean compareCoordinates(int row, int col, int type) {
		if(type == AXE){
			return (row == coordinates[0] && col == coordinates[1]) ? true : false;
		}
		else if(type == BOAT){
			return (row == coordinates[2] && col == coordinates[3]) ? true : false;
		}
		return false;
	}

	public void updateObjectPosition(int axeRow, int axeCol, int boatRow, int boatCol) {
		coordinates[0] = axeRow;
		coordinates[1] = axeCol;
		coordinates[2] = boatRow;
		coordinates[3] = boatCol;

		String location = Integer.toString(axeRow) + "," + Integer.toString(axeCol) + "," 
				+ Integer.toString(boatRow) + "," + Integer.toString(boatCol);
		ObjectLocation.overwriteFile(location,1);
	}
}
