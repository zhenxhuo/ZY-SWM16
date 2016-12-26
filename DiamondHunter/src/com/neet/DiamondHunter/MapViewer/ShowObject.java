//Handles Axe, Boat, Player and Diamonds on Map Viewer

package com.neet.DiamondHunter.MapViewer;

public class ShowObject{

	private int[] location;
	private static int diamondCount;

	//items
	public static final int AXE = 1;
	public static final int BOAT = 2;

	public ShowObject(String object){
		switch(object){

		case "ITEMS":
			location = new int[4];
			getItemPosition();
			break;

		case "PLAYER":
			location = new int[2];
			getPlayerPosition();
			updatePlayerPosition();
			break;

		case "DIAMONDS":
			getDiamondPosition();
			break;
		}
	}

	public void getItemPosition(){
		//Line 1 indicates the axe and boat location
		location = ObjectLocation.getLocation(1);
	}

	public void getPlayerPosition() {
		//Line 2 indicates player's location
		location = ObjectLocation.getLocation(2);
	}

	public void getDiamondPosition() {
		//Line 3 indicates diamonds' location
		location = ObjectLocation.getLocation(3);
		diamondCount = 0;
	}

	public boolean axeBoatLocation(int row, int col, int type) {
		if(type == AXE){
			return (row == location[0] && col == location[1]) ? true : false;
		}
		else if(type == BOAT){
			return (row == location[2] && col == location[3]) ? true : false;
		}
		return false;
	}
	
	public boolean playerLocation(int row, int col) {
		return (row == location[0] && col == location[1]) ? true : false;
	}

	public boolean diamondLocations(int row, int col) {
		if(diamondCount < location.length - 1) {
			for(int i = 0; i < location.length; i += 2) {
				if(row == location[i] && col == location[i + 1]) {
					diamondCount += 2;
					return true;
				}
			}
		}
		return false;
	}

	public void updateItemPosition(int axeRow, int axeCol, int boatRow, int boatCol) {
		location[0] = axeRow;
		location[1] = axeCol;
		location[2] = boatRow;
		location[3] = boatCol;

		String location = Integer.toString(axeRow) + "," + Integer.toString(axeCol) + "," 
				+ Integer.toString(boatRow) + "," + Integer.toString(boatCol);
		ObjectLocation.overwriteFile(location,1);
	}

	public void updatePlayerPosition() {
		String xyLocation = Integer.toString(location[0]) + "," + Integer.toString(location[1]);
		ObjectLocation.overwriteFile(xyLocation,1);
	}
}
