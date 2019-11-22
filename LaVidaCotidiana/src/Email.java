import processing.core.*;

public class Email {
	int x;
	int y;
	int width;
	int height;
	int color;
	String message;
	PApplet parent;
	
	public Email(PApplet p, int xPos, int yPos){
		parent = p;	
		x = xPos;
		y = yPos;
	}
	
	public Email(PApplet p, int xPos, int yPos, String mes){
		parent = p;	
		x = xPos;
		y = yPos;
		message = mes;
	}
	
	public void display() {
		width = message.length() * 100;
		parent.rect(x - (width / 2), y - (height / 2), width, height);
		parent.fill(0);
		parent.text(message, x + 10, y + 20);
	}
	
	public void setMessage(String mes) {
		message = mes;
	}

}
