/* Get the position of Diamonds
 * and return the value
 * Get Diamonds sprite
 * */

package com.neet.DiamondHunter.MapViewer;
import com.neet.DiamondHunter.Manager.Content;

import javafx.scene.image.WritableImage;

/**
 * Handles diamonds in the GridPane
 */
public class ShowDiamonds{

	private int[] coordinate;
	private static int coordCount;

	public ShowDiamonds(){
		getEntityPosition();
	}

	public void getEntityPosition() {
		//3 indicates line 3 which is the Diamonds's coordinate
		coordinate = ObjectLocation.getLocation(3);
		coordCount = 0;
	}

	public WritableImage getEntity() {
		return new ImageConversion(Content.DIAMOND[0][0]).getWrImg();
	}

	public boolean compareCoordinates(int row, int col) {
		if(coordCount < coordinate.length - 1) {
			for(int i = 0; i < coordinate.length; i += 2) {
				if(row == coordinate[i] && col == coordinate[i + 1]) {
					coordCount += 2;
					return true;
				}
			}
		}
		return false;
	}

	public void updateEntityPosition() {
		String coords = Integer.toString(coordinate[0]) + "," + Integer.toString(coordinate[1]);
		ObjectLocation.overwriteFile(coords,2);
	}

}