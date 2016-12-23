package com.neet.DiamondHunter.MapViewer;

import java.net.URL;
import java.util.ResourceBundle;

import com.neet.DiamondHunter.Main.Game;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MapController  {

	private GraphicsContext gc;
	private TileType[][] tileInfo;
	boolean isGameLaunched;
	Map map;

	@FXML
	private AnchorPane Pane;
	@FXML
	private Canvas Canvas;
	@FXML
	private GridPane tileMapping;
	@FXML
	private StackPane mapStack;
	@FXML
	private VBox tileVBox;
	@FXML
	private Button Save;
	@FXML
	private Button Exit;
	@FXML
	private Button Play;


	public void initialize(URL location, ResourceBundle resources) {

		map = new Map();
		initMapCanvas();
		mapStack.relocate(20, 20);
		mapStack.setPrefSize((double) (map.getNumRows() * map.getTileSize()),
				(double) (map.getNumCols() * map.getTileSize()));


		initTileMapping();
	}

	private void initMapCanvas() {
		Canvas.setWidth((double) Map.WIDTH);
		Canvas.setHeight((double) Map.HEIGHT);

		map.loadTiles("/Tilesets/testtileset.gif");
		map.loadMap("/Maps/testmap.map");
		gc = Canvas.getGraphicsContext2D();
		map.drawImage(gc);


		Canvas.getGraphicsContext2D().drawImage(gc.getCanvas().snapshot(new SnapshotParameters(), null), 0, 0);
	}

	private void updateGridPane(){
		tileMapping.getChildren().clear();
	}

	//Initializes the grid on top of the map that handles input of boat and axe.

	private void initTileMapping(){

	}

	@FXML
	private void saveCoor() {
		System.out.println(" Location saved");
	}

	//Exit
	@FXML
	private void exitMapView() {
		System.exit(0);
	}
	
	// Start the Game
	@FXML
	private void playGame() {
		if (!isGameLaunched) {
			Game.play();
			
			isGameLaunched = true;
		}
	}
}
