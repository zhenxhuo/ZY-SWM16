package com.neet.DiamondHunter.MapViewer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.DiamondHunter.Entity.Entity;
import com.neet.DiamondHunter.Manager.Content;
import com.neet.DiamondHunter.TileMap.TileMap;

public class Pointer extends Entity{
	
	//sprites
	private BufferedImage[] point;
	
	private boolean hasBoat;
	private boolean hasAxe;

	public Pointer(TileMap tm){
		
		super(tm);

		width	= 16;
		height	= 16;
		cwidth	= 12;
		cheight	= 12;
		
		moveSpeed = 2;

		point = Content.PLAYER[0];

		animation.setFrames(point);
		animation.setDelay(10);
	}
	
	public void gotBoat(){
		hasBoat = true;
		tileMap.replace(22, 4);
	}
	public void gotAxe(){
		hasAxe = true;
	}
	public boolean hasBoat(){ return hasBoat; }
	public boolean hasAxe(){ return hasAxe; }
	
	//Keyboard Inputs. Moves pointer
	public void setDown() {
		super.setDown();
	}
	public void setLeft() {
		super.setLeft();
	}
	public void setRight() {
		super.setRight();
	}
	public void setUp() {
		super.setUp();
	}
	
	// Draw Pointer
	public void draw(Graphics2D g){
		super.draw(g);
	}
}
