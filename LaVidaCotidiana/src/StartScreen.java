import processing.core.PApplet;
import java.util.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class StartScreen extends Screen {

	//Button startButton;
	String name;
	Scanner scanner; 

	StartScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = 1800;
		height = 1000;
		screenName = name;
		color = col;
		//startButton = new Button(parent, width / 2 - 50, height / 2 - 25);
		scanner = new Scanner(System.in);
	}

//	public void addName() {
//		name = scanner.nextLine();
//	}
//	
//	public String getName() {
//		return name;
//	}

	public void display(String name) {
		parent.noStroke();
		parent.fill(color);
		parent.rect(x, y, width, height);
		
//		startButton.display("Click to start");
//		
//		startButton.isOver();
//		if(startButton.isOver()) {
//			screenName = 'd';
//			System.out.println(screenName);
//		}
	}
}
