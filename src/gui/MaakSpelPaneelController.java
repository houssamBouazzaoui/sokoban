package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class MaakSpelPaneelController extends HBox {
// ATTRIBUTEN
	@FXML
	private Label lblSpelnaam;
	@FXML
	private TextField txfSpelnaam;
	@FXML
	private Button btnOK;

	private final DomeinController dc;
	private final ResourceBundle rb;

// CONSTRUCTOR
	public MaakSpelPaneelController(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MaakSpelPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		lblSpelnaam.setText(rb.getString("Geef_een_naam_voor_het_nieuwe_spel"));
	}

// METHODES

	// Event Listener on Button[#btnOK].onAction
	@FXML
	public void btnOKAfhandeling(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("");

		try {
			if (txfSpelnaam.getText() == null || txfSpelnaam.getText().isEmpty())
				throw new IllegalArgumentException(rb.getString("Je_moet_een_spelnaam_ingeven"));

			if (!dc.isGeldig(txfSpelnaam.getText()))
				throw new IllegalArgumentException("**Je moet een geldige spelnaam ingeven.**");

			dc.maakSpel(txfSpelnaam.getText());
			Stage stage = (Stage) this.getScene().getWindow();
			Scene scene = new Scene(new MaakSpelbordPaneelController(dc, rb));
			stage.setScene(scene);

		} catch (IllegalArgumentException e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
