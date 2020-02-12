import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CanvasLaby extends Canvas {
	protected Image imgCachee;
	protected Dimension dimImgCachee;

	// générer graphic avec fond (ou image)

	public void update(Graphics g) {
		Dimension dimCanvas = getSize(); // taille actuelle du canvas visible sur l'écran
		if (imgCachee == null || // l'image cachée n'a pas encore été crée
				dimImgCachee.width != dimCanvas.width || // l'IHM a été redimensionnée
				dimImgCachee.height != dimCanvas.height) {
			imgCachee = createImage(dimCanvas.width, dimCanvas.height);
			dimImgCachee = dimCanvas;
		}
		paint(imgCachee.getGraphics()); // dessine sur l'image cachée
		g.drawImage(imgCachee, 0, 0, null); // affiche l'image
	}

	public void paint(Graphics g) {
//		g.drawLine(0, 0, 1580, 860);
//		g.drawLine(150, 150, 1430, 710);

		// System.out.println("Position joueur : ("+GameMaze.joueur.getAbs()+",
		// "+GameMaze.joueur.getOrd()+")");

		int deltax = GameMaze.labyrinthe.getdeltax();
		// JOUEUR
		int abscisseJDansCanvas = 150 + deltax * GameMaze.joueur.getAbs();
		int ordonneeJDansCanvas = 150 + deltax * GameMaze.joueur.getOrd();
		// BOTDROITE
		int abscisseBDDansCanvas = 150 + deltax * GameMaze.botDroite.getAbs();
		int ordonneeBDDansCanvas = 150 + deltax * GameMaze.botDroite.getOrd();
		// BOTGAUCHE
		int abscisseBGDansCanvas = 150 + deltax * GameMaze.botGauche.getAbs();
		int ordonneeBGDansCanvas = 150 + deltax * GameMaze.botGauche.getOrd();

		GameMaze.labyrinthe.afficher(g);
		BufferedImage imgJoueur;
		try {
//		imgBotDroite = ImageIO.read(new File("images\\droite.png"));
			g.setColor(Color.BLUE);
			g.fillOval(abscisseBDDansCanvas, ordonneeBDDansCanvas, deltax, deltax);
			// g.drawImage(imgBotDroite,abscisseBDDansCanvas,ordonneeBDDansCanvas,deltax,deltax,null);
//		imgBotGauche = ImageIO.read(new File("images\\gauche.png"));
			g.setColor(Color.RED);
			g.fillOval(abscisseBGDansCanvas, ordonneeBGDansCanvas, deltax, deltax);
//		g.drawImage(imgBotGauche,abscisseBGDansCanvas,ordonneeBGDansCanvas,deltax,deltax,null);
			imgJoueur = ImageIO.read(new File("images\\" + GameMaze.stringImgJoueur + ".png"));
			g.drawImage(imgJoueur, abscisseJDansCanvas, ordonneeJDansCanvas, deltax, deltax, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//    g.fillOval(abscisseDansCanvas,ordonneeDansCanvas,deltax,deltax);

	}
}