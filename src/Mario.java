import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class Mario extends MovingObject {

	private static Image westImg, eastImg;

	public Mario(double x, double y) {
		super(x, y, "/res/images/marioR.gif", 1);
		openDirectionalImages();
		setDirection(GameObject.EAST);
	}

	private void openDirectionalImages() {
		eastImg = super.getImage();

		String filePath = "/res/images/marioL.gif";
		try {
			System.out.println("IMAGE LOADED" + filePath);
			URL url = getClass().getResource(filePath);
			westImg = new ImageIcon(url).getImage();
		} catch (Exception e) {
			System.out.println("Problem opening the image at " + filePath);
			e.printStackTrace();
		}
	}

	@Override
	public void setDirection(int d) {
		super.setDirection(d);
		if (d==GameObject.WEST) {
			super.setImage(westImg);
		}
		else {
			super.setImage(eastImg);
		}
	}

}
