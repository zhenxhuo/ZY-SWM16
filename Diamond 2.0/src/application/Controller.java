package application;

import com.neet.DiamondHunter.MapViewer.Layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
	Stage primaryStage;
	Button button;
	
	public void handle(ActionEvent event){
		
	}
	@FXML
		public void start(ActionEvent event){
			Group base = new Group();
			Layout draw = new Layout();
			base.getChildren().add(draw);
			Scene scene = new Scene(base,750,750);
			primaryStage.setScene(scene);
			primaryStage.show();
		
		}

}
			
		
	

	
	

