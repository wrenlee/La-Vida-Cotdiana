import processing.core.*;
import java.util.ArrayList;

public class EmailSmall {
	float x;
	float y;
	int width;
	int height;
	int color;
	PApplet parent;
	boolean isClicked;

	public EmailSmall(PApplet p, float xPos, float yPos) {
		parent = p;
		x = xPos;
		y = yPos;
		width = parent.width;
		height = 50;
	}
	
	public EmailSmall(PApplet p) {
		parent = p;
		width = parent.width;
		height = 50;
	}
	
	public void display(String subject, float xPos, float yPos) {
		x = xPos;
		y = yPos;
		color = parent.color(226, 240, 203);
		width = parent.width;
		
		parent.fill(color); // yellow!
		parent.rect(x, y, width, height); //rect box
		parent.fill(0);
		parent.text(subject, x + 10, y + 30); //message
	}
	
	public boolean isOver() {
		float xBound1 = x - (width / 2);
		float xBound2 = x + width;
		if ((parent.mouseX > xBound1 && parent.mouseX < xBound2) && (parent.mouseY > y && parent.mouseY < y + height) && parent.mousePressed == true) {
			isClicked = true;
		}
		else {
			isClicked = false;
		}
		return isClicked;
	}

}
