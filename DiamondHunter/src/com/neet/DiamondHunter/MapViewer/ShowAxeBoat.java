/* Get position of axe and boat
 * and return the value
 * Update position of axe and boat
 * Get axe and boat sprites
 * */

package com.neet.DiamondHunter.MapViewer;

import com.neet.DiamondHunter.Manager.Content;

import javafx.scene.image.WritableImage;

//Handles axe and boat entity for the GridPane.
public class ShowAxeBoat {

	private int[] coordinates;

	//items
	public static final int AXE = 1;
	public static final int BOAT = 2;

	public ShowAxeBoat() {
		coordinates = new int[4];
		getEntityPosition();
	}

	public void getEntityPosition(){
		coordinates = ObjectLocation.getLocation(1);
	}

	public WritableImage getObject(int type){
		if(type == BOAT){
			return new ImageConversion(Content.ITEMS[1][0]).getWrImg();
		}
		else if(type == AXE){
			return new ImageConversion(Content.ITEMS[1][1]).getWrImg();
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

	public void updateEntityPosition(int axeRow, int axeCol, int boatRow, int boatCol) {
		coordinates[0] = axeRow;
		coordinates[1] = axeCol;
		coordinates[2] = boatRow;
		coordinates[3] = boatCol;

		String location = Integer.toString(axeRow) + "," + Integer.toString(axeCol) + "," 
				+ Integer.toString(boatRow) + "," + Integer.toString(boatCol);
		ObjectLocation.overwriteFile(location,1);
	}
}
