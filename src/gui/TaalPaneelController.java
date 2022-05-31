package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


import domein.DomeinController;
import javafx.event.ActionEvent;

public class TaalPaneelController extends VBox {
//ATTRIBUTEN
	@FXML
	private Button btnNederlands;
	@FXML
	private Button btnEnglish;
	@FXML
	private Button btnFrancais;
	private ResourceBundle rb;
	private final DomeinController dc;
	
//CONSTRUCTOR
	public TaalPaneelController(DomeinController dc) {
		this.dc=dc;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

//METHODES
	// Event Listener on Button[#btnNederlands].onAction
	@FXML
	public void btnNederlandsAfhandeling(ActionEvent event) {
		rb = ResourceBundle.getBundle("resources/resource_bundle", new Locale("nl", "BE"));
		AanmeldPaneelOpvragen();
	}

	// Event Listener on Button[#btnEnglish].onAction
	@FXML
	public void btnEnglishAfhandeling(ActionEvent event) {
		rb = ResourceBundle.getBundle("resources/resource_bundle_en_UK", new Locale("en", "UK"));
		AanmeldPaneelOpvragen();
	}

	// Event Listener on Button[#btnFrancais].onAction
	@FXML
	public void btnFrancaisAfhandeling(ActionEvent event) {
		rb = ResourceBundle.getBundle("resources/resource_bundle_fr_FR", new Locale("fr", "FR"));
		AanmeldPaneelOpvragen();
	}
	private void AanmeldPaneelOpvragen() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new AanmeldPaneelController(dc, rb));
		stage.setScene(scene);
	}
}
