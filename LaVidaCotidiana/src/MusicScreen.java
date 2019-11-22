import processing.core.*;

public class MusicScreen extends Screen{

	MusicScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1800;
		height = 1000;
		screenName = name;
		// color = parent.color(100, 82, 86);
		color = col;
	}

	public void display() {
		// background
		parent.fill(color);
		parent.rect(x, y, width, height);
	}
}
