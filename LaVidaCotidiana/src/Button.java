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
		width = 10;
		height = 50;
	}

	public void display(String mes) {
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
		//System.out.println("IS OVER function");
		//System.out.println(x);
		//System.out.println("X " + (x - (width / 2)) + ", x + width " + (x + (width / 2)));
		//System.out.println(parent.mouseX);
		//System.out.println(width);
		int xBound1 = x - (width / 2);
		int xBound2 = x + (width / 2);
//		System.out.println(xBound1);
//		System.out.println(xBound2);
		if ((parent.mouseX > xBound1 && parent.mouseX < xBound2)){
//			 && (parent.mouseY > y && parent.mouseY < y + height)
//				&& parent.mousePressed == true) {
			isClicked = true;
			//System.out.println("is clicked");
		} else {
			isClicked = false;
		}
		return isClicked;
	}

}
