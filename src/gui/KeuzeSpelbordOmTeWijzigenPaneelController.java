package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Spel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class KeuzeSpelbordOmTeWijzigenPaneelController extends GridPane {
	@FXML
	private Label lblKeuzeSpelbord;
	@FXML
	private Button btnHoofdmenu;
	@FXML
	private Button btnWijzigSpelbord;
	@SuppressWarnings("rawtypes")
	@FXML
	private ListView lvSpelbordLijst;
	@FXML
	private Label lblGekozenSpelbord;
	@FXML
	private Label lblGekozenSpelbordNaam;
	@FXML
	private Button btnVerwijderSpelbord;

	private final DomeinController dc;
	private final ResourceBundle rb;

	@SuppressWarnings("unchecked")
	public KeuzeSpelbordOmTeWijzigenPaneelController(DomeinController dc, ResourceBundle rb) {

		super();

		this.dc = dc;
		this.rb = rb;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("KeuzeSpelbordOmTeWijzigenPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		lblKeuzeSpelbord.setText(rb.getString("Kies_een_van_de_volgende_spelborden"));
		lblGekozenSpelbord.setText(rb.getString("Gekozen_Spelbord"));
		btnHoofdmenu.setText(rb.getString("Hoofdmenu"));
		btnVerwijderSpelbord.setText(rb.getString("Verwijder_spelbord"));
		btnWijzigSpelbord.setText(rb.getString("Wijzig_spelbord"));

		@SuppressWarnings("rawtypes")
		List<String> Spelborden = new ArrayList();
		for (int nummer : dc.geefSpelbordNummers()) {
			Spelborden.add(String.valueOf(nummer));
		}

		ObservableList<String> items = FXCollections.observableArrayList(Spelborden);
		lvSpelbordLijst.setItems(items);
		lvSpelbordLijst.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				lblGekozenSpelbordNaam.setText((String) lvSpelbordLijst.getSelectionModel().getSelectedItem());
			}
		});
	}

	// Event Listener on Button[#btnHoofdmenu].onAction
	@FXML
	public void btnHoofdmenuAfhandeling(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new HoofdMenuPaneelController(dc, rb));
		stage.setScene(scene);
	}

	// Event Listener on Button[#btnSpeel].onAction
	@FXML
	public void btnWijzigSpelbordAfhandeling(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("");
		try {
			if (lblGekozenSpelbordNaam.getText() == null || lblGekozenSpelbordNaam.getText().isEmpty()) {

				throw new IllegalArgumentException(rb.getString("Je_moet_een_spelbord_kiezen_uit_de_lijst"));
			} else {
				dc.kiesSpelbordVoorWijziging(Integer.parseInt(lblGekozenSpelbordNaam.getText()) - 1);
				Stage stage = (Stage) this.getScene().getWindow();
				Scene scene = new Scene(new WijzigSpelbordPaneelController(dc, rb));
				stage.setScene(scene);

			}
		} catch (IllegalArgumentException e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	// Event Listener on Button[#btnVerwijderSpelbord].onAction
	@SuppressWarnings("unchecked")
	@FXML
	public void btnVerwijderSpelbordAfhandeling(ActionEvent event) {
		if (lblGekozenSpelbordNaam.getText() == null || lblGekozenSpelbordNaam.getText().isEmpty()) {
			throw new IllegalArgumentException(rb.getString("Je_moet_een_spelbord_kiezen_uit_de_lijst"));
		} else {
			dc.kiesSpelbordVoorWijziging(Integer.parseInt(lblGekozenSpelbordNaam.getText()) - 1);

			if (dc.geefSpelbordNummers().length > 1) {
				dc.verwijderSpelbord();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(rb.getString("U_kan_momenteel_geen_spelbord_verwijderen."));
				alert.showAndWait();
			}
			
			@SuppressWarnings("rawtypes")
			List<String> Spelborden = new ArrayList();
			for (int nummer : dc.geefSpelbordNummers()) {
				Spelborden.add(String.valueOf(nummer));
			}

			ObservableList<String> items = FXCollections.observableArrayList(Spelborden);
			lvSpelbordLijst.setItems(items);

		}

	}
}
