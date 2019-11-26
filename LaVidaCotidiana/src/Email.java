import processing.core.*;

public class Email {
	int x;
	int y;
	int width;
	int height;
	int color;
	String message;
	PApplet parent;

	public Email(PApplet p, int xPos, int yPos) {
		parent = p;
		x = xPos;
		y = yPos;
	}

	public Email(PApplet p, int xPos, int yPos, String mes) {
		parent = p;
		x = xPos;
		y = yPos;
		message = mes;
		width = 1200;
	}

	public void display() {
		parent.fill(0);
		parent.text(message, x + 10, y + 20);
	}

	public void setMessage(String mes) {
		message = mes;
	}

	public void displayMessage() {
		System.out.println(message);
	}

}
