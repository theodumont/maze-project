import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Labyrinthe {
	// attributs
	int hauteurLab, longueurLab;
	int deltax;
	Case[][] cases;
	// Direction[] directions = { Direction.NORTH, Direction.EAST, Direction.SOUTH,
	// Direction.WEST };

	// constructeur
	public Labyrinthe(int h) {
		hauteurLab = h;
		longueurLab = 16 * h / 7;
		deltax = 560 / h;
		cases = new Case[hauteurLab][longueurLab];
		for (int i = 0; i < hauteurLab; i++) {
			for (int j = 0; j < longueurLab; j++) {
				cases[i][j] = new Case(j, i);
				// System.out.println("Case ("+i+","+j+") créée");
			}
		}

		// CONSTRUCTION
		int ord = h / 2;
		int abs = 0;

		Stack<Case> pileCases = new Stack<Case>();
		pileCases.push(cases[ord][abs]);
		Case sauvCase;
		int aleatoire;
		Random rand = new Random();
		int ordFin = rand.nextInt(hauteurLab);
		int nombreCases = hauteurLab * longueurLab;
		int casesVisitees = 1;

		ArrayList<Coordonnees> voisinsCase = new ArrayList<Coordonnees>();
		while (casesVisitees < nombreCases) {

			voisinsCase.clear();
			// NORTH
			if (ord > 0 && !(cases[ord - 1][abs].visitee)) {
				voisinsCase.add(new Coordonnees(abs, ord - 1, Direction.NORTH));
			}
			// EAST
			if (abs < longueurLab - 1 && !(cases[ord][abs + 1].visitee)) {
				voisinsCase.add(new Coordonnees(abs + 1, ord, Direction.EAST));
			}
			// SOUTH
			if (ord < hauteurLab - 1 && !(cases[ord + 1][abs].visitee)) {
				voisinsCase.add(new Coordonnees(abs, ord + 1, Direction.SOUTH));
			}
			// WEST
			if (abs > 0 && !(cases[ord][abs - 1].visitee)) {
				voisinsCase.add(new Coordonnees(abs - 1, ord, Direction.WEST));
			}

			cases[ord][abs].visitee = true;
			if (voisinsCase.size() > 0) {
				aleatoire = rand.nextInt(voisinsCase.size());
				Coordonnees coordCaseSuivante = voisinsCase.get(aleatoire);

				casserMurs(abs, ord, coordCaseSuivante.getDir());

				pileCases.push(cases[ord][abs]);

				abs = coordCaseSuivante.getAbs();
				ord = coordCaseSuivante.getOrd();
				casesVisitees += 1;
				// System.out.println("cases visitées :"+casesVisitees);
			} else {// On fait demi-tour
					// System.out.println("demitour");
				sauvCase = pileCases.pop();
				// System.out.println("anciennes coord :"+abs+ord);
				abs = sauvCase.getAbs();
				ord = sauvCase.getOrd();
				// System.out.println("nouvelles coord :"+abs+ord);
			}

		}
		cases[ordFin][longueurLab - 1].murEast = false;
	}

	private void casserMurs(int abs, int ord, Direction dir) {

		switch (dir) {
		case NORTH:
			cases[ord][abs].murNorth = false;
			cases[ord - 1][abs].murSouth = false;
			break;
		case EAST:
			cases[ord][abs].murEast = false;
			cases[ord][abs + 1].murWest = false;
			break;
		case SOUTH:
			cases[ord][abs].murSouth = false;
			cases[ord + 1][abs].murNorth = false;
			break;
		case WEST:
			cases[ord][abs].murWest = false;
			cases[ord][abs - 1].murEast = false;
			break;
		}

	}

	public void afficher(Graphics g) {

		BufferedImage solTout, solLab;
		try {
			solTout = ImageIO.read(new File("images\\solTout.png"));
			solLab = ImageIO.read(new File("images\\solLab.png"));
			g.drawImage(solTout, 0, 0, 1580, 860, null);
			g.drawImage(solLab, 150, 150, longueurLab * deltax, hauteurLab * deltax, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("Affichage du labyrinthe : début");
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i < hauteurLab; i++) {
			for (int j = 0; j < longueurLab; j++) {
				Case c = cases[i][j];
				// if (c.visitee)
				// g.drawLine(150+j*deltax,150+i*deltax,150+(j+1)*deltax,150+(i+1)*deltax);
				if (c.murNorth)
					g.fillRect(148 + j * deltax, 148 + i * deltax, deltax + 4, 4);
				if (c.murSouth)
					g.fillRect(148 + j * deltax, 148 + (i + 1) * deltax, deltax + 4, 4);
				if (c.murWest)
					g.fillRect(148 + j * deltax, 148 + i * deltax, 4, deltax + 4);
				if (c.murEast)
					g.fillRect(148 + (j + 1) * deltax, 148 + i * deltax, 4, deltax + 4);

			}
		} // System.out.println(150+(longueurLab+1)*deltax);
			// System.out.println(150+(hauteurLab+1)*deltax);
		g.setColor(Color.PINK);
		g.fillRect(148, 152 + hauteurLab / 2 * deltax, 4, deltax - 4);
		// System.out.println("Affichage du labyrinthe : fin");

		if (GameMaze.enPause) {
			String[] choix = { "Reprendre", "Recommencer", "Nouvelle partie", "Quitter" };
			int intChoix = JOptionPane.showOptionDialog(null, "Le jeu est en pause. Que voulez-vous faire ?", "Pause",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
			switch (intChoix) {
			case 0:
				GameMaze.enPause = false;
				break;
			case 1:
				GameMaze.enPause = false;
				GameMaze.aDemarre = false;
				GameMaze.joueur = new Joueur();
				GameMaze.botDroite = new BotDroite();
				GameMaze.botGauche = new BotGauche();
				GameMaze.panelCenter.repaint();
				GameMaze.chrono.restart();
				break;
			case 2:
				String personnages[] = { "Ours", "Théo", "Stéphane Plaza" };
				String stringImgJoueur = (String) JOptionPane.showInputDialog(null, "Choisis ton personnage :",
						"Choix perso", JOptionPane.QUESTION_MESSAGE, null, personnages, personnages[0]);
				int hauteurChoix = Integer.parseInt(JOptionPane.showInputDialog(null,
						"Choisis la hauteur du labyrinthe :", "Taille du labyrinthe", JOptionPane.QUESTION_MESSAGE));
				int vitesse = Integer.parseInt(JOptionPane.showInputDialog(null,
						"Choisis la féquence de mouvement des robots :\n(100 est très lent, 100 très rapide)",
						"Vitesse des robots", JOptionPane.QUESTION_MESSAGE));
				new GameMaze(hauteurChoix, stringImgJoueur, vitesse);
				GameMaze.enPause = false;
				GameMaze.aDemarre = false;
				break;
			case 3:
				System.exit(1);
				break;
			}
		}
	}

	public int getHauteur() {
		return hauteurLab;
	}

	public int getLongueur() {
		return longueurLab;
	}

	public int getdeltax() {
		return deltax;
	}
}
