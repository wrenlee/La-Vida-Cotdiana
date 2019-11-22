import processing.core.PApplet;

public class EmailButton extends Button{
	PApplet parent;
	int x;
	int y;
	int width;
	int height;
	boolean isClicked;
	int color;
	String message;

	EmailButton() {
		x = 10;
		y = 10;
		width = 10;
		height = 50;
	}

	EmailButton(PApplet p, int xPos, int yPos) {
		parent = p;
		x = xPos;
		y = yPos;
		color = parent.color(207, 255, 213); // green
		width = 10;
		height = 50;
	}
	
	EmailButton(PApplet p, int xPos, int yPos, int col) {
		parent = p;
		x = xPos;
		y = yPos;
		color = col;
		width = 10;
		height = 50;
	}

	public void display(String mes) {
		message = mes;
		int mesLen = message.length();
		width = mesLen + 100;

		parent.fill(0, 50);
		parent.rect(x - (width / 2), y, mesLen + 105, mesLen + 105);// shadow
		parent.fill(color);
		parent.rect(x - (width / 2), y, mesLen + 100, mesLen + 100);
		parent.fill(0);
		parent.line(x - (width / 2) - ((mesLen + 300) / 2), y - ((mesLen + 300) / 2), x - (width / 2), y + (mesLen + 300));
		parent.text(mes, x - (width / 2) + 35, (y + height / 2) + 30);
	}

	public boolean isOver() {
		int xBound1 = x - (width / 2);
		int xBound2 = x + (width / 2);
		if ((parent.mouseX > xBound1 && parent.mouseX < xBound2) && (parent.mouseY > y && parent.mouseY < y + height) && parent.mousePressed == true) {
			//System.out.println("Email CLICKED!");
			isClicked = true;
		}
		return isClicked;
	}

}
