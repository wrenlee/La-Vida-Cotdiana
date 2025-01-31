import java.util.ArrayList;

import processing.core.*;

public class Screen {
	PApplet parent;
	// variables
	int x;
	int y;
	int width;
	int height;
	Character screenName;
	int color;
	Button button;

	ArrayList<EmailSmall> subjectArr = new ArrayList<EmailSmall>(); // holds individual emails
	ArrayList<Email> allEmails = new ArrayList<Email>();
	EmailSmall tempSub;
	Email tempEmail;

	PImage cat;
	int catX;
	int catY;
	int catSpeed;
	int catSpeedY;

	Screen() {
		x = 0;
		y = 0;
//		width = parent.width;
//		height = parent.height;
		screenName = 'z';
	}

	Screen(PApplet p) {
		parent = p;
	}

	Screen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = parent.width;
		height = parent.height;
		screenName = name;
		color = col;
		button = new Button();
	}

	public void init(ArrayList<String> subArr, ArrayList<String> bodyArr, int subNum, int bodyNum) {
		for (int i = 0; i < subNum; i++) {// initialize emails
			tempSub = new EmailSmall(parent);
			subjectArr.add(tempSub);
		}

		// initialize all emails
		for (int i = 0; i < bodyNum; i++) {
			tempEmail = new Email(parent, 10, (i * 100) + 10);
			allEmails.add(tempEmail);
		}
	}

	public void display() {
		// background
		parent.fill(color);
		parent.rect(x, y, width, height);
	}

	public void display(String name) {
		// background
		parent.fill(color);
		parent.rect(x, y, width, height);
	}

	public void displayEmail(String subject, int num) {
		for (int i = 0; i < num; i++) {// display emails
			subjectArr.get(i).display(subject, 10, (i * 100) + 10);
		}
	}

	public void displayEmail(String subject) {
		for (int i = 0; i < subjectArr.size(); i++) {// display emails
			System.out.println(i);
			subjectArr.get(i).display(subject, 10, (i * 100) + 10);
		}
	}

	public void isOver(ArrayList<String> email) {
		for (int i = 0; i < allEmails.size(); i++) {
			allEmails.get(i).setMessage(email.get(i));
			if (subjectArr.get(i).isOver()) {// enter email
				allEmails.get(i).display();
			}
		}
	}

	public int isClicked() {
		int state = 0;
		if (button.isOver()) {
			state = 1;
		}
		return state;
	}
	
	public boolean buttonOver() {
		boolean over = false;
		if(button.isOver()) {
			over = true;
		}
		return over;
	}
}
