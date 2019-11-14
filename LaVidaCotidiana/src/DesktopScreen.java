import processing.core.PApplet;
import java.util.*;

public class DesktopScreen extends Screen {

	//2 buttons
	String name; 

	DesktopScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1300;
		height = 800;
		screenName = name;
		color = col;
	}

	public void display() {
		parent.noStroke();
		parent.fill(color);
		parent.rect(x, y, width, height);
		//parent.rect();
		//parent.text()
	}
	
	public Character getName() {
		return screenName;
	}
}
