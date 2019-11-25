import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class DesktopScreen extends Screen {

	// 2 buttons
	String name;
	Bubble[] bubbleArr;
	PImage background;
	PImage bar;
	PImage cat;
	
	int catX;
	int catY;
	int catSpeed;
	int catSpeedY;
	
	DesktopScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1800;
		height = 1000;
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

	public void display(String name) {
//	public void display() {
		parent.noStroke();
		//parent.fill(color);
		//parent.rect(x, y, width, height);
		background.resize(1500, 900);
		parent.image(background, 0, 0);
		parent.fill(parent.color(255, 207, 240));// pink
		parent.rect(x, y, width, 35); // top box
		
		//bottom bar
		bar.resize(1100, 80);
		parent.image(bar, 150, 800);
		
		//cat
//		cat.resize(150, 200);
//		parent.pushMatrix();
//		parent.translate(x, y);
//		parent.image(cat, 0, 0);
//		parent.popMatrix();
		
		// create name
		parent.fill(0);
		//parent.text("Welcome " + name + " to La Vida Cotidiana", x + 10, y + 20);
		parent.text("Welcome to La Vida Cotidiana", x + 10, y + 20);
		
		parent.fill(255);
		parent.text("Move your mouse!", x + 10, y + 60);
		
		for (int i = 0; i < 10; i++) {
			bubbleArr[i].display();
			bubbleArr[i].move();
			bubbleArr[i].boop();
		}
	}
	
	public void catMove() {
//		System.out.println("Cat move!");
//		catX = catX + catSpeed;
//		catY = catY + catSpeed;
//		if(catX > parent.width || catX < 0) {
//			catSpeed *= -1;
//		}
//		if(catY > parent.displayHeight || catY < 0) {
//			catSpeedY *= -1;
//		}
	}

	public Character getName() {
		return screenName;
	}
}
