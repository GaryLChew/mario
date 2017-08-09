import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlatformerMap extends GameMap {
	private int turn = -1;
	private static int rows;
	private static int columns;
	public int turning = 0;
	Timer t;
	private static int scale = -1;
	private int coins = 0;
	private int g = 0;

	private int screenHeight, screenWidth;
	private int deltaX, deltaY;

	private List<String> keyPresses = new ArrayList<>();

	Timer songTimer;

	AudioStream bgSong = null;

	boolean deathPaused;

	/*
	 * gameStatus -1 is loss, 0 is ongoing, 1 is win
	 */
	private int gameStatus = 0;

	// public static void setScore(int sc){
	//
	// }
	public PlatformerMap() {
		constructor();
	}

	private void constructor() {
		createMap();
		setUpTimer();
		playSong();
	}

	private void createMap() {
		// This method initializes the game

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = (int) screenSize.getHeight();
		screenWidth = (int) screenSize.getWidth();

		MapGenerator mg = new MapGenerator(screenWidth, screenHeight);
		super.setMovers(mg.getMap(1));
	}

	private void playSong() {
		try {
			URL url = getClass().getResource("res/sounds/bgSong.wav");
			bgSong = new AudioStream(url.openStream());
		} catch (Exception e) {
			System.out.println("Problem opening a sound");
			e.printStackTrace();
		}
		AudioPlayer.player.start(bgSong);
		if (songTimer == null) {
			songTimer = new Timer(82 * 1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					playSong();
				}
			});
			songTimer.start();
		}
		Timer deathChecker = null;
		deathChecker = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameStatus != 0) {
					AudioPlayer.player.stop(bgSong);
				}
			}
		});
		deathChecker.start();

	}

	public void executeKey(InputMap im, ActionMap am) {
		// maps keys with actions...
		// The code below maps a KeyStroke to an action to be performed
		// In this case I mapped the space bar key to the action named "shoot"
		// Whenever someone hits the Space Bar the action shoot is sent out

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "sprint");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "reset");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "jump");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "moveLeft");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "moveRight");

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true), "releaseSprint");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "releaseJump");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "releaseMoveLeft");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "releaseMoveRight");

		// This associates the command shoot with some action. In this
		// case, the action triggers a shoot command invoked on my GameMap. In
		// general, whatever
		// goes in the actionPerformed method will be executed when a shoot
		// command
		// is sent...
		GameObject m = super.getMoversObject(0);

		am.put("sprint", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("YA BOI I BE SPRINTIN!");
				String keyString = "sprint";
				if (keyPresses.indexOf(keyString) == -1) {
					keyPresses.add(keyString);
				}
			}
		});
		am.put("reset", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "reset";
				if (keyPresses.indexOf(keyString) == -1) {
					keyPresses.add(keyString);
				}
			}
		});
		am.put("jump", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "jump";
				if (keyPresses.indexOf(keyString) == -1) {
					keyPresses.add(keyString);
				}
			}
		});
		am.put("moveRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "moveRight";
				if (keyPresses.indexOf(keyString) == -1) {
					keyPresses.add(keyString);
				}
			}
		});
		am.put("moveLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "moveLeft";
				if (keyPresses.indexOf(keyString) == -1) {
					keyPresses.add(keyString);
				}
			}
		});

		am.put("releaseSprint", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "sprint";
				while (keyPresses.indexOf(keyString) != -1) {
					keyPresses.remove(keyString);
				}
				m.setSpeedToOriginal();
			}
		});

		am.put("releaseJump", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "jump";
				while (keyPresses.indexOf(keyString) != -1) {
					keyPresses.remove(keyString);
				}
			}
		});

		am.put("releaseMoveRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "moveRight";
				while (keyPresses.indexOf(keyString) != -1) {
					keyPresses.remove(keyString);
				}
			}
		});

		am.put("releaseMoveLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyString = "moveLeft";
				while (keyPresses.indexOf(keyString) != -1) {
					keyPresses.remove(keyString);
				}
			}
		});

	}

	private void setUpTimer() {
		t = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		t.start();
	}

	private void tick() {
		if (super.getMoversObject(0).getY() > screenHeight) {
			killMO(0);
		}

		if (((MovingObject) super.getMoversObject(0)).isAlive()) {
			checkForCollisions();
		}
		executeKeyPresses();
		executeGoombaMotion();
		super.getMoversObject(0).setSpeedToOriginal();

	}

	private void gameOver() {
		gameStatus = -1;
		deathPaused = true;
	}

	private void resetGame() {
		System.out.println("GAME RESETTING");
		

		super.setMovers(new ArrayList<GameObject>());

		turn = -1;
		rows = 0;
		columns = 0;
		turning = 0;
		t = null;
		scale = -1;
		coins = 0;
		g = 0;

		screenHeight = 0;
				screenWidth = 0;
		deltaX = 0;
		deltaY = 0;

		keyPresses = new ArrayList<>();

		songTimer = null;

		bgSong = null;

		deathPaused = false;
		
		
		constructor();
	}

	private void executeKeyPresses() {

		GameObject m = super.getMoversObject(0);

		for (int i = 0; i < keyPresses.size(); i++) {
			String key = keyPresses.get(i);
			if (key.equals("jump")) {
				for (GameObject go : super.getMovers()) {
					if (go.getType() == 0 && go.collideTop(m)) {
						jumpMario();
						break;
					}
				}
			} else if (key.equals("sprint")) {
				m.setSpeed(m.getSpeed() * 1.5);

			} else if (key.equals("reset")) {
				resetGame();

			} else if (key.equals("moveRight")) {
				m.setDirection(2);
				moveMario();
			} else if (key.equals("moveLeft")) {
				m.setDirection(4);
				moveMario();
			}
		}
		((MovingObject) super.getMoversObject(0)).adjustY();
	}

	private void checkForCollisions() {

		GameObject m = super.getMoversObject(0);

		for (int i = 1; i < getMovers().size(); i++) {

			// if object at c collides with mario
			GameObject c = getMoversObject(i);
			if (c.isColliding(m)) {

				// if it's a wall
				if ((c.getType() == 0)) {
					if (c.collideTop(m) && !c.collideSide(m)) {
						System.out.println("A" + i);
						((MovingObject) m).setVertVelocity(0);
						if (m.getY() > (c.getY() + m.getSpeed())) {
							System.out.println("AK" + i);
							m.setY(c.getY());
						}
					} else if (c.collideBot(m) && !c.collideSide(m)) {
						((MovingObject) getMoversObject(0)).setVertVelocity(-1);
						System.out.println("BK" + i);

					}
					if (c.collideSide(m) && !c.collideBot(m)) {
						// ((MovingObject)
						// getMoversObject(0)).setVertVelocity(-1);
						m.setSpeed(0);
						System.out.println("CL" + i);
					}
				}

				// if it's a coin
				else if (c.getType() == 2) {
					playSound("res/sounds/coinSound.wav");
					delete(c);
					coins++;
					i--;
				}

				else if (c.getType() == 3) {
					if (((MovingObject) m).isAlive() && ((MovingObject) c).isAlive()) {
						if (c.collideTop(m)) {
							jumpMario();
							killMO(i);
						}

						else {
							killMO(0);
						}
					}
				}
			}
		}
	}

	private void executeGoombaMotion() {
		if (g == 0) {
			for (int i = 0; i < getMovers().size(); i++) {
				if (getMoversObject(i).getType() == 3) {
					getMoversObject(i).setDirection(2);

				}
			}
			g++;
		}
		for (int i = 0; i < getMovers().size(); i++) {
			if (getMoversObject(i).getType() == 3 && ((MovingObject) getMoversObject(i)).isAlive()) {
				getMoversObject(i).move();
			}
		}
		for (int c = 0; c < getMovers().size(); c++) {
			for (int d = 0; d < getMovers().size(); d++) {
				if (getMoversObject(c).getType() == 3 && getMoversObject(d).getType() == 5
						&& getMoversObject(c).isColliding(getMoversObject(d))) {
					if (getMoversObject(c).getType() == 3 && ((MovingObject) getMoversObject(c)).isAlive()) {
						if (getMoversObject(c).getDirection() == 2) {
							getMoversObject(c).setDirection(4);
							getMoversObject(c).move();
						} else if (getMoversObject(c).getDirection() == 4) {
							getMoversObject(c).setDirection(2);
							getMoversObject(c).move();
						}
					}
				}
			}
		}
	}

	private void moveMario() {
		GameObject m = super.getMoversObject(0);
		if ((marioBlock() && m.getDirection() == GameObject.EAST)
				|| (blockMario() && m.getDirection() == GameObject.WEST)) {
			return;
		}
		if (m.getX() > 0 || m.getDirection() == GameObject.EAST) {
			// System.out.println("m: " + m.getX() + " deltaX " + deltaX + " GO
			// Delta: " + GameObject.getDX());
			if (m.getX() < ((.45 * screenWidth) - deltaX) && m.getDirection() == GameObject.WEST
					&& m.getX() > .45 * screenWidth) {
				GameObject.addDelta((int) m.getSpeed(), 0);
				deltaX += m.getSpeed();
			}
			if (m.getX() > ((.55 * screenWidth) - deltaX) && m.getDirection() == GameObject.EAST) {
				GameObject.addDelta((int) -m.getSpeed(), 0);
				deltaX -= m.getSpeed();
			}
			m.move();
		}
	}

	private void jumpMario() {
		boolean blockUnderMario = false;
		boolean goombaUnderMario = false;
		for (GameObject go : super.getMovers()) {
			if (go.collideTop(super.getMoversObject(0))) {
				if (go.getType() == 0)
					blockUnderMario = true;
				else if (go.getType() == 3)
					goombaUnderMario = true;

			}

		}

		if ((!marioBlock() && !blockMario()) || blockUnderMario) {
			if (goombaUnderMario) {
				playSound("res/sounds/stompSound.wav");
			} else {
				playSound("res/sounds/jumpSound.wav");
			}
			((MovingObject) super.getMoversObject(0)).jump();
		}
	}

	private void playSound(String filePath) {
		AudioStream sound = null;
		try {
			URL url = getClass().getResource(filePath);
			sound = new AudioStream(url.openStream());
		} catch (Exception e) {
			System.out.println("Problem opening a sound");
			e.printStackTrace();
		}
		AudioPlayer.player.start(sound);
	}

	private void killMO(int i) {
		MovingObject mo = (MovingObject) super.getMoversObject(i);
		if (!mo.isAlive()) {
			return;
		}
		mo.kill();
		if (mo.getType() == 1) {

			Timer tempTimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameOver();
				}
			});
			tempTimer.start();
			gameStatus = -1;
			playSound("res/sounds/deathSound.wav");
		}
		Timer deathTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mo.getType() == 3) {
					mo.addY(GameObject.getHeight() / 15);
				} else if (mo.getType() == 1) {
					if (deathPaused) {
						mo.addY(GameObject.getHeight() / 10);
					} else {
						mo.addY(-GameObject.getHeight() / 150);
					}
				}
			}
		});
		deathTimer.start();

		Timer endTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameStatus == 0 && mo.getType() == 1 && mo.getY() < 0) {
					delete((GameObject) mo);
					deathTimer.stop();
				}
			}
		});
		deathTimer.start();
		endTimer.start();

	}

	@Override
	public void openBackgroundImage() {
		// TODO Auto-generated method stub
		if (backgroundImage == null) {
			try {
				System.out.println("Background Loaded");
				URL url = getClass().getResource("res/images/marioB.jpg");
				backgroundImage = new ImageIcon(url).getImage();
			} catch (Exception e) {
				System.out.println("Problem opening background image");
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void drawAdditional(Graphics g) {
		// g.drawImage(backgroundImage, deltaX, deltaY, screenWidth,
		// screenHeight, null);
		g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Coins Collected: " + coins, (int) (screenHeight * .1), (int) (screenHeight * .1));
	}

	public static int getScale() {
		return scale;
	}

	public static int getRows() {
		return rows;
	}

	public static int getCols() {
		return columns;
	}

	private boolean marioBlock() {
		GameObject m = super.getMoversObject(0);

		for (GameObject go : super.getMovers()) {
			if (go.getType() == 0 && go.collideLeft(m)) {
				return true;
			}
		}
		return false;
	}

	private boolean blockMario() {
		GameObject m = super.getMoversObject(0);

		for (GameObject go : super.getMovers()) {
			if (go.getType() == 0 && go.collideRight(m)) {
				return true;
			}
		}
		return false;
	}
}
