package cz.zcu.fjp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class launches GUI and starts controller
 */
public class MainFrame extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
			Scene scene = new Scene(root, 1024, 768);
			primaryStage.setTitle("PL/0 interpreter");
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(800);
			scene.getStylesheets().add(getClass().getResource("bootstrap3.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
