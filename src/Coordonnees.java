
public class Coordonnees {
	int abscisse;
	int ordonnee;
	Direction direction;

	public Coordonnees(int abs, int ord, Direction dir) {
		abscisse = abs;
		ordonnee = ord;
		direction = dir;
	}

	int getAbs() {
		return abscisse;
	}

	int getOrd() {
		return ordonnee;
	}

	Direction getDir() {
		return direction;
	}
}
