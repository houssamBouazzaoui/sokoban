package domein;

import java.util.ResourceBundle;

public class Menu {
	// METHODES
	public String[] geefKeuzeMogelijkheden(boolean isAdmin,ResourceBundle rb) {
		String[] keuzes;

		if (isAdmin) {
			keuzes = new String[4];
			keuzes[0] = ("1. " + rb.getString("Speel_spel"));
			keuzes[1] = ("2. " + rb.getString("Maak_nieuw_spel"));
			keuzes[2] = ("3. " + rb.getString("Wijzig_een_spel"));
			keuzes[3] = ("4. " + rb.getString("Afsluiten"));
		} else {
			keuzes = new String[2];
			keuzes[0] = ("1. " + rb.getString("Speel_spel"));
			keuzes[1] = ("2. " + rb.getString("Afsluiten"));
		}

		return keuzes;
	}
}
