package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.GebruikersnaamAlInGebruikException;
import exceptions.VerplichtVeldException;
import exceptions.WachtwoordException;

public class UC2 {
// ATTRIBUTEN
	private DomeinController dc;
	private ResourceBundle rb;

// CONSTRUCTORS
	public UC2(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
	}

// METHODES
	/* Registreren */
	public void startRegistratie() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		boolean exception = true;
		do {
			try {
				System.out.println(rb.getString("Voer_uw_gebruikersnaam_in"));
				// TODO probleem met spatie oplossen
				String gebruikersnaam = input.next();

				System.out.println(rb.getString("Voer_uw_wachtwoord_in"));
				String wachtwoord = input.next();

				System.out.println(rb.getString("Voer_uw_familienaam_in"));
				String naam = input.next();
				naam += input.nextLine();

				System.out.println(rb.getString("Voer_uw_voornaam_in"));
				String voornaam = input.next();
				voornaam += input.nextLine();

				dc.registreerSpeler(gebruikersnaam, wachtwoord, naam, voornaam);
				exception = false;

			} catch (VerplichtVeldException | GebruikersnaamAlInGebruikException | WachtwoordException e) {
				System.out.println(e.getMessage());
			}
		} while (exception);
	}
}
