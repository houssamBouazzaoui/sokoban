package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.FouteInlogGegevensException;

public class UC1 {
// ATTRIBUTEN
	private DomeinController dc;
	private ResourceBundle rb;

// CONSTRUCTORS
	public UC1(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
	}

// METHODES
	/* Aanmelden */
	public void startAanmelden() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		boolean exception = true;
		do {
			try {
				System.out.println(rb.getString("Voer_uw_gebruikersnaam_in") + " (Admin123)");
				// TODO probleem met spatie oplossen
				String gebruikersnaam = input.next();

				System.out.println(rb.getString("Voer_uw_wachtwoord_in") + " (Admin123)");
				String wachtwoord = input.next();

				dc.meldAan(gebruikersnaam, wachtwoord);

				exception = false;

			} catch (FouteInlogGegevensException e) {
				System.out.println(rb.getString("Gebruikersnaam_of_wachtwoord_is_fout_Probeer_opnieuw."));
			}
		} while (exception);

	}
}
