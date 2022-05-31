package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class KeuzeSpelPaneelController extends GridPane {
//ATTRIBUTEN
	@FXML
	private Label lblKeuzeSpel;
	@FXML
	private Button btnHoofdmenu;
	@FXML
	private Button btnSpeel;
	@FXML
	private ListView<String> lvSpelLijst;
	@FXML
	private Label lblGekozenSpel;
	@FXML
	private Label lblGekozenSpelNaam;

	private final DomeinController dc;
	private final ResourceBundle rb;

// CONSTRUCTOR
	public KeuzeSpelPaneelController(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("KeuzeSpelPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		lblKeuzeSpel.setText(rb.getString("Kies_een_van_de_volgende_spellen"));
		lblGekozenSpel.setText(rb.getString("Gekozen_Spel"));
		btnHoofdmenu.setText(rb.getString("Hoofdmenu"));
		btnSpeel.setText(rb.getString("Speel_spel"));
		
		ObservableList<String> items = FXCollections.observableArrayList(dc.geefSpelNamen());
		lvSpelLijst.setItems(items);

		lvSpelLijst.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				lblGekozenSpelNaam.setText(lvSpelLijst.getSelectionModel().getSelectedItem());
			}
		});
	}

// METHODES
	// Event Listener on Button[#btnHoofdmenu].onAction
	@FXML
	public void btnHoofdmenuAfhandeling(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new HoofdMenuPaneelController(dc, rb));
		stage.setScene(scene);
	}

	// Event Listener on Button[#btnSpeel].onAction
	@FXML
	public void btnSpeelAfhandeling(ActionEvent event) {

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("");
		try {
			if (lblGekozenSpelNaam.getText() == null || lblGekozenSpelNaam.getText().isEmpty()) {
				throw new IllegalArgumentException(rb.getString("Je_moet_een_spel_kiezen_uit_de_lijst"));
			} else {
				dc.kiesSpel(lblGekozenSpelNaam.getText());
				Stage stage = (Stage) this.getScene().getWindow();
				Scene scene = new Scene(new SpeelSpelbordPaneelController(dc, rb));
				stage.setScene(scene);

			}
		} catch (IllegalArgumentException e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}

	}
}
