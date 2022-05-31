package cui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.VoegSpelbordToeException;

public class UC7_8 {
// ATTRIBUTEN
	private DomeinController dc;
	private ResourceBundle rb;
	private DR dr;

// CONSTRUCTORS
	public UC7_8(DomeinController dc, ResourceBundle rb, DR dr) {
		this.dc = dc;
		this.rb = rb;
		this.dr = dr;
	}

// METHODES
//UC7: Wijzig spel
	public void wijzigSpel() {

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		boolean repeatFlagBlok1 = true;
		boolean repeatFlagBlok2 = true;

		// Spel kiezen
		do {
			try {
				List<String> spelNamen = dc.geefSpelNamenVanSpeler(dc.geefGebruikersnaam());

				System.out.println(rb.getString("Spellen"));
				int teller = 1;
				for (String naam : spelNamen) {
					System.out.printf("%d. %s%n", teller, naam);
					teller++;
				}

				int index;
				do {
					System.out.println(rb.getString("Welk_spel_wilt_u_aanpassen?"));
					index = input.nextInt() - 1;
				} while (index < 0 || spelNamen.size() < index);

				String naam = spelNamen.get(index);
				dc.kiesSpelVoorWijziging(naam);
				repeatFlagBlok1 = false;
			} catch (InputMismatchException e) {
				System.out.println(rb.getString("Geef_een_getal_in."));
				input.nextLine();
			}
		} while (repeatFlagBlok1);

		// Spelbord kiezen
		int nogSpelbord;

		do {
			int spelbordNummer;
			int[] nummers = dc.geefSpelbordNummers();
			do {
				try {
					System.out.println(rb.getString("Spelborden"));
					int teller = 1;
					for (int nummer : nummers) {
						System.out.printf("%d. " + rb.getString("Spelbord") + " %d%n", teller, nummer);
						teller++;
					}
					do {
						System.out.println(rb.getString("Welk_spelbord_wilt_u_aanpassen?"));
						spelbordNummer = input.nextInt();
					} while (spelbordNummer < 1 || spelbordNummer > nummers.length);
					dc.kiesSpelbordVoorWijziging(spelbordNummer - 1);
					repeatFlagBlok2 = false;
				} catch (InputMismatchException e) {
					System.out.println(rb.getString("Geef_een_nummer_in."));
					input.nextLine();
				}
			} while (repeatFlagBlok2);

			// Wijzigen of verwijderen
			System.out.println(rb.getString("Wat_wilt_u_doen_met_dit_spelbord?"));
			System.out.println("1. " + rb.getString("Wijzigen"));
			System.out.println("2. " + rb.getString("Verwijderen"));
			int actie = input.nextInt();
			switch (actie) {
			case 1:
				wijzigSpelbord();
				break;
			case 2:
				if (dc.geefSpelbordNummers().length > 1) {
					verwijderSpelbord();
				} else {
					System.out.println(rb.getString("U_kan_momenteel_geen_spelbord_verwijderen."));
				}
			}

			// Doorgaan of stoppen
			System.out.println(rb.getString("Wilt_u_nog_een_spelbord_wijzigen_of_verwijderen?"));
			System.out.println(rb.getString("Ja,_Nee"));
			nogSpelbord = input.nextInt();
		} while (nogSpelbord == 1);

	}

//UC8: Wijzig spelbord
	public void wijzigSpelbord() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		int nogAanpassing = 1;

		
		boolean verderAanpassenFlag = false;

		do {
			do {
				dr.printWeergaveSpelbord();
				
				//Veld aanpassen
				int x = -1, y = -1, actie = 0;
				boolean exception = true;
				do {
					try {
						while (x < 0 || 9 < x) {
							System.out.println(rb.getString("Welk_veld_wilt_u_aanpassen?"));
							System.out.println(rb.getString("Geef_de_x-coördinaat"));
							x = input.nextInt();
						}

						while (y < 0 || 9 < y) {
							System.out.println(rb.getString("Geef_de_y-coördinaat"));
							y = input.nextInt();
						}

						while (actie < 1 || 5 < actie) {
							System.out.println(rb.getString("Wat_wilt_u_doen_met_dit_veld?"));
							System.out.println("1. " + rb.getString("Plaatsen_muur"));
							System.out.println("2. " + rb.getString("Plaatsen_kist"));
							System.out.println("3. " + rb.getString("Plaatsen_doel"));
							System.out.println("4. " + rb.getString("Plaatsen_mannetje"));
							System.out.println("5. " + rb.getString("Veld_leeg_maken"));
							actie = input.nextInt();
						}

						exception = false;

					} catch (InputMismatchException e) {
						System.out.println(rb.getString("Geef_een_getal_in."));
						input.nextLine();
					}
				} while (exception);

				dc.pasVeldAan(x, y, actie);

				// Doorgaan of niet
				boolean exceptionDoordoen = true;
				do {
					try {
						System.out.println(rb.getString("Wilt_u_het_spelbord_nog_verder_aanpassen?"));
						System.out.println(rb.getString("Ja,_Nee"));
						nogAanpassing = input.nextInt();
						exceptionDoordoen = false;

					} catch (InputMismatchException e) {
						System.out.println(rb.getString("Geef_een_getal_in."));
						input.nextLine();
					}
				} while (nogAanpassing < 0 || 1 < nogAanpassing || exceptionDoordoen);

			} while (nogAanpassing != 0);

			// Validatie
			try {
				dc.valideerSpelbord();
				verderAanpassenFlag = false;

			} catch (VoegSpelbordToeException e) {
				System.out.println(e.getMessage());
				System.out.println(rb.getString("Wat_wilt_u_doen_met_dit_ongeldige_spelbord?"));
				System.out.println("1. " + rb.getString("Het_spelbord_verder_aanpassen."));
				System.out.println("2. " + rb.getString("De_wijziginen_aan_het_spelbord_ongedaan_maken."));

				switch (input.nextInt()) {
				case 1:
					verderAanpassenFlag = true;
					break;
				case 2:
					verderAanpassenFlag = false;
				}
			}

		} while (verderAanpassenFlag);

		dc.registreerGewijzigdSpel();
	}

	/* Spelbord verwijderen */
	private void verwijderSpelbord() {
		dc.verwijderSpelbord();
	}
}
