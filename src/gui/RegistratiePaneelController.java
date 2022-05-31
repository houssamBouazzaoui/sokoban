package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.GebruikersnaamAlInGebruikException;
import exceptions.VerplichtVeldException;
import exceptions.WachtwoordException;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class RegistratiePaneelController extends GridPane {
//ATTRIBUTEN
	@FXML
	private Label lblRegistratie;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private Label lblVoornaam;
	@FXML
	private Label lblAchternaam;
	@FXML
	private Button btnRegistreren;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private TextField txfVoornaam;
	@FXML
	private TextField txfAchternaam;
	@FXML
	private PasswordField pwdfWachtwoord;
	@FXML
	private Hyperlink hlAnnuleren;
	
	private DomeinController dc;
	private ResourceBundle rb;

//CONSTRUCTOR
	public RegistratiePaneelController(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistratiePaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		lblGebruikersnaam.setText(rb.getString("Gebruikersnaam"));
		lblWachtwoord.setText(rb.getString("Wachtwoord"));
		lblVoornaam.setText(rb.getString("Voornaam"));
		lblAchternaam.setText(rb.getString("Achternaam"));
		hlAnnuleren.setText(rb.getString("Annuleren"));
		btnRegistreren.setText(rb.getString("Registreren"));
	}

//METHODES

	// Event Listener on Hyperlink[#hlAnnuleren].onAction
		@FXML
		public void hlAnnulerenAfhandeling(ActionEvent event) {
			Stage stage = (Stage) this.getScene().getWindow();
			Scene scene = new Scene(new AanmeldPaneelController(dc, rb));
			stage.setScene(scene);
		}

	// Event Listener on Button[#btnRegistreren].onAction
	@FXML
	public void btnRegistrerenAfhandeling(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("");
		try {
			dc.registreerSpeler(txfGebruikersnaam.getText(), pwdfWachtwoord.getText(), txfAchternaam.getText(),
					txfVoornaam.getText());
			Stage stage = (Stage) this.getScene().getWindow();
			Scene scene = new Scene(new AanmeldPaneelController(dc, rb));
			stage.setScene(scene);
		} catch (VerplichtVeldException | GebruikersnaamAlInGebruikException | WachtwoordException e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}
}
