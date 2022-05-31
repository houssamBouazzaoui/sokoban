package domein;

import exceptions.VerplichtVeldException;
import exceptions.WachtwoordException;

public class Speler {

// ATTRIBUTEN
	private String gebruikersnaam, wachtwoord, naam, voornaam;
	private boolean admin;
	private int spelerId;
// CONSTRUCTORS
	public Speler(String gebruikersnaam, String wachtwoord) {
		this(gebruikersnaam, wachtwoord, "onbekend", "onbekend", false);
	}

	public Speler(String gebruikersnaam, String wachtwoord, String naam, String voornaam) {
		this(gebruikersnaam, wachtwoord, naam, voornaam, false);
	}

	public Speler(String gebruikersnaam, String wachtwoord, String naam, String voornaam, boolean admin) {
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setNaam(naam);
		setVoornaam(voornaam);
		setAdmin(admin);
	}
	
	public Speler(String gebruikersnaam, String wachtwoord,String naam, String voornaam, boolean admin,int spelerId) {
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setNaam(naam);
		setVoornaam(voornaam);
		setAdmin(admin);
		setSpelerId(spelerId);
	}


// METHODES
	private void controleerWachtwoord(String wachtwoord) {
		if (wachtwoord == null || wachtwoord.length() < 8)
			throw new VerplichtVeldException("Een wachtwoord bestaat uit minimum 8 karakters!");

		if (!(bevatHoofdletter(wachtwoord) && bevatKleineLetter(wachtwoord) && bevatCijfer(wachtwoord)))
			throw new WachtwoordException(
					"Een wachtwoord moet minstens 1 hoofdletter, 1 kleine letter en 1 cijfer bevatten!");
	}

	private boolean bevatHoofdletter(String wachtwoord) {
		return wachtwoord.matches(".*[A-Z].*");
	}

	private boolean bevatKleineLetter(String wachtwoord) {
		return wachtwoord.matches(".*[a-z].*");
	}

	private boolean bevatCijfer(String wachtwoord) {
		return wachtwoord.matches(".*[0-9].*");
	}

// GETTERS EN SETTERS
	// naam
	public String getNaam() {
		return naam;
	}

	private void setNaam(String naam) {
		this.naam = naam;
	}

	// voornaam
	public String getVoornaam() {
		return voornaam;
	}

	private void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	// gebruikersNaam
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam == null || gebruikersnaam.length() < 8) {
			throw new VerplichtVeldException("Een gebruikersnaam bestaat uit minimum 8 karakters!");
		}
		this.gebruikersnaam = gebruikersnaam;
	}

	// wachtWoord
	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		controleerWachtwoord(wachtwoord);
		this.wachtwoord = wachtwoord;
	}

	// admin
	public boolean isAdmin() {
		return admin;
	}

	private void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	//id
	public int getSpelerId() {
		return spelerId;
	}

	
	public void setSpelerId(int spelerId) {
		this.spelerId = spelerId;
	}

}
