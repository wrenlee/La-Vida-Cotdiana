import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class StartScreen extends Screen {

	//Button startButton;
	//String name;
	//Scanner scanner;
	
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
		// startButton = new Button(parent, width / 2 - 50, height / 2 - 25);
		//scanner = new Scanner(System.in);
//		startButton = new Button(parent, parent.width / 2, parent.height / 2);
	}

//	public void addName() {
//		name = scanner.nextLine();
//	}
//	
//	public String getName() {
//		return name;
//	}

	public void display(String name) {
		computerCat.resize(500, 800);
		parent.image(computerCat, 0, 0);
	}
//
//	public int isClicked() {
//		int state = 0;
//		if (startButton.isOver()) {
//			state = 1;
//		}
//		return state;
//	}
}
