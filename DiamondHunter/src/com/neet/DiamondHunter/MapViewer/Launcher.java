//Map Viewer main class
//Application Launcher

package com.neet.DiamondHunter.MapViewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Launcher extends Application{

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)throws Exception{
		try{
			Parent base = FXMLLoader.load(getClass().getResource("MapView.fxml"));
			primaryStage.setTitle(" Map Viewer");

			Scene scene = new Scene(base);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

		}catch(Exception e) {e.printStackTrace();}
	}
}
