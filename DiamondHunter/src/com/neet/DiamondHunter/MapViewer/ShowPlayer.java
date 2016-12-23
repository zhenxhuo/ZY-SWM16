//Handles player in the GridPane

package com.neet.DiamondHunter.MapViewer;

import com.neet.DiamondHunter.Manager.Content;

import javafx.scene.image.WritableImage;

public class ShowPlayer{

	private int[] coordinate;

	public ShowPlayer(){
		coordinate = new int[2];
		getObjectPosition();
		updateObjectPosition();
	}

	public void getObjectPosition() {
		//2 indicates line 2 which is the player's coordinate
		coordinate = ObjectLocation.getLocation(2);
	}

	public WritableImage getObject() {
		WritableImage player = new ImageConvert(Content.PLAYER[0][0]).getWrImg();
		return player;
	}

	public boolean compareCoordinates(int row, int col) {
		return (row == coordinate[0] && col == coordinate[1]) ? true : false;
	}

	public void updateObjectPosition() {
		String coords = Integer.toString(coordinate[0]) + "," + Integer.toString(coordinate[1]);
		ObjectLocation.overwriteFile(coords,1);
	}
}
