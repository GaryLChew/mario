import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class MovingObject extends GameObject {

	private static double gravity = -GameObject.getHeight() / 200;
	private static double defaultVertVelocity = GameObject.getHeight() / 5;
	private double vertVelocity = 0;
	private double acceleration = gravity;
	private boolean alive = true;

	public MovingObject(double x, double y, String fp, int ty) {
		super(x, y, fp, ty);
	}

	@Override
	public void move() {
		if (alive)
			super.move();
	}

	public void adjustY() {
		if (alive) {
			System.out.println("y: " + super.getY() + " v: " + vertVelocity + " a: " + acceleration);
			super.addY(-(vertVelocity));
			vertVelocity += acceleration;
		}
	}

	public double getVertVelocity() {
		return vertVelocity;
	}

	public void setVertVelocity(double v) {
		vertVelocity = v;
	}

	public void jump() {
		if (alive) {
			vertVelocity = defaultVertVelocity;
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		alive = false;
	}

}
