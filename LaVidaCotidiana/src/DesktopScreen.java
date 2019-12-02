import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class DesktopScreen extends Screen {

	// 2 buttons
	String name;
	Bubble[] bubbleArr;
	PImage background;
	PImage bar;
	
	DesktopScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = parent.width;
		height = parent.height;
		screenName = name;
		color = col;
		bubbleArr = new Bubble[10];
		for (int i = 0; i < 10; i++) {
			bubbleArr[i] = new Bubble(parent);
		}
		background = parent.loadImage("data/kitteh.jpg");
		bar = parent.loadImage("data/bar.png");
		cat = parent.loadImage("data/cat.png");
		catX = (int) parent.random(100);
		catY = (int) parent.random(100);
		catSpeed = (int) parent.random(1, 5);
		catSpeedY = (int) parent.random(1,3);
	}

	public void display() {
		parent.noStroke();
		background.resize(parent.width, parent.height);
		parent.image(background, 0, 0);
		parent.fill(parent.color(255, 207, 240));// pink
		parent.rect(x, y, width, 35); // top box
		
		//bottom bar
		bar.resize(1100, 80);
		parent.image(bar, 150, 800);
		
		// create welcome message
		parent.fill(0);
		parent.text("Welcome to La Vida Cotidiana", x + 10, y + 20);
		parent.text("Move your mouse to start the magic!", x + 300, y + 20);
		
		for (int i = 0; i < 10; i++) {
			bubbleArr[i].display();
			bubbleArr[i].move();
			bubbleArr[i].boop();
		}
	}
}
