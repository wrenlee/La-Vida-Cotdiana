import processing.core.*;

public class MusicScreen extends Screen{
	PImage cat;

	MusicScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1800;
		height = 1000;
		screenName = name;
		// color = parent.color(100, 82, 86);
		color = col;
		cat = parent.loadImage("data/cat music.jpg");
	}

	public void display() {
		cat.resize(1440, 900);
		parent.image(cat, 0,0);
	}
}
