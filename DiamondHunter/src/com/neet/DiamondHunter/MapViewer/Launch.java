package com.neet.DiamondHunter.MapViewer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

public class Launch extends Application{
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)throws Exception{
		primaryStage.setTitle("Diamond Hunter Map Viewer");
		Group base = new Group();

		Scene scene = new Scene(base,640,480);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
