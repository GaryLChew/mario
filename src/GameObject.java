import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class GameObject {
	private double originalSpeed, speed, x, y;
	private static int width;
	private static int height;
	public static final int NORTH = 1, SOUTH = 3, EAST = 2, WEST = 4;
	// public static int length = 30;

	private String filePath;
	private int direction;
	private Image img = null;
	int type;

	private static int deltaX, deltaY;

	public GameObject(double x, double y, String fp, int ty) {

		originalSpeed = width / 14;
		speed = originalSpeed;
		filePath = fp;
		type = ty;
		openImage();
		this.x = x;
		this.y = y;
	}

	public boolean isColliding(GameObject go) {
		return go.getRect().intersects(getRect());
	}
	
	public boolean collideSide(GameObject go) {
		return collideLeft(go)||collideRight(go);
	}

	public boolean collideLeft(GameObject go) {
		Rectangle lRect = new Rectangle((int) x, (int) (y + height / 2), 1, 1);
		return go.getRect().intersects(lRect);
//		Rectangle lRect = new Rectangle((int) x, (int) (y+speed), (int) (1.6*speed),(int) (height-(2*speed+2)));
//		return go.getRect().intersects(lRect);
	}

	public boolean collideRight(GameObject go) {
		Rectangle rRect = new Rectangle((int) (x + width - 1), (int) (y + height / 2), 1, 1);
		return go.getRect().intersects(rRect);
//		Rectangle rRect = new Rectangle((int) (x + width - 1.6*speed), (int) (y+speed), (int) (1.6*speed), (int) (height-(2*speed+2)));
//		return go.getRect().intersects(rRect);
	}

	public boolean collideTop(GameObject go) {
		Rectangle tRect = new Rectangle((int) x, (int) y, width, 1);
		return go.getRect().intersects(tRect) || go.getRect().intersects(tRect);
	}

	public boolean collideBot(GameObject go) {
		Rectangle tRect = new Rectangle((int) x, (int) (y + height - 1), width, 1);
		return go.getRect().intersects(tRect) || go.getRect().intersects(tRect);
	}

	public void move() {
		if (direction == GameObject.WEST) {
			x -= speed;
		} else if (direction == GameObject.EAST) {
			x += speed;
		}

		// there should be some checking that takes place

	}

	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void openImage() {
		if (img == null) {
			try {
				System.out.println("IMAGE LOADED" + filePath);
				URL url = getClass().getResource(filePath);
				img = new ImageIcon(url).getImage();
			} catch (Exception e) {
				System.out.println("Problem opening the image at " + filePath);
				e.printStackTrace();
			}
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double coord) {
		x = coord;
	}

	public void setY(double coord) {
		y = coord;
	}

	public void addX(double addition) {
		x += addition;
	}

	public void addY(double addition) {
		y += addition;
	}

	public int getType() {
		return type;
	}

	public static double getWidth() {
		return width;
	}

	public static double getHeight() {
		return height;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int d) {
		direction = d;
	}

	public void draw(Graphics g) {
		// NOTE: x and y are in PIXELS
		// System.out.println(filePath);
		// System.out.println("drawn at " + x + "," + y + " with width " + width
		// + " and height " + height);
		g.drawImage(img, (int) (deltaX + x), (int) (deltaY + y), width, height, null);
//		 g.setColor(Color.MAGENTA);
//		 g.drawRect((int) x, (int) y, width, height);
//		 
//		 g.setColor(Color.RED);
//		 g.drawRect((int) (x + width - 1.6*speed), (int) (y+speed), (int) (1.6*speed), (int) (height-(2*speed+2)));
	}

	public void setImage(Image im) {
		img = im;
	}

	public Image getImage() {
		return img;
	}

	public String getFilePath() {
		return filePath;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double s) {
		speed = s;
	}

	public void setSpeedToOriginal() {
		speed = originalSpeed;
	}

	public static void giveLength(int sc) {
		width = sc;
		height = sc;
	}

	public static void addDelta(int kx, int ky) {
		deltaX += kx;
		deltaY += ky;
	}

	public static int getDX() {
		return deltaX;
	}

	public static int getDY() {
		return deltaY;
	}

}
