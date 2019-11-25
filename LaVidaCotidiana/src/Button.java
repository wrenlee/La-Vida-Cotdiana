import processing.core.PApplet;

public class Button {
	PApplet parent;
	int x;
	int y;
	int width;
	int height;
	boolean isClicked = false;
	int color;
	String message;

	Button() {
		x = 10;
		y = 10;
		width = 10;
		height = 50;
	}

	Button(PApplet p, int xPos, int yPos) {
		parent = p;
		x = xPos;
		y = yPos;
		color = parent.color(207, 255, 213); // green
		width = 10;
		height = 50;
	}
	
	Button(PApplet p, int xPos, int yPos, int col) {
		parent = p;
		x = xPos;
		y = yPos;
		color = col; // green
		width = 10;
		height = 50;
	}

	public void display(String mes) {
		//System.out.println("Button!");
		message = mes;
		int mesLen = message.length();
		width = mesLen + 100;

		parent.fill(0, 50);
		parent.rect(x - (width / 2), y, mesLen + 105, height + 5);// shadow
		parent.fill(color);
		parent.rect(x - (width / 2), y, mesLen + 100, height);
		parent.fill(0);
		parent.text(message, x - (width / 2) + mesLen, y + height / 2);
	}

	public boolean isOver() {
		int xBound1 = x - (width / 2);
		int xBound2 = x + (width / 2);
		//System.out.println("x " + parent.mouseX + " y" + parent.mouseY + " xPos " + x + " yPos " + y + " mes " + message + " xbound1 " + xBound1 + " xBound 2 "+ xBound2);
		if ((parent.mouseX > xBound1 && parent.mouseX < xBound2) && (parent.mouseY > y && parent.mouseY < y + height)){
				//&& parent.mousePressed == true) {
			color = parent.color(219, 196, 223);
			isClicked = true;
		}
		else {
			//color = parent.color(0, 0, 0);
			isClicked = false;
		}
		//System.out.println(message + " " + isClicked);
		return isClicked;
	}
	
	public void reClick() {
		isClicked = false;
	}
	
	public boolean getClicked() {
		return isClicked;
	}

}
