package com.neet.DiamondHunter.MapViewer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.neet.DiamondHunter.Entity.Item;
import com.neet.DiamondHunter.Main.GamePanel;
import com.neet.DiamondHunter.TileMap.TileMap;

public class Launch extends Application{
	
	// pointer
	private Pointer pointer;

	// tilemap
	private TileMap tileMap;
	
	// items
	private ArrayList<Item> items;

	// camera position
	private int xsector;
	private int ysector;
	private int sectorSize; 
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)throws Exception{
		primaryStage.setTitle("Map Viewer");
		primaryStage.show();
		
		StackPane layout = new StackPane();
		
		Scene scene = new Scene(layout,640,480);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void initlz(){
		//create items list
		items = new ArrayList<Item>();
		
		// load map
		tileMap = new TileMap(16);
		tileMap.loadTiles("/Tilesets/testtileset.gif");
		tileMap.loadMap("/Maps/testmap.map");
		
		// fill lists
		populateItems();
		
		// create player
		pointer = new Pointer(tileMap);

		// initialize player
		pointer.setTilePosition(5,5);
		
		// set up camera position
		sectorSize = GamePanel.WIDTH;
		xsector = pointer.getx() / sectorSize;
		ysector = pointer.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);
	}
	
	public void update(){}
	public void draw(Graphics2D g){}
	public void handleInput(){}
	
	private void populateItems() {
		
		Item item;
		
		item = new Item(tileMap);
		item.setType(Item.AXE);
		item.setTilePosition(26, 37);
		items.add(item);
		
		item = new Item(tileMap);
		item.setType(Item.BOAT);
		item.setTilePosition(12, 4);
		items.add(item);
	}
}
