import processing.core.PApplet;

public class Mouse {
	
	int x;
	int y;
	PApplet parent;
	
	Mouse(PApplet p){
		parent = p;
	}
	
	public void display() {
		x = parent.mouseX;
		y = parent.mouseY;
		parent.fill(0);
		parent.triangle(x, y, x + 15, y + 15, x - 5, y + 20);
	}
}
