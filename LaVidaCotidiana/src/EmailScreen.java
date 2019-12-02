import java.util.ArrayList;

import processing.core.*;
import processing.core.PImage;

public class EmailScreen extends Screen {

	ArrayList<EmailSmall> subjectArr = new ArrayList<EmailSmall>(); // holds individual emails
	ArrayList<Email> allEmails = new ArrayList<Email>();
	EmailSmall tempSub;
	Email tempEmail;
	PImage email;

	EmailScreen(PApplet p, Character name, int col) {
		parent = p;
		x = 0;
		y = 0;
		width = parent.width;
		height = parent.height;
		screenName = name;
		color = col;
		email = parent.loadImage("data/email.png");
	}

	public void init(ArrayList<String> subArr, ArrayList<String> bodyArr, int subNum, int bodyNum) {
		for (int i = 0; i < subNum; i++) {// initialize emails
			tempSub = new EmailSmall(parent);
			subjectArr.add(tempSub);
		}

		// initialize all emails
		for (int i = 0; i < bodyNum; i++) {
			tempEmail = new Email(parent, 50, (i * 100));
			allEmails.add(tempEmail);
		}
	}

	public void display() {
		email.resize(width, height);
		parent.image(email, 0, 0);
		parent.fill(200);
		parent.rect(265, 140, parent.width - 100, 800);
		parent.fill(parent.color(255, 207, 240));// pink
		parent.rect(x, y, width, 35); // top box
		parent.fill(0);
		parent.text("Click and hold to read the emails and view a surprise!", 10, 20);
	}

	public void displayEmail(String subject, int num) {
			subjectArr.get(num).display(subject, 265, (num * 60) + 140);
	}

	public void isOver(ArrayList<String> email) {
		for (int i = 0; i < allEmails.size() - 1; i++) {
			allEmails.get(i).setMessage(email.get(i));
			if (subjectArr.get(i).isOver()) {// enter email
				allEmails.get(i).display();
			}
		}
	}
}
