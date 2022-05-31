package cui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.IllegalMoveException;

public class UC3_4 {

// ATTRIBUTEN
	private DomeinController dc;
	private ResourceBundle rb;
	private DR dr;

	private boolean forfait;

// CONSTRUCTORS
	public UC3_4(DomeinController dc, ResourceBundle rb, DR dr) {
		this.dc = dc;
		this.rb = rb;
		this.dr = dr;
	}

// METHODES

//UC3: Speel spel
	public void kiesSpel() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		int spelKeuze = 0;
		boolean exception = true;

		do {
			try {
				System.out.println(rb.getString("Kies_een_van_de_volgende_spellen"));
				List<String> spelNamen = dc.geefSpelNamen();

				int teller = 1;
				for (String spelNaam : spelNamen) {
					System.out.printf("%d.%s%n", teller, spelNaam);
					teller++;
				}

				do {
					spelKeuze = input.nextInt();
				} while (spelKeuze < 1 || spelNamen.size() < spelKeuze);

				exception = false;

			} catch (InputMismatchException e) {
				System.out.println(rb.getString("Geef_een_getal_in."));
				input.nextLine();
			}
		} while (exception);

		dc.kiesSpel(dc.geefSpelNamen().get(spelKeuze - 1));
		forfait = false;

		while (!dc.isEindeSpel() && !forfait) {

			switch (dr.actieSpeelSpel()) {
			case 1:
				startVoltooiSpelbord();
				System.out.printf("%s: %d / %d%n", rb.getString("Aantal_voltooide_spelborden"), dc.geefVoortgang()[0],
						dc.geefVoortgang()[1]);
				break;
			case 2:
				forfait = true;
				System.out.println(rb.getString("Je_hebt_het_spel_opgegeven."));
			}

		}

	}

//UC4: Voltooi spelbord
	private void startVoltooiSpelbord() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		dc.startVolgendSpelbord();
		dr.printWeergaveSpelbord();

		do {
			try {
				System.out.print(rb.getString("Welke_richting_wilt_u_uitgaan?"));
				System.out.println(rb.getString("Bewegingstoetsen"));
				char richting = input.next().charAt(0);

				switch (richting) {
				case 'z':
					dc.verplaatsMannetje(0, -1);
					break;
				case 'q':
					dc.verplaatsMannetje(-1, 0);
					break;
				case 's':
					dc.verplaatsMannetje(0, 1);
					break;
				case 'd':
					dc.verplaatsMannetje(1, 0);
					break;
				case 'r':
					dc.startVolgendSpelbord();
					break;
				case 'o':
					forfait = true;
				}
			} catch (IllegalMoveException e) {
				System.out.println(e.getMessage());
			}

			System.out.printf("%s: %d%n", rb.getString("Aantal_verplaatsingen"), dc.geefAantalVerplaatsingen());
			dr.printWeergaveSpelbord();

		} while (!dc.isEindeSpelbord() && !forfait);

		if (forfait)
			System.out.println(rb.getString("Je_hebt_het_spelbord_opgegeven."));
		else {
			System.out.println(rb.getString("U_heeft_het_spelbord_voltooid"));
			System.out.printf("%s: %d%n", rb.getString("Aantal_verplaatsingen"), dc.geefAantalVerplaatsingen());

		}
	}
}
