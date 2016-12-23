/* Get the position of player
 * and return the value
 * Get player sprite
 * */

package com.neet.DiamondHunter.MapViewer;

import com.neet.DiamondHunter.Entity.Player;
import com.neet.DiamondHunter.Manager.Content;

import javafx.scene.image.WritableImage;

/**
 * Handles player in the GridPane
 *
 */
public class ShowPlayer{

	private int[] coordinate;

	public ShowPlayer(){
		coordinate = new int[2];
		getEntityPosition();
		updateEntityPosition();
	}

	public void getEntityPosition() {
		//2 indicates line 2 which is the player's coordinate
		coordinate = ObjectLocation.getLocation(2);
	}

	public WritableImage getEntity() {
		WritableImage player = Player.onWater ? new ImageConversion(Content.PLAYER[0][5]).getWrImg() : new ImageConversion(Content.PLAYER[0][0]).getWrImg();
		return player;
	}

	public boolean compareCoordinates(int row, int col) {
		return (row == coordinate[0] && col == coordinate[1]) ? true : false;
	}

	public void updateEntityPosition() {
		String coords = Integer.toString(coordinate[0]) + "," + Integer.toString(coordinate[1]);
		ObjectLocation.overwriteFile(coords,1);
	}

}