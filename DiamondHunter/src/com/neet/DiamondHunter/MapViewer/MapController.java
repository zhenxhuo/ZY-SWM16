package com.neet.DiamondHunter.MapViewer;

import java.net.URL;
import java.util.ResourceBundle;

import com.neet.DiamondHunter.Main.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Main controller for the interface. Any interaction in the
 * MapViewInterface.fxml is computed here.
 *
 */
public class MapController implements Initializable {

	private GraphicsContext gc;
	private TileType[][] tileInfo;
	boolean isGameLaunched;

	@FXML
	private AnchorPane mainPane;
	@FXML
	private Canvas mapCanvas;
	@FXML
	private GridPane tileMapping;
	@FXML
	private StackPane mapStack;
	@FXML
	private VBox tileVBox;
	@FXML
	private TextArea infoText;

	@FXML
	private Button btnSave;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnPlay;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// The tile information display box is never editable
		infoText.setEditable(false);
		infoText.setText("Welcome to Diamond Hunter Map Editor!");
		initMapCanvas();

		/*
		 * This is the base size of the entire application. The AnchorPain (main
		 * window) is adjusted based on the size of the map. However, this is
		 * FXML overridden if the application grows.
		 */
		mainPane.setMinSize(mapStack.getPrefWidth() + 100, mapStack.getPrefHeight() + 100);
		initTileMapping();
	}

	/**
	 * Initialises the map in a non-FXML canvas and draws onto FXML canvas as a
	 * whole.
	 */
	private void initMapCanvas() {
		mapCanvas.setWidth((double) Map.WIDTH);
		mapCanvas.setHeight((double) Map.HEIGHT);

		gc = mapCanvas.getGraphicsContext2D();

		mapCanvas.getGraphicsContext2D().drawImage(gc.getCanvas().snapshot(new SnapshotParameters(), null), 0, 0);
	}

	private void updateGridPane(){
		tileMapping.getChildren().clear();
	}

	//Initializes the grid on top of the map that handles input of boat and axe.

	private void initTileMapping(){

	}

	@FXML
	private void saveCoor() {
		System.out.println("Axe and Boat location saved");
	}

	//Exits the Map Editing Mode
	@FXML
	private void exitMapView() {
		System.exit(0);
	}

	@FXML
	private void playGame() {
		if (!isGameLaunched) {
			Game.play();
			//Game.getWindow().setAutoRequestFocus(true);
			isGameLaunched = true;
		}else{
			//Game.getWindow().setVisible(true);
		}
	}
}
