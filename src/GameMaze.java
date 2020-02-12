import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class GameMaze extends JFrame implements ActionListener, KeyListener {

	// DECLARATION DES ATTRIBUTS :
	private JButton NouvellePartie, Recommencer, Quitter;
	public static CanvasLaby panelCenter;
	public JPanel panelNorth, panelEast, panelSouth, panelWest;
	public static boolean enPause = false;
	public static boolean aDemarre = false;

	public static int taille;
	public static int vitesse;
	public static Chrono chrono;
	public static Joueur joueur;
	public static String stringImgJoueur;
	public static BotDroite botDroite;
	public static BotGauche botGauche;
	public static Labyrinthe labyrinthe;

	// CONSTRUCTEUR :
	public GameMaze(int t, String pers, int v) {
		taille = t;
		stringImgJoueur = pers;
		vitesse = v;
		JFrame fenetre = new JFrame("GameMaze");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// pour mettre en plein écran
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fenetre.setUndecorated(true);

		// initialisation des attributs :
		setLayout(new BorderLayout());

		panelCenter = new CanvasLaby();
		panelCenter.setSize(500, 300);
		fenetre.add(panelCenter, BorderLayout.CENTER);

		// BLOC DU HAUT
		NouvellePartie = new JButton("Nouvelle partie");
		Recommencer = new JButton("Recommencer");
		Quitter = new JButton("Quitter");
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(110, 110));
		panelNorth.setLayout(new FlowLayout());
		panelNorth.setBackground(Color.pink);
		panelNorth.add(NouvellePartie);
		panelNorth.add(Recommencer);
		panelNorth.add(Quitter);
		fenetre.add(panelNorth, BorderLayout.NORTH);

		// BLOC DE DROITE
		panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(170, 170));
		panelEast.setLayout(new FlowLayout());
		JLabel JLabelRobot = new JLabel("Robot");
		JLabelRobot.setFont(new Font("Palatino Linotype", 2, 30));
		panelEast.add(JLabelRobot);
		panelEast.setBackground(Color.LIGHT_GRAY);
		fenetre.add(panelEast, BorderLayout.EAST);

		// BLOC DE GAUCHE
		panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(170, 170));
		panelWest.setLayout(new FlowLayout());
		JLabel JLabelJoueur = new JLabel("Joueur");
		JLabelJoueur.setFont(new Font("Palatino Linotype", 2, 30));
		panelWest.add(JLabelJoueur);
		panelWest.setBackground(Color.LIGHT_GRAY);
		fenetre.add(panelWest, BorderLayout.WEST);

		// BLOC DU BAS
		panelSouth = new JPanel();
		panelSouth.setPreferredSize(new Dimension(110, 110));
		panelSouth.setLayout(new GridLayout());
		panelSouth.setBackground(Color.DARK_GRAY);
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		String formatted = current.format(formatter);
		JLabel date = new JLabel(formatted);
		date.setForeground(Color.WHITE);
		panelSouth.add(date);

		chrono = new Chrono();
		chrono.setForeground(Color.WHITE);
		chrono.setFont(new Font("", 1, 30));
		panelSouth.add(chrono);

		fenetre.add(panelSouth, BorderLayout.SOUTH);

		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);

		NouvellePartie.addActionListener(this);
		Recommencer.addActionListener(this);
		Quitter.addActionListener(this);

		panelCenter.addKeyListener(this);

		labyrinthe = new Labyrinthe(taille);
		joueur = new Joueur();
		botDroite = new BotDroite();
		botGauche = new BotGauche();
		panelCenter.repaint();
	}

	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == NouvellePartie) {
			String personnages[] = { "Ours", "Théo", "Stéphane Plaza" };
			stringImgJoueur = (String) JOptionPane.showInputDialog(null, "Choisis ton personnage :", "Choix perso",
					JOptionPane.QUESTION_MESSAGE, null, personnages, personnages[0]);
			int hauteurChoix = Integer.parseInt(JOptionPane.showInputDialog(null, "Choisis la hauteur du labyrinthe :",
					"Taille du labyrinthe", JOptionPane.QUESTION_MESSAGE));
			vitesse = Integer.parseInt(JOptionPane.showInputDialog(null,
					"Choisis la féquence de mouvement des robots :\n(100 est très lent, 100 très rapide)",
					"Vitesse des robots", JOptionPane.QUESTION_MESSAGE));
			new GameMaze(hauteurChoix, stringImgJoueur, vitesse);
			GameMaze.enPause = false;
			GameMaze.aDemarre = false;
		}
//		else if (evt.getSource() == Recommencer) {
//			GameMaze.enPause = false;
//			GameMaze.aDemarre = false;
//			GameMaze.joueur = new Joueur();
//			GameMaze.botDroite = new BotDroite();
//			GameMaze.botGauche = new BotGauche();
//			GameMaze.panelCenter.repaint();
//			GameMaze.chrono.restart();
//		}
		else if (evt.getSource() == Quitter) {
			System.exit(1);
		}
	}

	// KEY LISTENER
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("Touche appuyée");
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_UP:
			if (!enPause && !labyrinthe.cases[joueur.getOrd()][joueur.getAbs()].murNorth) {
				aDemarre = true;
				joueur.deplacerJoueur(Direction.NORTH);
			}
			break;
		case KeyEvent.VK_DOWN:
			if (!enPause && !labyrinthe.cases[joueur.getOrd()][joueur.getAbs()].murSouth) {
				aDemarre = true;
				joueur.deplacerJoueur(Direction.SOUTH);
			}
			break;
		case KeyEvent.VK_LEFT:
			if (!enPause && !labyrinthe.cases[joueur.getOrd()][joueur.getAbs()].murWest) {
				aDemarre = true;
				joueur.deplacerJoueur(Direction.WEST);
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (!enPause && !labyrinthe.cases[joueur.getOrd()][joueur.getAbs()].murEast) {
				aDemarre = true;
				joueur.deplacerJoueur(Direction.EAST);
			}
			break;
		case KeyEvent.VK_ESCAPE:
			enPause = true;
			break;
		}
		panelCenter.repaint();
		if (joueur.abscisseJoueur == labyrinthe.longueurLab) {
			gagneJeu();
		}

	}

	private void gagneJeu() {
		aDemarre = false;
		ImageIcon icon = new ImageIcon("images\\" + stringImgJoueur + ".png");
		JOptionPane.showMessageDialog(this, "Vous êtes l'heureux gagnant d'un magnifique dictionnaire !", "Bravo !",
				JOptionPane.INFORMATION_MESSAGE, icon);
		// System.out.println("Gagné");
		String[] choix = { "Nouvelle partie", "Recommencer", "Quitter" };
		int intChoix = JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", "Et maintenant ?",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
		switch (intChoix) {
		case 0:
			String personnages[] = { "Ours", "Théo", "Stéphane Plaza" };
			stringImgJoueur = (String) JOptionPane.showInputDialog(null, "Choisis ton personnage :", "Choix perso",
					JOptionPane.QUESTION_MESSAGE, null, personnages, personnages[0]);
			int hauteurChoix = Integer.parseInt(JOptionPane.showInputDialog(null, "Choisis la hauteur du labyrinthe :",
					"Taille du labyrinthe", JOptionPane.QUESTION_MESSAGE));
			vitesse = Integer.parseInt(JOptionPane.showInputDialog(null,
					"Choisis la féquence de mouvement des robots :\n(100 est très lent, 100 très rapide)",
					"Vitesse des robots", JOptionPane.QUESTION_MESSAGE));
			new GameMaze(hauteurChoix, stringImgJoueur, vitesse);
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

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
