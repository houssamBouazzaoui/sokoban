package domein;

import java.util.List;
import java.util.ResourceBundle;

import exceptions.IllegalMoveException;

public class DomeinController {

// ATTRIBUTEN
	private SpelerRepository spelerRepo;
	private SpelRepository spelRepo;
	private Menu menu;

	private Speler speler;
	private Spel spel;
	private Spelbord huidigSpelbord;

// CONSTRUCTORS
	public DomeinController() {
		spelerRepo = new SpelerRepository();
		spelRepo = new SpelRepository();
		menu = new Menu();
	}

// METHODES
	/* UC1: Aanmelden */
	public void meldAan(String gebruikersnaam, String wachtwoord) {
		Speler gevondenSpeler = spelerRepo.geefSpeler(gebruikersnaam, wachtwoord);

		setSpeler(gevondenSpeler);
	}

	public String geefGebruikersnaam() {
		return speler.getGebruikersnaam();
	}

	public String[] geefKeuzeMogelijkheden(ResourceBundle resourcebundel) {
		return menu.geefKeuzeMogelijkheden(speler.isAdmin(), resourcebundel);
	}
	
	/* UC2: Registreren */
	public void registreerSpeler(String gebruikersnaam, String wachtwoord, String naam, String voornaam) {
		Speler nieuweSpeler = new Speler(gebruikersnaam, wachtwoord, naam, voornaam);
		spelerRepo.voegToe(nieuweSpeler);
		setSpeler(nieuweSpeler);
	}
	
	public boolean isSpelerAdmin() {
		return speler.isAdmin();
	}

	/* UC3: Speel spel */
	public List<String> geefSpelNamen() {
		return spelRepo.geefSpelNamen();
	}

	public void kiesSpel(String naam) {
		setSpel(new Spel(naam, spelRepo.geefSpelborden(naam)));
	}

	public int[] geefVoortgang() {
		return new int[] { spel.geefAantalVoltooideSpelborden(), spel.geefAantalSpelborden() };
	}

	public boolean isEindeSpel() {
		return spel.isEindeSpel();
	}

	/* UC4: Voltooi spelbord */
	public void startVolgendSpelbord() {
		huidigSpelbord = spel.geefVolgendOnvoltooidSpelbord();

		Veld[][] velden = spelRepo.geefVelden(spel.getNaam(), huidigSpelbord.getNummer());
		huidigSpelbord.laadVeldenIn(velden);
	}

	public char[][] geefWeergaveSpelbord() {
		return huidigSpelbord.geefWeergave();
	}

	public int geefAantalVerplaatsingen() {
		return huidigSpelbord.getAantalVerplaatsingen();
	}

	public boolean isEindeSpelbord() {
		if (huidigSpelbord.bevatElkVeldMetDoelKist()) {
			huidigSpelbord.setVoltooid(true);
			return true;
		}

		return false;
	}

	public void verplaatsMannetje(int dx, int dy) throws IllegalMoveException {
		huidigSpelbord.verplaatsMannetje(dx, dy);
	}

	public String geefSpelbordNaam() {
		return String.format("%s - %d", spel.getNaam(), huidigSpelbord.getNummer());
	}

	/* UC5: Maak nieuw spel */
	public void maakSpel(String naam) {
		setSpel(new Spel(naam));
	}

	public String[] geefInfoSpel() {
		String[] info = { spel.getNaam(), Integer.toString(spel.geefAantalSpelborden()) };
		return info;
	}

	public void registreerNieuwSpel() {
		spelRepo.registreerNieuwSpel(spel, speler.getSpelerId());
	}

	// DR_SPEL_NAAM
	public boolean isGeldig(String spelNaam) {
		return !spelNaam.matches(".* .*") && !spelRepo.geefSpelNamen().contains(spelNaam);
	}

	/* UC6: Maak nieuw spelbord */
	public void startMaakNieuwSpelbord() {
		Spelbord spelbord = new Spelbord();
		setHuidigSpelbord(spelbord);
	}

	public void pasVeldAan(int x, int y, int actie) {
		huidigSpelbord.pasVeldAan(x, y, actie);
	}

	public void voegSpelbordToeAanSpel() {
		spel.voegSpelbordToe(huidigSpelbord);
	}

	/* UC7: Wijzig spelbord */
	public List<String> geefSpelNamenVanSpeler(String gebruikersnaam) {
		return spelRepo.geefSpelNamenVanSpeler(gebruikersnaam);
	}

	public void kiesSpelVoorWijziging(String naam) {
		setSpel(new Spel(naam, spelRepo.geefSpelborden(naam)));
	}

	public int[] geefSpelbordNummers() {
		return spel.geefSpelbordNummers();
	}

	public void kiesSpelbordVoorWijziging(int nummer) {
		String spelnaam = spel.getNaam();
		Spelbord gekozenSpelbord = spel.getSpelborden().get(nummer);
		gekozenSpelbord.laadVeldenIn(spelRepo.geefVelden(spelnaam, gekozenSpelbord.getNummer()));
		setHuidigSpelbord(spel.getSpelborden().get(nummer));
	}

	public void registreerGewijzigdSpel() {
		spelRepo.registreerGewijzigdSpel(spel);
	}

	public void verwijderSpelbord() {
		spelRepo.verwijderSpelbord(spel, huidigSpelbord);
		spel.getSpelborden().remove(huidigSpelbord);
	}

	// DR_SPELBORD
	public void valideerSpelbord() {
		huidigSpelbord.controleerSpelbord();
	}

	// DR_SPEL
	public void valideerSpel() {
		spel.valideerSpel();
	}

// GETTERS EN SETTERS
	private void setSpeler(Speler speler) {
		this.speler = speler;
	}

	private void setSpel(Spel spel) {
		this.spel = spel;
	}

	private void setHuidigSpelbord(Spelbord huidigSpelbord) {
		this.huidigSpelbord = huidigSpelbord;
	}

}