import processing.core.PApplet;

public class Button {
	PApplet parent;
	int x;
	int y;
	int width;
	int height;
	boolean isClicked;
	int color = parent.color(207, 255, 213); //green
	String message;
	
	Button(){
		x = 10;
		y = 10;
		width = 10;
		height = 20;
	}
	
	Button(PApplet p, int xPos, int yPos, int w, int h){
		parent = p;
		x = xPos;
		y = yPos;
		width = w;
		height = h;
	}
	
	public boolean isOver() {
		if(parent.mouseX > x && parent.mouseX < x + width && parent.mouseY > y && parent.mouseY < y + height && parent.mousePressed == true) {
			isClicked = true;
		}
		else {
			isClicked = false;
		}
		return isClicked;
	}
	
	public void display(String mes) {
		System.out.println("Display");
		message = mes;
		parent.fill(color);
		parent.rect(x, y, width, height);
		parent.text(message, x + 5, y + 5);
	}

}
