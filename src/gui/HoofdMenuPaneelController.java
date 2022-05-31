package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HoofdMenuPaneelController extends VBox {
//ATTRIBUTEN
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblSpelerNaam;
	@FXML
	private Button btnSpeelSpel;
	@FXML
	private Button btnMaakNieuwSpel;
	@FXML
	private Button btnWijzigSpel;
	@FXML
	private Button btnAfsluiten;
	private final DomeinController dc;
	private final ResourceBundle rb;

//CONSTRUCTOR
	public HoofdMenuPaneelController(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdMenuPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		lblSpelerNaam.setText(dc.geefGebruikersnaam());
		btnSpeelSpel.setText(rb.getString("Speel_spel"));
		btnAfsluiten.setText(rb.getString("Afsluiten"));
		btnMaakNieuwSpel.setText(rb.getString("Maak_nieuw_spel"));
		btnWijzigSpel.setText(rb.getString("Wijzig_een_spel"));

		if(!dc.isSpelerAdmin()) {
			btnMaakNieuwSpel.setVisible(false);
			btnWijzigSpel.setVisible(false);
		}
	}

//METHODES
	// Event Listener on Button[#btnSpeelSpel].onAction
	@FXML
	public void btnSpeelSpelAfhandeling(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new KeuzeSpelPaneelController(dc, rb));
		stage.setScene(scene);
	}

	// Event Listener on Button[#btnMaakNieuwSpel].onAction
	@FXML
	public void btnMaakNieuwSpelAfhandeling(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new MaakSpelPaneelController(dc, rb));
		stage.setScene(scene);
	}

	// Event Listener on Button[#btnWijzigSpel].onAction
	@FXML
	public void btnWijzigSpelAfhandeling(ActionEvent event) {
		Scene scene = new Scene(new WijzigSpelPaneelController (dc, rb));
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
	}

	// Event Listener on Button[#btnAfsluiten].onAction
	@FXML
	public void btnAfsluitenAfhandeling(ActionEvent event) {
		Platform.exit();

	}
}
