package domein;

import java.util.ArrayList;
import java.util.List;

import domein.Veld;
import exceptions.IllegalMoveException;
import exceptions.VoegSpelbordToeException;

public class Spelbord {

// ATTRIBUTEN
	private int nummer;
	private boolean voltooid;
	private int aantalVerplaatsingen;

	private Veld[][] velden;
	private List<Veld> veldenMetDoel;
	private int xMannetje, yMannetje;

//CONSTRUCTORS
	
	public Spelbord(int nummer) {
		setNummer(nummer);
	}
	
	public Spelbord() {
		Veld[][] velden = new Veld[10][10];
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				velden[y][x] = new Veld();
			}
		}

		setVelden(velden);

	}

// METHODES
	public char[][] geefWeergave() {
		char[][] weergave = new char[10][10];

		for (int y = 0; y < velden.length; y++)
			for (int x = 0; x < velden[y].length; x++)
				weergave[y][x] = velden[y][x].geefSymbool();

		return weergave;
	}

	public void verplaatsMannetje(int dx, int dy) throws IllegalMoveException {
		Veld veldMetMannetje = velden[yMannetje][xMannetje];
		Veld bestemmingMannetje = velden[yMannetje + dy][xMannetje + dx];

		if (bestemmingMannetje.isMuur())
			throw new IllegalMoveException("Het mannetje botste tegen een muur.");

		if (bestemmingMannetje.bevatKist()) {
			Veld bestemmingKist = velden[yMannetje + 2 * dy][xMannetje + 2 * dx];
			verplaatsKist(bestemmingMannetje, bestemmingKist);
		}

		// Mannetje daadwerkelijk verplaatsen
		veldMetMannetje.setVerplaatsbaarItem('.');
		bestemmingMannetje.setVerplaatsbaarItem('@');
		xMannetje += dx;
		yMannetje += dy;
		aantalVerplaatsingen++;
	}

	public void verplaatsKist(Veld veldMetKist, Veld bestemmingKist) throws IllegalMoveException {
		if (bestemmingKist.isMuur() || bestemmingKist.bevatKist())
			throw new IllegalMoveException("De kist botste tegen een muur of een andere kist.");

		// Kist daadwerkelijk verplaatsen
		veldMetKist.setVerplaatsbaarItem('.');
		bestemmingKist.setVerplaatsbaarItem('K');
	}

	public boolean bevatElkVeldMetDoelKist() {
		for (Veld veldMetDoel : veldenMetDoel) {
			if (!veldMetDoel.bevatKist())
				return false;
		}

		return true;
	}

	public void laadVeldenIn(Veld[][] velden) {
		this.velden = velden;
		aantalVerplaatsingen = 0;
		veldenMetDoel = new ArrayList<>();

		for (int y = 0; y < velden.length; y++)
			for (int x = 0; x < velden[y].length; x++) {
				// veldMetDoel toevoegen
				if (velden[y][x].isDoel())
					veldenMetDoel.add(velden[y][x]);

				// Coordinaten mannetje bijhouden
				if (velden[y][x].bevatMannetje()) {
					setXMannetje(x);
					setYMannetje(y);
				}
			}
	}

	public void pasVeldAan(int x, int y, int actie) {

		switch (actie) {
		case 1:
			velden[y][x].maakMuur();
			break;
		case 2:
			velden[y][x].plaatsKist();
			break;
		case 3:
			velden[y][x].maakDoel();
			break;
		case 4:
			velden[y][x].plaatsMannetje();
			break;
		case 5:
			velden[y][x].maakLeeg();
			break;
		}

	}

	public void controleerSpelbord() {
		int aantalMannetjes = 0, aantalKisten = 0, aantalDoelen = 0;

		for (Veld[] rij : velden) {
			for (Veld veld : rij) {
				if (veld.bevatMannetje())
					aantalMannetjes++;
				else if (veld.bevatKist())
					aantalKisten++;
				if (veld.isDoel())
					aantalDoelen++;
			}
		}

		String message = "Spelbord niet toegevoegd: ";
		boolean exception = false;

		if (aantalMannetjes != 1) {
			message += String.format("%n-Er moet exact 1 mannetje op het bord staan.");
			exception = true;
		}
		if (aantalKisten != aantalDoelen) {
			message += String.format("%n-Het bord moet evenveel kisten als doelen hebben.");
			exception = true;
		}
		if (0 == aantalDoelen) {
			message += String.format("%n-Er moet minstens 1 doel op het bord staan.");
			exception = true;
		}

		if (exception)
			throw new VoegSpelbordToeException(message);
	}

	// GETTERS EN SETTER
	public boolean isVoltooid() {
		return voltooid;
	}

	private void setVelden(Veld[][] velden) {
		this.velden = velden;
	}

	public int getAantalVerplaatsingen() {
		return aantalVerplaatsingen;
	}

	public final void setVoltooid(boolean voltooid) {
		this.voltooid = voltooid;
	}

	private void setXMannetje(int xMannetje) {
		this.xMannetje = xMannetje;
	}

	private void setYMannetje(int yMannetje) {
		this.yMannetje = yMannetje;
	}

	public final void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public int getNummer() {
		return nummer;
	}

	public Veld[][] getVelden() {
		return velden;
	}
}
