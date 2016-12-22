package com.neet.DiamondHunter.MapViewer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application{
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)throws Exception{
		primaryStage.setTitle("Diamond Hunter Map Viewer");
		Group base = new Group();
		Layout draw = new Layout();
		base.getChildren().add(draw);
		Scene scene = new Scene(base,750,750);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
