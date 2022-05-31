package domein;

import exceptions.FouteInlogGegevensException;
import exceptions.GebruikersnaamAlInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

// ATTRIBUTEN
	private final SpelerMapper spelerMapper;

// CONSTRUCTORS
	public SpelerRepository() {
		spelerMapper = new SpelerMapper();
	}

// METHODES
	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
		Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
		if (speler == null) {
			throw new FouteInlogGegevensException();
		}
		if (speler.getWachtwoord().equals(wachtwoord))
			return speler;

		throw new FouteInlogGegevensException();
	}

	public void voegToe(Speler speler) {
		if (bestaatSpeler(speler.getGebruikersnaam()))
			throw new GebruikersnaamAlInGebruikException("Gebruikersnaam bestaat al!");
		else
			spelerMapper.voegToe(speler);

	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return spelerMapper.geefSpeler(gebruikersnaam) != null;
	}

}
