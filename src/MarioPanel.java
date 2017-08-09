import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class MarioPanel extends JPanel {

	final Dimension defaultDim;// = new Dimension(800,600);
	PlatformerMap pm;

	public int screenWidth;
	public int screenHeight;
	// public static double scaleY = 10;
	Timer drawTimer;

	/*
	 * public MovingObjectsPanel() { this(new Dimension(800, 600)); }
	 */
	public MarioPanel(Dimension dim) {
		// defaultDim = dim;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		defaultDim = new Dimension((int) (screenWidth*.85), (int) (screenHeight*.85));
		this.setPreferredSize(defaultDim);
		makeGameMap();
		setUpKeyMappings();
		setUpDrawTimer();
	}

	private void makeGameMap() {
		// scaleY=defaultDim.height/r;
		pm = new PlatformerMap();
		// need to change this later
//		this.setPreferredSize(new Dimension((int) (PlatformerMap.getCols() * PlatformerMap.getScale()), (int) ((PlatformerMap.getRows()+2) * PlatformerMap.getScale())));
	}

	private void setUpKeyMappings() {
		pm.executeKey(this.getInputMap(), this.getActionMap());
		this.requestFocusInWindow();
	}

	private void setUpDrawTimer() {
		drawTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		drawTimer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// System.out.println("new layer painted");
		pm.draw(g);
	}
}
