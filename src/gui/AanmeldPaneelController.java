package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.FouteInlogGegevensException;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

import javafx.scene.control.Hyperlink;

public class AanmeldPaneelController extends GridPane {
//ATTRIBUTEN
	@FXML
	private Label lblSokobanTitel;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private Button btnAanmelden;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private PasswordField pwdfWachtwoord;
	@FXML
	private Hyperlink hlRegistreren;
	private final DomeinController dc;
	private final ResourceBundle rb;
	
//CONSTRUCTOR
	public AanmeldPaneelController(DomeinController dc,ResourceBundle rb) {
		this.dc=dc;
		this.rb=rb;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AanmeldPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		lblGebruikersnaam.setText(rb.getString("Gebruikersnaam"));
		lblWachtwoord.setText(rb.getString("Wachtwoord"));
		btnAanmelden.setText(rb.getString("Aanmelden"));
		hlRegistreren.setText(rb.getString("Registreren"));
	}
	
//METHODES
	// Event Listener on Button[#btnAanmelden].onAction
	@FXML
	public void btnAanmeldenAfhandeling(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("");
		try {
			dc.meldAan(txfGebruikersnaam.getText(), pwdfWachtwoord.getText());
			Stage stage = (Stage) this.getScene().getWindow();
			Scene scene = new Scene(new HoofdMenuPaneelController(dc, rb));
			stage.setScene(scene);
		}catch(FouteInlogGegevensException |NullPointerException e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
	}
	// Event Listener on Hyperlink[#hlRegistreren].onAction
	@FXML
	public void hlregistrerenAfhandeling(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new RegistratiePaneelController(dc, rb));
		stage.setScene(scene);
	}
	
		
	
}
