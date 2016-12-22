// The tile map class contains a loaded tile set
// and a 2d array of the map.
// Each index in the map corresponds to a specific tile.

package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.neet.DiamondHunter.Main.GamePanel;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Map extends Canvas {
	
	
	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int x;
	private int y;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public Map(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
	}
	
	public void loadTiles(String s) {
		
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize,tileSize,tileSize,tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
				
				int width = (int)tileset.getWidth();
				int height = (int)tileset.getHeight();
				
				
				WritableImage dest = new WritableImage(width,height);
				PixelWriter writer = dest.getPixelWriter();
				
				for(int x = 0; x< width; x++){
					for(int y = 0; y< height; y++){
						writer.setArgb(x, y, subimage.getRGB(x, y));
					}
	
				}
				
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void drawImage(GraphicsContext gc) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				gc.drawImage(
					tiles[r][c].getImage(),
					x + col * tileSize,
					y + row * tileSize
				);
			}
		}
	}
}
