package domein;

public class Veld {

// ATTRIBUTEN
	private char type, verplaatsbaarItem;

// CONSTRUCTOREN
	public Veld(char type, char verplaatsbaarItem) {
		setType(type);
		setVerplaatsbaarItem(verplaatsbaarItem);
	}

	public Veld() {
		this('.', '.');
	}

// METHODES
	public char geefSymbool() {
		if (verplaatsbaarItem == '.')
			return type;
		else
			return verplaatsbaarItem;
	}

	public void maakMuur() {
		setVerplaatsbaarItem('.');
		setType('M');
	}

	public void maakDoel() {
		if (type == 'M')
			setType('.');

		setType('X');
	}

	public void plaatsMannetje() {
		if (type == 'M')
			setType('.');

		setVerplaatsbaarItem('@');
	}

	public void plaatsKist() {
		if (type == 'M')
			setType('.');

		setVerplaatsbaarItem('K');
	}

	public void maakLeeg() {
		setType('.');
		setVerplaatsbaarItem('.');
	}

	// GETTERS EN SETTERS
	private void setType(char type) {
		this.type = type;
	}

	public void setVerplaatsbaarItem(char verplaatsbaarItem) {
		this.verplaatsbaarItem = verplaatsbaarItem;
	}

	public boolean isMuur() {
		return type == 'M';
	}

	public boolean isDoel() {
		return type == 'X';
	}

	public boolean bevatKist() {
		return verplaatsbaarItem == 'K';
	}

	public boolean bevatMannetje() {
		return verplaatsbaarItem == '@';
	}

	public char getType() {
		return type;
	}

	public char getVerplaatsbaarItem() {
		return verplaatsbaarItem;
	}
}
