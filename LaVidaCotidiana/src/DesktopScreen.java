import processing.core.PApplet;
import java.util.*;

public class DesktopScreen extends Screen {

	// 2 buttons
	String name;
	Bubble[] bubbleArr;

	DesktopScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1300;
		height = 800;
		screenName = name;
		color = col;
		bubbleArr = new Bubble[10];
		for (int i = 0; i < 10; i++) {
			bubbleArr[i] = new Bubble(parent);
		}
	}

	public void display() {
		parent.noStroke();
		parent.fill(color);
		parent.rect(x, y, width, height);
		parent.fill(parent.color(255, 207, 240));// pink
		parent.rect(x, y, width, 35); // top box
		// create name
		parent.fill(0);
		parent.text("Welcome " + name + " to La Vida Cotidiana", x + 10, y + 20);

		for (int i = 0; i < 10; i++) {
			bubbleArr[i].display();
			bubbleArr[i].move();
			bubbleArr[i].boop();
		}
	}

	public Character getName() {
		return screenName;
	}
}
