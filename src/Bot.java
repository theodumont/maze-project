import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public abstract class Bot implements Runnable {
	// attributs
	public int abscisseBot, ordonneeBot;
	public Direction regard;

	// constructeur
	public Bot() {
		abscisseBot = 0;
		ordonneeBot = GameMaze.labyrinthe.getHauteur() / 2;
		regard = Direction.EAST;
		Thread t = new Thread(this);
		t.start();
	}

	// méthodes
	public int getAbs() {
		return abscisseBot;
	}

	public int getOrd() {
		return ordonneeBot;
	}

	public void deplacerBot(Direction dir) {
		switch (dir) {
		case NORTH:
			ordonneeBot -= 1;
			break;
		case WEST:
			abscisseBot -= 1;
			break;
		case EAST:
			abscisseBot += 1;
			break;
		case SOUTH:
			ordonneeBot += 1;
			break;
		}
		regard = dir;
	}

	public abstract void choixPuisDeplacement();

	@Override
	public void run() {

		while (abscisseBot != GameMaze.labyrinthe.longueurLab) {
			try {
				Thread.sleep(GameMaze.vitesse);
				if (GameMaze.aDemarre && !GameMaze.enPause) {
					choixPuisDeplacement();
					GameMaze.panelCenter.repaint();
					if (GameMaze.botDroite.abscisseBot == GameMaze.labyrinthe.longueurLab
							&& GameMaze.botGauche.abscisseBot == GameMaze.labyrinthe.longueurLab) {
						perdJeu();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void perdJeu() {
		GameMaze.aDemarre = false;
		ImageIcon icon = new ImageIcon("images\\" + GameMaze.stringImgJoueur + ".png");
		JOptionPane.showMessageDialog(null, "Vous avez perdu...", "Perdu...", JOptionPane.INFORMATION_MESSAGE, icon);
		// System.out.println("Gagné");
		String[] choix = { "Nouvelle partie", "Recommencer", "Quitter" };
		int intChoix = JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", "Et maintenant ?",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
		switch (intChoix) {
		case 0:
			String personnages[] = { "Ours", "Théo", "Stéphane Plaza" };
			GameMaze.stringImgJoueur = (String) JOptionPane.showInputDialog(null, "Choisis ton personnage :",
					"Choix perso", JOptionPane.QUESTION_MESSAGE, null, personnages, personnages[0]);
			int hauteurChoix = Integer.parseInt(JOptionPane.showInputDialog(null, "Choisis la hauteur du labyrinthe :",
					"Taille du labyrinthe", JOptionPane.QUESTION_MESSAGE));
			GameMaze.vitesse = Integer.parseInt(JOptionPane.showInputDialog(null,
					"Choisis la féquence de mouvement des robots :\n(100 est très lent, 100 très rapide)",
					"Vitesse des robots", JOptionPane.QUESTION_MESSAGE));
			new GameMaze(hauteurChoix, GameMaze.stringImgJoueur, GameMaze.vitesse);
			GameMaze.enPause = false;
			GameMaze.aDemarre = false;
			break;
		case 1:
			GameMaze.enPause = false;
			GameMaze.aDemarre = false;
			GameMaze.joueur = new Joueur();
			GameMaze.botDroite = new BotDroite();
			GameMaze.botGauche = new BotGauche();
			GameMaze.panelCenter.repaint();
			GameMaze.chrono.restart();
		case 2:
			System.exit(1);
			break;
		}
	}
}
