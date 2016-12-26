package com.neet.DiamondHunter.MapViewer;

import com.neet.DiamondHunter.Main.Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Main controller for the interface. Interactions in the
 * MapView.fxml is computed here.
 */
public class MapController{

	private Map dhmap;
	private GraphicsContext gc;
	private TileType[][] tileInfo;
	boolean isGameLaunched;

	//All objects instantiation
	private ShowObject	axeBoat;
	private ShowObject	player;
	private ShowObject	diamonds;

	//All objects' image
	private Image axeship = new Image("/Sprites/items.gif");
	PixelReader axeImage = axeship.getPixelReader();
	WritableImage drawAxe = new WritableImage(axeImage,16,16,16,16);
	WritableImage drawBoat = new WritableImage(axeImage,0,16,16,16);

	private Image players = new Image("/Sprites/playersprites.gif");
	PixelReader playerImage = players.getPixelReader();
	WritableImage drawPlayer = new WritableImage(playerImage,0,0,16,16);

	private Image diamond = new Image("/Sprites/diamond.gif");
	PixelReader diamondImage = diamond.getPixelReader();
	WritableImage drawDiamond = new WritableImage(diamondImage,0,0,16,16);

	//Temporary storage for the updated item location
	private int[] tmpLocation = new int[4];

	@FXML
	private AnchorPane mainPane;
	@FXML
	private Canvas mapCanvas;
	@FXML
	private GridPane mapGrid;
	@FXML
	private StackPane mapStack;
	@FXML
	private VBox tileVBox;
	@FXML
	private TextArea infoText;

	@FXML
	private Button btnPlay;
	@FXML
	private Button btnAxe;
	@FXML
	private Button btnBoat;
	@FXML
	private Button btnExit;

	public void initialize(){

		ObjectLocation.checkExist();
		// At launch of Map Viewer, the game itself is never launched
		isGameLaunched = false;
		// MapPane has all the loaders for the map
		dhmap = new Map();
		axeBoat = new ShowObject("ITEMS");
		player = new ShowObject("PLAYER");
		diamonds = new ShowObject("DIAMONDS");

		// The info display box cannot be edited by user
		infoText.setEditable(false);
		infoText.setText("Welcome to Diamond Hunter Map Editor!");
		initMapCanvas();

		// Initializes size of the StackPane that contains the map in canvas and GridPane
		mapStack.relocate(20, 20);
		mapStack.setPrefSize((dhmap.getNumRows() * dhmap.getTileSize()),(dhmap.getNumCols() * dhmap.getTileSize()));
		tileVBox.setOnMouseEntered(e -> {
			infoText.setText("This Diamond Hunter Map Editor. Drag and drop the axe or boat to change location. Changes is saved automatically.");
		});
		mainPane.setOnMouseEntered(e -> {
			infoText.setText("This Diamond Hunter Map Editor. Drag and drop the axe or boat to change location. Changes is saved automatically.");
		});
		btnPlay.setOnMouseEntered(e -> {
			infoText.setText("Play Diamond Hunter with current axe and boat location");
		});
		btnExit.setOnMouseEntered(e -> {
			infoText.setText("Exit Diamond Hunter Map Editor");
		});

		mainPane.setMinSize(mapStack.getPrefWidth() + 16, mapStack.getPrefHeight() + 16);
		initMapGrid();
	}

	/**
	 * Initialize the map in a non-FXML canvas and draws onto FXML canvas
	 */
	private void initMapCanvas() {
		mapCanvas.setWidth((double) Map.WIDTH);
		mapCanvas.setHeight((double) Map.HEIGHT);

		dhmap.loadTiles("/Tilesets/testtileset.gif");
		dhmap.loadMap("/Maps/testmap.map");
		gc = mapCanvas.getGraphicsContext2D();
		dhmap.drawImage(gc);

		mapCanvas.getGraphicsContext2D().drawImage(gc.getCanvas().snapshot(new SnapshotParameters(), null), 0, 0);
	}

	//Initializes the grid on top of the map that handles input of boat and axe.
	private void initMapGrid(){
		tileInfo = new TileType[dhmap.getNumRows()][dhmap.getNumCols()];
		for (int i = 0; i < tileInfo.length; i++) {
			mapGrid.getColumnConstraints().add(new ColumnConstraints((dhmap.getTileSize())));
			mapGrid.getRowConstraints().add(new RowConstraints((dhmap.getTileSize())));
		}

		for (int row = 0; row < dhmap.getNumRows(); row++) {
			for (int col = 0; col < dhmap.getNumCols(); col++) {
				tileInfo[row][col] = new TileType(dhmap.getTileImageFromMap(row, col), row, col);
				addTile(col, row);
			}
		}
	}

	private void addTile(int colIndex, int rowIndex) {

		Label label = new Label();
		label.setMinSize(dhmap.getTileSize(), dhmap.getTileSize());
		String tileText = "Coordinate: ("+ Integer.toString(rowIndex) +","+ Integer.toString(colIndex)
		+")\nTile Image: ";

		if (tileInfo[rowIndex][colIndex].getTileImageType() == TileType.GREENS) {
			tileText += "Green tile";
		} else if (tileInfo[rowIndex][colIndex].getTileImageType() == TileType.GRASS) {
			tileText += "Grass tile";
		} else if (tileInfo[rowIndex][colIndex].getTileImageType() == TileType.FLOWER) {
			tileText += "Flower tile";
		} else if (tileInfo[rowIndex][colIndex].getTileImageType() == TileType.TREE) {
			tileText += "Tree";
		} else if (tileInfo[rowIndex][colIndex].getTileImageType() == TileType.BRANCH) {
			tileText += "Dead Tree";
		} else if (tileInfo[rowIndex][colIndex].getTileImageType() == TileType.WATER) {
			tileText += "Water";
		}

		//display axe on top of tile
		if(axeBoat.axeBoatLocation(rowIndex, colIndex, ShowObject.AXE)){
			label.setGraphic(new ImageView(drawAxe));
			tileInfo[rowIndex][colIndex].setObjectType(TileType.AXE);
			tileText += "\nAxe";
			tmpLocation[0] = rowIndex;
			tmpLocation[1] = colIndex;
		}
		//display boat on top of tile
		else if(axeBoat.axeBoatLocation(rowIndex, colIndex, ShowObject.BOAT)){
			label.setGraphic(new ImageView(drawBoat));
			tileInfo[rowIndex][colIndex].setObjectType(TileType.BOAT);
			tileText += "\nBoat";
			tmpLocation[2] = rowIndex;
			tmpLocation[3] = colIndex;
		}
		//display player initial position on map
		else if(player.playerLocation(rowIndex, colIndex)){
			label.setGraphic(new ImageView(drawPlayer));
			tileInfo[rowIndex][colIndex].setObjectType(TileType.PLAYER);
			tileText += "\nYour Starting Location";
		}
		// display diamonds initial position on map
		else if(diamonds.diamondLocations(rowIndex, colIndex)) {
			label.setGraphic(new ImageView(drawDiamond));
			tileInfo[rowIndex][colIndex].setObjectType(TileType.DIAMOND);
			tileText += "\nDiamond";
		}

		if (!tileInfo[rowIndex][colIndex].isNormal()){
			tileText += "\nBlocked";
		}

		label.setUserData(tileInfo[rowIndex][colIndex]);

		final String tt = tileText;

		label.setOnMouseEntered(e -> {
			infoText.setText(tt);
		});

		mapGrid.add(label, colIndex, rowIndex);
	}

	/**
	 * Refreshes the GridPane when an action is completed.
	 * Saves the new location of moved item(s).
	 */
	private void updateGridPane() {
		axeBoat.getItemPosition();
		player.getPlayerPosition();
		diamonds.getDiamondPosition();
		mapGrid.getChildren().clear();
		tileInfo = new TileType[dhmap.getNumRows()][dhmap.getNumCols()];

		for (int row = 0; row < dhmap.getNumRows(); row++) {
			for (int col = 0; col < dhmap.getNumCols(); col++) {
				tileInfo[row][col] = new TileType(dhmap.getTileImageFromMap(row, col), row, col);
				addTile(col, row);
			}
		}
	}

	/**
	 * Saves the coordinates of all items.
	 */
	private void saveLocation() {
		ObjectLocation.overwriteMode = true;
		axeBoat.updateItemPosition(tmpLocation[0], tmpLocation[1], tmpLocation[2], tmpLocation[3]);
	}

	@FXML
	private void playGame() {
		if (!isGameLaunched) {
			Game.main(null);
			isGameLaunched = true;
		}
	}

	@FXML
	void UpdateAxeLocation(ActionEvent event){

		infoText.setText("Click on the map to set new position of Axe.");

		//Set Axe new location
		mapGrid.setOnMouseClicked(click -> {

			tmpLocation[1] = (int) click.getX()/16; 
			tmpLocation[0] = (int) click.getY()/16;

			infoText.setText("Axe location updated.");
			saveLocation();
			updateGridPane();
		});
	}

	@FXML
	void UpdateBoatLocation(ActionEvent event){

		infoText.setText("Click on the map to set new position of Boat.");

		//Set Boat new location
		mapGrid.setOnMouseClicked(click -> {

			tmpLocation[3] = (int) click.getX()/16; 
			tmpLocation[2] = (int) click.getY()/16;

			infoText.setText("Boat location updated.");
			saveLocation();
			updateGridPane();
		});
	}

	//Exits the Map Editing Mode
	@FXML
	private void exitMapView() {
		System.exit(0);
	}
}
