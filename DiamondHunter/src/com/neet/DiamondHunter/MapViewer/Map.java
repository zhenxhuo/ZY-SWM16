package com.neet.DiamondHunter.MapViewer;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Map {

	// position
	private int x;
	private int y;

	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;

	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private WritableImage[][] tiles;

	// dimensions
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;

	public Map(){
		tileSize = 16;
	}

	public void loadTiles(String s){

		try{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new WritableImage[2][numTilesAcross];

			BufferedImage subimage1, subimage2;
			for (int col = 0; col < numTilesAcross; col++){
				subimage1 = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				subimage2 = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);

				WritableImage wr1, wr2;
				wr1 = wr2 = null;

				if (subimage1 != null && subimage2 != null){

					wr1 = new WritableImage(subimage1.getWidth(), subimage1.getHeight());
					wr2 = new WritableImage(subimage2.getWidth(), subimage2.getHeight());
					PixelWriter pw1 = wr1.getPixelWriter();
					PixelWriter pw2 = wr2.getPixelWriter();

					for (int x = 0; x < subimage1.getWidth(); x++){
						for (int y = 0; y < subimage1.getHeight(); y++){
							pw1.setArgb(x, y, subimage1.getRGB(x, y));
						}
					}

					for (int x = 0; x < subimage2.getWidth(); x++){
						for (int y = 0; y < subimage2.getHeight(); y++){
							pw2.setArgb(x, y, subimage2.getRGB(x, y));
						}
					}
				}
				tiles[0][col] = wr1;
				tiles[1][col] = wr2;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public void loadMap(String s){

		try{
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public void drawImage(GraphicsContext gc) {

		for (int row = 0; row < numRows; row++){
			for (int col = 0; col < numCols; col++){
				if (map[row][col] == 0)	continue;

				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				gc.save();
				gc.drawImage(tiles[r][c], x + col * tileSize, y + row * tileSize);
			}
		}
	}
}
