package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class SokobanApplicatie {

// ATTRIBUTEN
	private final DomeinController dc;
	private ResourceBundle rb;
	private DR dr;
	private UC1 uc1;
	private UC2 uc2;
	private UC3_4 uc3_4;
	private UC5_6 uc5_6;
	private UC7_8 uc7_8;

// CONSTRUCTOR
	public SokobanApplicatie(DomeinController dc) {
		this.dc = dc;
	}

// METHODES
	public void start() {

		System.out.println(" _____       _         _                 ");
		System.out.println("/  ___|     | |       | |                ");
		System.out.println("\\ `--.  ___ | | _____ | |__   __ _ _ __ ");
		System.out.println(" `--. \\/ _ \\| |/ / _ \\| '_ \\ / _` | '_ \\");
		System.out.println("/\\__/ / (_) |   < (_) | |_) | (_| | | | |");
		System.out.println("\\____/ \\___/|_|\\_\\___/|_.__/ \\__,_|_| |_|");

		kiesTaal();
		this.dr = new DR(dc, rb);
		this.uc1 = new UC1(dc, rb);
		this.uc2 = new UC2(dc, rb);
		this.uc3_4 = new UC3_4(dc, rb, dr);
		this.uc5_6 = new UC5_6(dc, rb, dr);
		this.uc7_8 = new UC7_8(dc, rb, dr);

		switch (keuzeAanmeldenOfRegistrerenSpeler()) {
		case 1:
			uc1.startAanmelden();
			break;
		case 2:
			uc2.startRegistratie();
			break;
		}

		boolean afsluiten = false;
		do {
			int menuKeuze = dr.geefHoofdMenu();

			if (dc.geefKeuzeMogelijkheden(rb).length == 2) {
				switch (menuKeuze) {
				case 1:
					uc3_4.kiesSpel();
					break;
				case 2:
					afsluiten = true;
				}
			} else {
				switch (menuKeuze) {
				case 1:
					uc3_4.kiesSpel();
					break;
				case 2:
					uc5_6.maakNieuwSpel();
					break;
				case 3:
					uc7_8.wijzigSpel();
					break;
				case 4:
					afsluiten = true;
				}
			}
		} while (!afsluiten);
	}

	/* kiesTaal */
	public void kiesTaal() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		int taalKeuze = 0;
		boolean exception = true;
		do {
			try {
				System.out.println("1. Nederlands");
				System.out.println("2. English");
				System.out.println("3. Français");
				taalKeuze = input.nextInt();

				exception = false;

			} catch (InputMismatchException e) {
				System.out.println("Geef een getal in.");
				input.nextLine();
			}
		} while (taalKeuze > 3 || taalKeuze < 1 || exception);

		switch (taalKeuze) {
		case 1:
			rb = ResourceBundle.getBundle("resources/resource_bundle", new Locale("nl", "BE"));
			break;
		case 2:
			rb = ResourceBundle.getBundle("resources/resource_bundle_en_UK", new Locale("en", "UK"));
			break;
		case 3:
			rb = ResourceBundle.getBundle("resources/resource_bundle_fr_FR", new Locale("fr", "FR"));
			break;
		}
	}

	/* keuzeAanmdeldenOfRegistreren */
	public int keuzeAanmeldenOfRegistrerenSpeler() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		int keuze = 0;
		do {
			System.out.println("1. " + rb.getString("Aanmelden"));
			System.out.println("2. " + rb.getString("Registreren"));

			try {
				keuze = input.nextInt();

			} catch (InputMismatchException e) {
				System.out.println(rb.getString("Geef_een_getal_in."));
				input.next();
			}
		} while (keuze < 1 || 2 < keuze);

		return keuze;
	}

}