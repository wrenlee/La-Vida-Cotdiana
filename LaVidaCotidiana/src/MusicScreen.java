import processing.core.*;

public class MusicScreen extends Screen{
	PImage cat;

	MusicScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = parent.width;
		height = parent.height;
		screenName = name;
		color = col;
		cat = parent.loadImage("data/cat music.jpg");
	}

	public void display() {
		cat.resize(parent.width, parent.height);
		parent.image(cat, 0,0);
	}
}
