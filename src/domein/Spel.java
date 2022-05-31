package domein;

import java.util.ArrayList;
import java.util.List;

public class Spel {
//ATTRIBUTEN
	private String naam;
	private List<Spelbord> spelborden;

//CONSTRUCTOR
	public Spel(String naam, List<Spelbord> spelborden) {
		setNaam(naam);
		setSpelborden(spelborden);
	}

	public Spel(String naam) {
		this(naam, new ArrayList<>());
	}

//METHODES
	public int geefAantalSpelborden() {
		return spelborden.size();
	}

	public int geefAantalVoltooideSpelborden() {
		int aantal = 0;
		for (Spelbord spelbord : spelborden)
			if (spelbord.isVoltooid())
				aantal++;

		return aantal;
	}

	public boolean isEindeSpel() {
		return geefAantalSpelborden() == geefAantalVoltooideSpelborden();
	}

	public Spelbord geefVolgendOnvoltooidSpelbord() {
		return spelborden.get(geefAantalVoltooideSpelborden()/*-1+1*/);
	}

	public void voegSpelbordToe(Spelbord spelbord) {
		spelborden.add(spelbord);
	}

	@Override
	public String toString() {
		return String.format("%s", naam);
	}

	public int[] geefSpelbordNummers() {
		int[] spelbordNummers = new int[spelborden.size()];
		int index = 0;
		for (Spelbord spelbord : spelborden) {
			spelbordNummers[index] = spelbord.getNummer();
			index++;
		}
		return spelbordNummers;
	}

	public void valideerSpel() {
		if (0 < spelborden.size()) {
			// throw new Exception
		}
	}

	// GETTERS EN SETTERS
	private void setNaam(String naam) {
		this.naam = naam;
	}

	public String getNaam() {
		return naam;
	}

	private void setSpelborden(List<Spelbord> spelborden) {
		this.spelborden = spelborden;
	}

	public List<Spelbord> getSpelborden() {
		return spelborden;
	}

}