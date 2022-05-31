package domein;

import java.util.List;

import persistentie.SpelMapper;

public class SpelRepository {
// ATTRIBUTEN
	private final SpelMapper spelMapper;

// CONSTRUCTORS
	public SpelRepository() {
		spelMapper = new SpelMapper();
	}

// METHODES
	public List<String> geefSpelNamen() {
		return spelMapper.geefSpelNamen();
	}

	public List<Spelbord> geefSpelborden(String spelNaam) {
		return spelMapper.geefSpelborden(spelNaam);
	}

	public Veld[][] geefVelden(String spelnaam, int spelbordNummer) {
		return spelMapper.geefVelden(spelnaam, spelbordNummer);
	}

	public void registreerNieuwSpel(Spel spel,int spelerId) {
		if(spel.getSpelborden().size()>0) {
		spelMapper.registreerNieuwSpel(spel,spelerId);
		}
	}

	public List<String> geefSpelNamenVanSpeler(String gebruikersnaam) {
		return spelMapper.geefSpelNamenVanSpeler(gebruikersnaam);
	}

	public void registreerGewijzigdSpel(Spel spel) {
		spelMapper.registreerGewijzigdSpel(spel);
	}

	public void verwijderSpelbord(Spel spel, Spelbord spelbord) {
		spelMapper.verwijderSpelbord(spel, spelbord);
	}
}
