import processing.core.PApplet;
import java.util.*;

public class Bubble {
	float x;
	float y;
	float size;
	float xSpeed;
	float ySpeed;
	int color;
	PApplet parent;
	
	public Bubble(PApplet p){
		parent = p;
		x = parent.random(1300);
		y = parent.random(50, 800);
		size = parent.random(50, 200);
		xSpeed = parent.random(-10, 10);
		ySpeed = parent.random(-5, 5);
		color = parent.color(parent.random(255), parent.random(255), parent.random(255));
	}
	
	public void display() {
		parent.fill(color);
		parent.ellipse(x,  y,  size,  size);
	}
	
	public void move() {
		x += xSpeed;
		y += ySpeed;
		
		if(x < 0 || x > 1300) {
			xSpeed *= -1;
		}
		if(y < 30 || y > 800) {
			ySpeed *= -1;
		}
	}
	
	public void boop() {
		if((parent.mouseX > x - size && parent.mouseX < x + size)){
			xSpeed *= -1;
		}
	}

}
