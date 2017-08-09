import java.awt.Graphics;

public class Goomba extends MovingObject {

	public Goomba(double x, double y) {
		super(x, y, "/res/images/Goomba.gif" , 3);
		// TODO Auto-generated constructor stub
		super.setSpeed(super.getSpeed()*.3);
	}


	//slightly too high gif fix
	@Override
	public void draw(Graphics g) {

		g.drawImage(super.getImage(), (int) (super.getDX()+super.getX()), (int) (super.getDY() + super.getY()+super.getHeight()*.1), (int) super.getWidth(), (int) super.getHeight(), null);
	}
}
