package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.VoegSpelbordToeException;

public class UC5_6 {
// ATTRIBUTEN
	private DomeinController dc;
	private ResourceBundle rb;
	private DR dr;

// CONSTRUCTORS
	public UC5_6(DomeinController dc, ResourceBundle rb, DR dr) {
		this.dc = dc;
		this.rb = rb;
		this.dr = dr;
	}

// METHODES
//UC5: Maak nieuw spel
	public void maakNieuwSpel() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		String spelNaam = "";
		do {
			System.out.println(rb.getString("Geef_een_naam_voor_het_nieuwe_spel"));
			spelNaam = input.next();
		} while (!dc.isGeldig(spelNaam));

		dc.maakSpel(spelNaam);

		boolean exception = true;
		int nogSpelBord = 1;
		do {
			maakNieuwSpelbord();

			do {
				try {
					System.out.println(rb.getString("Wilt_u_nog_een_spelbord_aanmaken?"));
					System.out.println(rb.getString("Ja,_Nee"));
					nogSpelBord = input.nextInt();
					exception = false;
				} catch (InputMismatchException e) {
					System.out.println(rb.getString("Geef_een_getal_in."));
					input.nextLine();
				}
			} while (exception || nogSpelBord < 0 || 1 < nogSpelBord);
		} while (nogSpelBord == 1);

		try {
			dc.registreerNieuwSpel();
		} catch (VoegSpelbordToeException e) {
			System.out.println(e.getMessage());
		}

	}

	/* UC6: Maak nieuw spelbord */
	private void maakNieuwSpelbord() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		dc.startMaakNieuwSpelbord();

		boolean verderAanpassenFlag = false;

		do {
			int doordoen = 1;
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

				//Doorgaan of niet
				boolean exceptionDoordoen = true;
				do {
					try {
						System.out.println(rb.getString("Wilt_u_het_spelbord_nog_verder_aanpassen?"));
						System.out.println(rb.getString("Ja,_Nee"));
						doordoen = input.nextInt();
						exceptionDoordoen = false;
					} catch (InputMismatchException e) {
						System.out.println(rb.getString("Geef_een_getal_in."));
						input.nextLine();
					}
				} while (doordoen < 0 || 1 < doordoen || exceptionDoordoen);
			} while (doordoen != 0);

			// Spelbord toevoegen aan Spel
			try {
				dc.valideerSpelbord();
				dc.voegSpelbordToeAanSpel();
				verderAanpassenFlag = false;
			} catch (VoegSpelbordToeException e) {
				System.out.println(e.getMessage());
				System.out.println(rb.getString("Wat_wilt_u_doen_met_dit_ongeldige_spelbord?"));
				System.out.println("1. " + rb.getString("Het_spelbord_verder_aanpassen."));
				System.out.println("2. " + rb.getString("Het_spelbord_verwerpen."));

				switch (input.nextInt()) {
				case 1:
					verderAanpassenFlag = true;
					break;
				case 2:
					verderAanpassenFlag = false;
					break;
				}
			}
		} while (verderAanpassenFlag);

	}
}
