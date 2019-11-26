import processing.core.PApplet;

public class EmailButton extends Button{
	
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
		parent.text(mes, x - (width / 2) + 10, (y + height / 2) + 30);
	}

}
