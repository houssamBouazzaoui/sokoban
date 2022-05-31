package cui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class DR {
// ATTRIBUTEN
	private DomeinController dc;
	private ResourceBundle rb;

// CONSTRUCTORS
	public DR(DomeinController dc, ResourceBundle rb) {
		this.dc = dc;
		this.rb = rb;
	}

// METHODES

	/* UC1-2: DR_HOOFDMENU */
	public int geefHoofdMenu() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		int menuKeuze = 0;
		String[] keuzes = null;

		boolean exception = true;
		do {
			try {
				System.out.println(dc.geefGebruikersnaam());

				keuzes = dc.geefKeuzeMogelijkheden(rb);
				for (String keuze : keuzes) {
					System.out.println(keuze);
				}
				menuKeuze = input.nextInt();
				exception = false;
			} catch (InputMismatchException e) {
				System.out.println(rb.getString("Geef_een_getal_in."));
				input.nextLine();
			}
		} while (menuKeuze > keuzes.length || menuKeuze < 1 || exception);

		return menuKeuze;
	}

	/* UC3: DR_ACTIES_SPEL */
	public int actieSpeelSpel() {
		Scanner input = new Scanner(System.in);
		boolean exception = true;
		int keuzeActie = 0;
		do {
			try {
				System.out.println("1. "+rb.getString("Voltooi_volgend_spelbord"));
				System.out.println("2. "+rb.getString("Spel_verlaten"));
				keuzeActie = input.nextInt();
				exception = false;
			} catch (InputMismatchException e) {
				System.out.println(rb.getString("Geef_een_getal_in."));
				input.nextLine();
			}
		} while (keuzeActie > 2 || keuzeActie < 1 || exception);

		return keuzeActie;
	}

	/* UC4, 6, 8: DR_WEERGAVE_SPELBORD */
	public void printWeergaveSpelbord() {
		char[][] spelbord = dc.geefWeergaveSpelbord();

		for (char[] rij : spelbord) {
			for (char veld : rij) {
				System.out.printf(" %c ", veld);
			}
			System.out.println();
		}
	}
}
