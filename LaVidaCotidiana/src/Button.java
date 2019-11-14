import processing.core.PApplet;

public class Button {
	PApplet parent;
	int x;
	int y;
	int width;
	int height;
	boolean isClicked;
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
		height = 50;
	}

	public boolean isOver() {
		if (parent.mouseX > x && parent.mouseX < x + width && parent.mouseY > y && parent.mouseY < y + height
				&& parent.mousePressed == true) {
			isClicked = true;
			System.out.println("is clicked");
		} else {
			isClicked = false;
		}
		return isClicked;
	}

	public void display(String mes) {
		message = mes;
		int mesLen = message.length();
		int width = mesLen + 100;
		
		parent.fill(0, 50);
		parent.rect(x - (width / 2), y, mesLen + 105, height + 5);//shadow
		parent.fill(color);
		parent.rect(x - (width / 2), y, mesLen + 100, height);
		parent.fill(0);
		parent.text(message, x - (width / 2) + mesLen, y + height / 2);
	}

}
