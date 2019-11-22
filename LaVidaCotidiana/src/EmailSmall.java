import processing.core.*;
import java.util.ArrayList;

public class EmailSmall {
	int x;
	int y;
	int width;
	int height;
	int color;
	PApplet parent;
	boolean isClicked;

	public EmailSmall(PApplet p, int xPos, int yPos) {
		parent = p;
		x = xPos;
		y = yPos;
		width = 1000;
		height = 50;
	}
	
	public EmailSmall(PApplet p) {
		parent = p;
		width = 1500;
		height = 50;
	}
	
	public void display(String subject, int xPos, int yPos) {
		x = xPos;
		y = yPos;
		color = parent.color(226, 240, 203);
		width = subject.length() * 100;
		
		//System.out.println(subject);
		
		parent.fill(color); // yellow!
		parent.rect(x, y, width, height); //rect box
		parent.fill(0);
		parent.text(subject, x + 10, y + 30); //message
	}
	
	public boolean isOver() {
		int xBound1 = x - (width / 2);
		int xBound2 = x + (width / 2);
		if ((parent.mouseX > xBound1 && parent.mouseX < xBound2) && (parent.mouseY > y && parent.mouseY < y + height) && parent.mousePressed == true) {
			System.out.println("is over email small");
			isClicked = true;
		}
		return isClicked;
	}

}
