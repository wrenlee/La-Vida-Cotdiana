import java.util.ArrayList;

import processing.core.*;

public class AboutScreen extends Screen {
	PImage about;
	AboutScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1800;
		height = 1000;
		screenName = name;
		// color = parent.color(100, 82, 86);
		color = col;
		about = parent.loadImage("data/about.png");
	}
	public void display() {
		// background
//		parent.fill(color);
//		parent.rect(x, y, width, height);
		parent.image(about, width / 2, height / 2);
	}
}
