import processing.core.PApplet;
import java.util.*;

public class StartScreen extends Screen {

	Button startButton;
	String name;
	Scanner scanner; 

	StartScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1300;
		height = 800;
		screenName = name;
		color = col;
		startButton = new Button(parent, width / 2 - 50, height / 2 - 25);
		scanner = new Scanner(System.in);
	}

//	public void addName() {
//		name = scanner.nextLine();
//	}
//	
//	public String getName() {
//		return name;
//	}

	public void display() {
		parent.noStroke();
		parent.fill(color);
		parent.rect(x, y, width, height);
		parent.text("Welcome to La Vida Cotidiana", width / 2 - 300, height / 2 - 50);
		startButton.display("Click to start");
	}
	
	public Character getName() {
		if(startButton.isOver()) {
			screenName = 'd';
		}
		return screenName;
	}
}
