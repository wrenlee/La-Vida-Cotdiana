import java.util.ArrayList;

import processing.core.*;

public class EmailReadScreen extends Screen{
	
	EmailReadScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = parent.width;
		height = parent.height;
		screenName = name;
		color = col;
	}

	public void display() {
		// background
		parent.fill(color);
		parent.rect(x, y, width, height);
	}
	
	public void display(String name) {
		// background
		parent.fill(color);
		parent.rect(x, y, width, height);
	}
	
	public void displayEmail(ArrayList<String> subArr) {
		System.out.println("SCREEN CLASSSSSS");
	}
}
