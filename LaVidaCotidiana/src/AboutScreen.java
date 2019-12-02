import java.util.ArrayList;

import processing.core.*;

public class AboutScreen extends Screen {
	PImage about;
	Button button;
	
	AboutScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = parent.width;
		height = parent.height;
		screenName = name;
		color = col;
		about = parent.loadImage("data/about.png");
		button = new Button(parent, width - 500, 740);
	}
	public void display() {
		about.resize(parent.width, parent.height);
		parent.image(about, 0, 0);
	}
	
	public boolean buttonOver() {
		boolean over = false;
		if(button.isOver()) {
			over = true;
		}
		return over;
	}
}
