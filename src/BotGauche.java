// BOT QUI SUIT LE MUR DE GAUCHE
class BotGauche extends Bot {
	public BotGauche() {
		super();
	}

	public void choixPuisDeplacement() {
		// System.out.println("choixpuisdeplacement");
		Case c = GameMaze.labyrinthe.cases[ordonneeBot][abscisseBot];
		switch (regard) {
		case NORTH:
			if (!c.murWest)
				deplacerBot(Direction.WEST);
			else if (!c.murNorth)
				deplacerBot(Direction.NORTH);
			else if (!c.murEast)
				deplacerBot(Direction.EAST);
			else
				deplacerBot(Direction.SOUTH);
			break;
		case EAST:
			if (!c.murNorth)
				deplacerBot(Direction.NORTH);
			else if (!c.murEast)
				deplacerBot(Direction.EAST);
			else if (!c.murSouth)
				deplacerBot(Direction.SOUTH);
			else
				deplacerBot(Direction.WEST);
			break;
		case SOUTH:
			if (!c.murEast)
				deplacerBot(Direction.EAST);
			else if (!c.murSouth)
				deplacerBot(Direction.SOUTH);
			else if (!c.murWest)
				deplacerBot(Direction.WEST);
			else
				deplacerBot(Direction.NORTH);
			break;
		case WEST:
			if (!c.murSouth)
				deplacerBot(Direction.SOUTH);
			else if (!c.murWest)
				deplacerBot(Direction.WEST);
			else if (!c.murNorth)
				deplacerBot(Direction.NORTH);
			else
				deplacerBot(Direction.EAST);
			break;

		}

	}
}