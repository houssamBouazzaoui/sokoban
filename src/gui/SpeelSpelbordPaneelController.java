package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.IllegalMoveException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SpeelSpelbordPaneelController extends BorderPane {
//ATTRIBUTEN
	@FXML
	private GridPane gpSpelbord;
	@FXML
	private Label lblSpelbord;
	@FXML
	private Label lblAantalVerplaatsingen;
	@FXML
	private Button btnHerstarten;
	@FXML
	private Button btnOpgeven;
	@FXML
	private Label lblInstructies;

	private final DomeinController dc;
	private final ResourceBundle rb;

// CONSTRUCTOR
	public SpeelSpelbordPaneelController(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SpeelSpelbordPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		dc.startVolgendSpelbord();

		lblSpelbord.setText(dc.geefSpelbordNaam());
		lblAantalVerplaatsingen.setText(Integer.toString(dc.geefAantalVerplaatsingen()));
		btnHerstarten.setText(rb.getString("Herstarten"));
		btnOpgeven.setText(rb.getString("Opgeven"));
		lblInstructies.setText(rb.getString("Bewegingstoetsen_Gui"));

		weergaveSpelbord();

		this.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				try {
					switch (keyEvent.getCode()) {
					case Z:
						dc.verplaatsMannetje(0, -1);
						break;
					case S:
						dc.verplaatsMannetje(0, 1);
						break;
					case Q:
						dc.verplaatsMannetje(-1, 0);
						break;
					case D:
						dc.verplaatsMannetje(1, 0);
						break;
					}
					weergaveSpelbord();

					lblAantalVerplaatsingen.setText(Integer.toString(dc.geefAantalVerplaatsingen()));

					if (dc.isEindeSpelbord()) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("");
						alert.setHeaderText(rb.getString("U_heeft_het_spelbord_voltooid"));
						alert.setContentText(
								rb.getString("Aantal_verplaatsingen") + " " + lblAantalVerplaatsingen.getText());
						ButtonType voltooiVolgendSpelbord = new ButtonType(rb.getString("Voltooi_volgend_spelbord"),
								ButtonData.YES);
						ButtonType spelVerlaten = new ButtonType(rb.getString("Spel_verlaten"), ButtonData.NO);
						alert.getButtonTypes().setAll(voltooiVolgendSpelbord, spelVerlaten);
						alert.showAndWait();

						if (alert.getResult().equals(voltooiVolgendSpelbord)) {
							eindeSpel();
						} else if (alert.getResult().equals(spelVerlaten)) {
							keuzeSpelOproepen();
						}

					}

				} catch (IllegalMoveException e) {
					System.out.println(e.getMessage());
				}
			}
		});

	}

//METHODES
	private void eindeSpel() {
		if (!dc.isEindeSpel()) {
			dc.startVolgendSpelbord();
			weergaveSpelbord();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setContentText(
					rb.getString("Aantal_voltooide_spelborden") +": "+ dc.geefVoortgang()[0] + "/" + dc.geefVoortgang()[1]);
			alert.showAndWait();

			hoofdmenuOproepen();
		}
	}

	private void hoofdmenuOproepen() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new HoofdMenuPaneelController(dc, rb));
		stage.setScene(scene);
	}

	private void keuzeSpelOproepen() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new KeuzeSpelPaneelController(dc, rb));
		stage.setScene(scene);
	}

	private void weergaveSpelbord() {
		char[][] spelbord = dc.geefWeergaveSpelbord();
		int veldRij = 0;
		int veldKolom = 0;

		for (char[] rij : spelbord) {
			for (char veld : rij) {
				ImageView imgVeld = new ImageView(new Image(
						getClass().getResourceAsStream(String.format("/images/%s.png", veld)), 25, 25, true, false));

				gpSpelbord.add(imgVeld, veldKolom, veldRij);

				veldKolom++;
			}
			veldRij++;
			veldKolom = 0;
		}

	}

	// Event Listener on Button[#btnReset].onAction
	@FXML
	public void btnHerstartenAfhandeling(ActionEvent event) {
		dc.startVolgendSpelbord();
		weergaveSpelbord();
		lblAantalVerplaatsingen.setText(Integer.toString(dc.geefAantalVerplaatsingen()));
	}

	// Event Listener on Button[#btnOpgeven].onAction
	@FXML
	public void btnOpgevenAfhandeling(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText(rb.getString("Je_hebt_het_spelbord_opgegeven."));
		alert.showAndWait();

		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new KeuzeSpelPaneelController(dc, rb));
		stage.setScene(scene);

	}
}
