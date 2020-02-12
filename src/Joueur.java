
public class Joueur {
	// attributs
	public int abscisseJoueur, ordonneeJoueur;

	// constructeur
	public Joueur() {
		abscisseJoueur = 0;
		ordonneeJoueur = GameMaze.labyrinthe.getHauteur() / 2;
	}

	// méthodes
	public int getAbs() {
		return abscisseJoueur;
	}

	public int getOrd() {
		return ordonneeJoueur;
	}

	public void deplacerJoueur(Direction dir) {
		switch (dir) {
		case NORTH:
			ordonneeJoueur -= 1;
			break;
		case WEST:
			abscisseJoueur -= 1;
			break;
		case EAST:
			abscisseJoueur += 1;
			break;
		case SOUTH:
			ordonneeJoueur += 1;
			break;
		}
	}
}
