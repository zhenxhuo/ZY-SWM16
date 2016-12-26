package com.neet.DiamondHunter.Main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Controller {
	
	@FXML
	private Button Play;
	@FXML
	private Button Exit;
	boolean PlayGame;
	
	@FXML
	private Canvas canvas;
	private int tileSize = 16;
	private newTile[][] tiles;
	private double numTileAcross;
	private int[][] MAP;
	private int numRows;
	private int numCols;
	private Image tileset = new Image("/Tilesets/testtileset.gif");
	PixelReader tiless = tileset.getPixelReader();

	

	private Image axe = new Image("/Sprites/items.gif");
	private Image boat = new Image("/Sprites/items.gif");
	private Image diamond = new Image("/Sprites/diamond.gif");
	
	PixelReader read1 = axe.getPixelReader();
	PixelReader read2 = boat.getPixelReader();
	PixelReader read3 = diamond.getPixelReader();
	
	//boat and axe location
	public static int axex = 22;
	public static int axey = 19;
	public static int boatx = 22;
	public static int boaty = 18;

	
	@FXML
	private void playGame() {
		if (!PlayGame) {
			Game.main(null);
			PlayGame= true;
		}
	}
	@FXML
	private void Exit() {
		System.exit(0);
	}

	@FXML
	public void MapDraw()     {	
    	
    	GraphicsContext gc = canvas.getGraphicsContext2D();
    	
    	// loading the map tiles
    	numTileAcross = tileset.getWidth() / tileSize;  	
    	tiles = new newTile[2][(int) numTileAcross];	
    	WritableImage subimage;
    	
		for(int col = 0; col < numTileAcross; col++) 
		{	
			subimage = new WritableImage(tiless, col*tileSize, 0, tileSize, tileSize);
			tiles[0][col] = new newTile(subimage, newTile.NORMAL);
			
			subimage = new WritableImage(tiless, col*tileSize, tileSize, tileSize, tileSize);
			tiles[1][col] = new newTile(subimage, newTile.BLOCKED);
		}
		
		// loading the map 
		
		try{
			InputStream in = getClass().getResourceAsStream("/Maps/testmap.map");
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			
			MAP = new int[numRows][numCols];
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) 
			{
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) 
				{
					MAP[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		// drawing the map on canvas			
		for(int row = 0; row < numRows; row++) 
		{		
			for(int col = 0; col < numCols; col++) 
			{			
				int rc = MAP[row][col];
				int r = (int) (rc / numTileAcross);
				int c = (int) (rc % numTileAcross);
				
				gc.drawImage(tiles[r][c].getImage(), col * tileSize, row * tileSize);
			}
		}

	//drawitem
	//draw axe
	WritableImage subimage1,subimage2,subimage3;
	subimage1 = new WritableImage(read1, tileSize, tileSize, tileSize, tileSize);
	gc.drawImage(subimage1,axex*tileSize, axey*tileSize);
	
	// draw boat
	subimage2 = new WritableImage(read2, 0, tileSize, tileSize, tileSize);	
	gc.drawImage(subimage2, boatx*tileSize, boaty*tileSize);
	
	
	// draw diamond
	subimage3 = new WritableImage(read3, 0, 0, tileSize, tileSize);
	
	gc.drawImage(subimage3, 20*tileSize, 20*tileSize);
	gc.drawImage(subimage3, 20*tileSize, 13*tileSize);
	gc.drawImage(subimage3, 36*tileSize, 12*tileSize);
	gc.drawImage(subimage3, 3*tileSize, 4*tileSize);
	gc.drawImage(subimage3, 4*tileSize, 28*tileSize);
	gc.drawImage(subimage3, 34*tileSize, 4*tileSize);
	gc.drawImage(subimage3, 19*tileSize, 28*tileSize);
	gc.drawImage(subimage3, 26*tileSize, 35*tileSize);
	gc.drawImage(subimage3, 36*tileSize, 38*tileSize);
	gc.drawImage(subimage3, 28*tileSize, 27*tileSize);
	gc.drawImage(subimage3, 30*tileSize, 20*tileSize);
	gc.drawImage(subimage3, 25*tileSize, 14*tileSize);
	gc.drawImage(subimage3, 21*tileSize, 4*tileSize);
	gc.drawImage(subimage3, 14*tileSize, 9*tileSize);
	gc.drawImage(subimage3, 14*tileSize, 20*tileSize);
	
	
	}
	}
	

