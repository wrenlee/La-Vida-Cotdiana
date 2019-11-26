import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class StartScreen extends Screen {
	
	PImage computerCat;

	StartScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1800;
		height = 1000;
		screenName = name;
		color = col;
		computerCat = parent.loadImage("data/computer cat.gif");
	}

	public void display() {
		parent.fill(parent.color(255, 207, 240));
		parent.rect(0, 0, width, height);
		computerCat.resize(650, 500);
		parent.image(computerCat, parent.width/2 - 325, 520);
		parent.fill(0);
		parent.text("Welcome to La Vida Cotidiana", parent.width/ 2 - 100, parent.height/2 - 20);
	}
}
