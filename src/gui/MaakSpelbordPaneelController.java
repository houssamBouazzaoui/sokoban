package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.VoegSpelbordToeException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MaakSpelbordPaneelController extends BorderPane {
//ATTRIBUTEN
	@FXML
	private Label lblSpelbord;
	@FXML
	private GridPane gpSpelbord;
	@FXML
	private Label lblInstructies;
	@FXML
	private Button btnKlaar;
	@FXML
	private Button btnHoofdMenu;
	@FXML
	private Button btnMuur;
	@FXML
	private Button btnKist;
	@FXML
	private Button btnDoel;
	@FXML
	private Button btnMannetje;
	@FXML
	private Button btnLeeg;

	private final DomeinController dc;
	private final ResourceBundle rb;

	private int actie;

// CONSTRUCTOR
	public MaakSpelbordPaneelController(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;

		dc.startMaakNieuwSpelbord();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MaakSpelbordPaneel.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		lblSpelbord.setText(dc.geefSpelbordNaam());

		btnMuur.setText(rb.getString("Muur"));
		btnMuur.fire();
		btnKist.setText(rb.getString("Kist"));
		btnDoel.setText(rb.getString("Doel"));
		btnMannetje.setText(rb.getString("Mannetje"));
		btnLeeg.setText(rb.getString("Leeg"));

		lblInstructies.setText(rb.getString("Instructies_spelbord_opstellen"));

		weergaveSpelbord();
	}

// METHODES
	private void weergaveSpelbord() {
		char[][] spelbord = dc.geefWeergaveSpelbord();
		int veldRij = 0;
		int veldKolom = 0;

		for (char[] rij : spelbord) {
			for (char veld : rij) {
				ImageView imgVeld = new ImageView(new Image(
						getClass().getResourceAsStream(String.format("/images/%s.png", veld)), 25, 25, true, false));

				gpSpelbord.add(imgVeld, veldKolom, veldRij);
				imgVeld.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						dc.pasVeldAan(gpSpelbord.getColumnIndex((ImageView) event.getSource()),
								gpSpelbord.getRowIndex((ImageView) event.getSource()), actie);
						weergaveSpelbord();
						event.consume();
					}
				});

				veldKolom++;
			}
			veldRij++;
			veldKolom = 0;
		}

	}

	// Event Listener on Button[#btnKlaar].onAction
	@FXML
	public void btnKlaarAfhandeling(ActionEvent event) {
		try {
			dc.valideerSpelbord();
			dc.voegSpelbordToeAanSpel();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText(rb.getString("Spelbord_toegevoegd"));
			alert.setContentText(rb.getString("Wilt_u_nog_een_spelbord_aanmaken?"));

			ButtonType Ja = new ButtonType(rb.getString("Ja"), ButtonData.YES);
			ButtonType Nee = new ButtonType(rb.getString("Nee"), ButtonData.NO);
			
			alert.getButtonTypes().setAll(Ja, Nee);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == Ja) {
				// Nieuw spelbord maken
				Stage stage = (Stage) this.getScene().getWindow();
				Scene scene = new Scene(new MaakSpelbordPaneelController(dc, rb));
				stage.setScene(scene);

			} else {
				registreerSpel();
				hoofdmenuOproepen();
			}

		} catch (VoegSpelbordToeException e) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText(e.getMessage());
			alert.setContentText(rb.getString("Wat_wilt_u_doen_met_dit_ongeldige_spelbord?"));

			ButtonType verderAanpassen = new ButtonType(rb.getString("Verder_aanpassen"), ButtonData.YES);
			ButtonType verwerpen = new ButtonType(rb.getString("Verwerpen"), ButtonData.NO);
			alert.getButtonTypes().setAll(verderAanpassen, verwerpen);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				alert.close();
			} else {
				registreerSpel();
				hoofdmenuOproepen();
			}

		}

	}

	private void registreerSpel() {
		// Spel registreren in DB
		try {
			dc.registreerNieuwSpel();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText(dc.geefInfoSpel()[0]);
			alert.setContentText(rb.getString("Aantal_spelborden") + dc.geefInfoSpel()[1]);

		} catch (VoegSpelbordToeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void hoofdmenuOproepen() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new HoofdMenuPaneelController(dc, rb));
		stage.setScene(scene);
	}

	// Event Listener on Button[#btnHoofdMenu].onAction
	@FXML
	public void btnHoofdMenuAfhandeling(ActionEvent event) {
		hoofdmenuOproepen();
	}

	// Event Listener on Button[#btnMuur].onAction
	@FXML
	public void btnMuurAfhandeling(ActionEvent event) {
		this.actie = 1;
	}

	// Event Listener on Button[#btnKist].onAction
	@FXML
	public void btnKistAfhandeling(ActionEvent event) {
		this.actie = 2;
	}

	// Event Listener on Button[#btnDoel].onAction
	@FXML
	public void btnDoelAfhandeling(ActionEvent event) {
		this.actie = 3;
	}

	// Event Listener on Button[#btnMannetje].onAction
	@FXML
	public void btnMannetjeAfhandeling(ActionEvent event) {
		this.actie = 4;
	}

	// Event Listener on Button[#btnLeeg].onAction
	@FXML
	public void btnLeegAfhandeling(ActionEvent event) {
		this.actie = 5;
	}
}
