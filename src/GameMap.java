import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public abstract class GameMap {

	private List<GameObject> movers;
	Image backgroundImage;

	public GameMap() {
		movers = new ArrayList<GameObject>();
		openBackgroundImage();
	}

	public GameMap(int r, int c) {
		openBackgroundImage();
	}

	public abstract void openBackgroundImage();

	public void add(GameObject go) {
		movers.add(go);
	}

	public void delete(GameObject go) {
		movers.remove(go);
		System.out.println(movers.size());
	}

	public void add(int location, GameObject go) {
		movers.add(location, go);
	}

	public List<GameObject> getMovers() {
		return movers;
	}

	public GameObject getMoversObject(int i) {
		return movers.get(i);
	}

	public void setMovers(List<GameObject> list) {
		movers = list;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		drawAdditional(g);
		for (int i = movers.size() - 1; i >= 0; i--) {
			movers.get(i).draw(g);
		}
	}

	protected abstract void drawAdditional(Graphics g);

	// public List<GameObject> getMovers() {
	// return movers;
	// }

}