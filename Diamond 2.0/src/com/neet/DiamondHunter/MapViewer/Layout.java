package com.neet.DiamondHunter.MapViewer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class Layout extends Pane {

	// dimensions
	// HEIGHT is the playing area size
	// HEIGHT2 includes the bottom window
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final int HEIGHT2 = HEIGHT + 16;
	public static final int SCALE = 3;
	private Map map;
	
	public Layout(){
		setPrefSize(WIDTH * SCALE, HEIGHT2 * SCALE);
		map = new Map(16, WIDTH * SCALE, HEIGHT2 * SCALE);
		map.loadTiles("/Tilesets/testtileset.gif");
		map.loadMap("/Maps/testmap.map");
		GraphicsContext gc = map.getGraphicsContext2D();
		map.drawImage(gc);
		getChildren().add(map);
	}
}
