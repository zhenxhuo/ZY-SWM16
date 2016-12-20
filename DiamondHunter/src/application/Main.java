package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import com.neet.DiamondHunter.TileMap.TileMap;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MapView.fxml"));
		primaryStage.setTitle("MapView");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
