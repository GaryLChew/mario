import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JFrame;

public class MarioLauncher {
	public static MarioPanel mop;
	static JFrame gameFrame = new JFrame();

	public static void main(String[] args) {
		//JFrame gameFrame = new JFrame();
		Map<String, String> environMap = System.getenv();
		System.out.println(environMap.keySet());
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		mop = new MarioPanel(d);
		gameFrame.add(mop);
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
	}

	public static void restartMap(boolean resetScore) {
		mop.setVisible(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		mop = new MarioPanel(d);
		mop.setVisible(true);
		gameFrame.add(mop);
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
		
	}

}
