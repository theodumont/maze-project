
public class Case {
	// attributs
	int abscisse, ordonnee;
	boolean murNorth, murEast, murSouth, murWest;
//	boolean[] murs;
	boolean visitee;

	// constructeurs
	public Case(int abs, int ord) {
		abscisse = abs;
		ordonnee = ord;
//		for (int k=0;k<3;k++) {
//			murs[k] = true;
//		}
		murNorth = true;
		murEast = true;
		murSouth = true;
		murWest = true;
		visitee = false;
	}

	int getAbs() {
		return abscisse;
	}

	int getOrd() {
		return ordonnee;
	}
}
