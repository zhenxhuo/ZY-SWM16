package com.neet.DiamondHunter.MapViewer;

import com.neet.DiamondHunter.Main.Game;

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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
	private String itemType = "";

	private Map dhmap;
	private GraphicsContext gc;
	private TileType[][] tileInfo;
	boolean isGameLaunched;

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
		if(axeBoat.compareCoordinates(rowIndex, colIndex, ShowObject.AXE)){
			label.setGraphic(new ImageView(drawAxe));
			tileInfo[rowIndex][colIndex].setEntityType(TileType.AXE);
			tileText += "\nAxe";
			itemType = "Axe";
			tmpLocation[0] = rowIndex;
			tmpLocation[1] = colIndex;
			dragSource(label, itemType);
		}
		//display boat on top of tile
		else if(axeBoat.compareCoordinates(rowIndex, colIndex, ShowObject.BOAT)){
			label.setGraphic(new ImageView(drawBoat));
			tileInfo[rowIndex][colIndex].setEntityType(TileType.BOAT);
			tileText += "\nBoat";
			itemType = "Boat";
			tmpLocation[2] = rowIndex;
			tmpLocation[3] = colIndex;
			dragSource(label, itemType);
		}
		//display player initial position on map
		else if(player.compareCoordinates(rowIndex, colIndex)){
			label.setGraphic(new ImageView(drawPlayer));
			tileInfo[rowIndex][colIndex].setEntityType(TileType.PLAYER);
			tileText += "\nYour Starting Location";
		}
		// display diamonds initial position on map
		else if(diamonds.diamondLocations(rowIndex, colIndex)) {
			label.setGraphic(new ImageView(drawDiamond));
			tileInfo[rowIndex][colIndex].setEntityType(TileType.DIAMOND);
			tileText += "\nDiamond";
		}

		if (!tileInfo[rowIndex][colIndex].isNormal()){
			tileText += "\nBlocked";
		}

		label.setUserData(tileInfo[rowIndex][colIndex]);
		dropTarget(label, tileInfo[rowIndex][colIndex]);

		final String tt = tileText;

		label.setOnMouseEntered(e -> {
			infoText.setText(tt);
		});

		mapGrid.add(label, colIndex, rowIndex);
	}

	/**
	 * Method to drag axe/boat
	 * @param source The label of the object going or being dragged
	 * @param itemType The string of the item being dragged
	 */
	private void dragSource(Label source, String item) {
		source.setOnDragDetected((MouseEvent e) -> {
			Dragboard drag = source.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(((ImageView) (source.getGraphic())).getImage());
			drag.setContent(content);

			infoText.setText("Dragging: " + item + "\nYou can only place the " + item + " in non-black tiles");
			itemType = item;
			e.consume();
		});

		source.setOnDragDone(e -> {
			if (e.getTransferMode() == TransferMode.MOVE)
				source.setGraphic(null);
			e.consume();
		});
	}

	/**
	 * Method to drop axe/boat on any valid tile
	 * 
	 * @param target The label where the dragging object is currently on
	 * @param tInfo The tile information of every tile in the map
	 */
	private void dropTarget(Label target, TileType tInfo) {

		target.setOnDragOver(e -> {
			if (e.getGestureSource() != target) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
			e.consume();
		});

		target.setOnDragEntered(e -> {
			if (e.getGestureSource() != target && e.getDragboard().hasContent(DataFormat.IMAGE)) {
				// if the tile has items on it or is blocked, set colour to red
				if (tInfo.isObject() || !tInfo.isNormal()) {
					target.setStyle("-fx-background-color: rgba(0,0,0,1)");
				} else {
					target.setStyle("-fx-background-color: rgba(0,0,0,0)");
				}
			}
			e.consume();
		});

		target.setOnDragExited(e -> {
			target.setStyle(null);
		});

		if (!tInfo.isObject() && tInfo.isNormal()) {
			target.setOnDragDropped((DragEvent e) -> {

				Dragboard drag = e.getDragboard();
				boolean flag = false;
				if (drag.hasContent(DataFormat.IMAGE)) {
					TileType targetTile = (TileType)(target.getUserData());

					target.setGraphic(new ImageView(((Image) drag.getContent(DataFormat.IMAGE))));
					flag = true;
					//update the new coordinates of axe or boat
					if(itemType == "Axe"){
						tmpLocation[0] = targetTile.getRow();
						tmpLocation[1] = targetTile.getCol();
					}else if(itemType == "Boat"){
						tmpLocation[2] = targetTile.getRow();
						tmpLocation[3] = targetTile.getCol();
					}
					saveLocation();
					updateGridPane();
				}
				e.setDropCompleted(flag);
				e.consume();
			});
		}
	}

	/**
	 * Refreshes the GridPane every time a drag and drop action is completed successfully.
	 * Saves the changed coordinates of the moved item as well.
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
	 * Switches the overwrite to true.
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

	//Exits the Map Editing Mode
	@FXML
	private void exitMapView() {
		System.exit(0);
	}
}
