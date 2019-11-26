import java.util.ArrayList;

import processing.core.*;

public class AboutScreen extends Screen {
	PImage about;
	Button button;
	
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
		button = new Button(parent, width - 500, 740);
	}
	public void display() {
		about.resize(1440, 900);
		parent.image(about, 0, 0);
		//button.display("Back");
	}
	
	public boolean buttonOver() {
		boolean over = false;
		if(button.isOver()) {
			over = true;
		}
		return over;
	}
}
