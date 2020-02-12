import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class executable {
	public static int hauteurChoix;
	public static String perso;
	public static int vitesse;

	public static void main(String[] args) {
		String personnages[] = { "Ours", "Théo", "Stéphane Plaza" };
		perso = (String) JOptionPane.showInputDialog(null, "Bienvenue ! Choisis ton personnage :", "Choix perso",
				JOptionPane.QUESTION_MESSAGE, null, personnages, personnages[0]);

		hauteurChoix = Integer.parseInt(JOptionPane.showInputDialog(null,
				"Choisis la hauteur du labyrinthe :\n(5 est très facile, 25 très dur)", "Taille du labyrinthe",
				JOptionPane.QUESTION_MESSAGE));

		vitesse = Integer.parseInt(JOptionPane.showInputDialog(null,
				"Choisis la féquence de mouvement des robots :\n(1000 est très lent, 100 très rapide)",
				"Vitesse des robots", JOptionPane.QUESTION_MESSAGE));
		JOptionPane.showMessageDialog(null,
				"Pour lancer la partie, cliquez sur le labyrinthe, puis dirigez-vous avec les flèches directionnelles !",
				"Instructions", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images\\" + perso + ".png"));

		new GameMaze(hauteurChoix, perso, vitesse);
	}

}
