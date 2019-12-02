import processing.core.*;

public class Email {
	float x;
	float y;
	int width;
	int height;
	int color;
	String message;
	PApplet parent;

	public Email(PApplet p, float xPos, float yPos) {
		parent = p;
		x = xPos;
		y = yPos;
	}

	public Email(PApplet p, float xPos, float yPos, String mes) {
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
