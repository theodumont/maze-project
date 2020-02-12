import javax.swing.JLabel;

public class Chrono extends JLabel implements Runnable {
	public int dixiemes = 0;

	public Chrono() {

		Thread t = new Thread(this);
		t.start();
	}

	public void restart() {
		dixiemes = 0;
	}

	@Override
	public void run() {
		while (true) {
			setText("Temps : " + dixiemes / 10 + "." + dixiemes % 10 + " s");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GameMaze.aDemarre && !GameMaze.enPause) {
				dixiemes++;
			}
		}
	}

}