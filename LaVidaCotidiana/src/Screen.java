import processing.core.*;

public class Screen {
	PApplet parent;
	//variables
	int x;
	int y;
	int width;
	int height;
	Character screenName;
	int color = parent.color(100, 82, 86);
	
	Screen(){
		x = 0;
		y = 0;
		width = 1300;
		height = 800;
		screenName = 'z';
	}
	
	Screen(PApplet p) {
	    parent = p;
	}

	Screen(PApplet p, Character name) {
		parent = p;
		x = 0;
		y = 0;
		width = 1300;
		height = 800;
		screenName = name;
	}
	
	public void display() {
		//background
		parent.fill(color);
		parent.rect(x, y, width, height);
	}

}
