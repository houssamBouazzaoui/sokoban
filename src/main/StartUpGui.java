package main;

import domein.DomeinController;
import gui.TaalPaneelController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {

		DomeinController dc = new DomeinController();
		TaalPaneelController taalPaneel = new TaalPaneelController(dc);

		Scene scene = new Scene(taalPaneel);
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(primaryStage.getWidth());
		primaryStage.setMinHeight(primaryStage.getHeight());
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
