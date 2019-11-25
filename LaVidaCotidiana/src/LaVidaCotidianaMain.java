
//Wren Lee
//final project
//main file!

//processing itself
import processing.core.*;

//processing libraries
import interfascia.*;
import processing.sound.*;

import java.util.*;
import java.io.*;

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LaVidaCotidianaMain extends PApplet {
	// color variables
	int pastelPink = color(255, 207, 240);
	int pastelGreen = color(207, 255, 213);
	int pastelRed = color(255, 183, 178);
	int pastelOrange = color(255, 218, 193);
	int pastelYellow = color(226, 240, 203);
	int pastelPurple = color(219, 196, 223);
	int pastelBlue = color(199, 206, 234);
	int white = color(231, 240, 241);
	PFont font;

	// text tokenizer
	ArrayList<ArrayList<String>> loadedText; // arraylist of tokens
	ArrayList<String> filePath;
	private ArrayList<String> tokens;

	private static final String fPUNCTUATION = "\",.!?;:()/\\";
	private static final String fENDPUNCTUATION = ".!?;,";
	private static final String fREALENDPUNCTUATION = ".!?";
	private static final String fWHITESPACE = "\t\r\n ";

	int order; // order n value

	// music player
	boolean isPlay;
	MelodyPlayer player;
	ArrayList<MidiFileToNotes> midiNotes;
	ArrayList<MarkovGeneratorN> rhythm;
	ArrayList<MarkovGeneratorN> pitch;

	ArrayList<String> possibleFirstString;

	// generators
	ProbabilityGenerator probGenDedman;
	ProbabilityGenerator probGenDining;
	MarkovGeneratorN eventText;
	MarkovGeneratorN eventSubject;
	MarkovGeneratorN diningText;
	MarkovGeneratorN jobText;
	MarkovGeneratorN jobLocation;
	ArrayList<String> subGenerated;
	ArrayList<ArrayList<String>> genEmail;
	ArrayList<String> emailText;

	ArrayList<EmailSmall> subjectArr = new ArrayList<EmailSmall>();
	ArrayList<Email> allEmails = new ArrayList<Email>();

	// screens
	int screenState;
	Screen[] screenArr;
	PImage background;
	StartScreen screen;
	DesktopScreen desktopScreen;

	// button
	Button startButton;
	EmailButton emailButton;
	EmailButton musicButton;
	Button nameButton;
	MusicButton coldplay;
	MusicButton countryRoads;
	MusicButton queen;
	MusicButton anime;
	Button backButton;
	EmailButton aboutButton;
	Button backButton2;
	Button backButton3;

	SoundFile sf1;
	SoundFile sf2;
	SoundFile sf3;
	SoundFile sf4;

	GUIController controller;
	IFTextField type;
	IFLabel l;
	IFTextField typeName = new IFTextField("Name", width / 2 - 50, height / 2 - 100, 50);
	String name = "";

	public static void main(String[] args) {
		PApplet.main("LaVidaCotidianaMain");

	}

	public void settings() {
		// size(1300, 800);
		fullScreen();
	}

	public void setup() {
		controller = new GUIController(this);
		type = new IFTextField("Reply", 300, 330, 1000);
		l = new IFLabel("Reply", 300, 330);
		controller.add(type);
		controller.add(l);
		type.addActionListener(this);

		font = createFont("data/StrawberryMilkshake.ttf", 17);
		textFont(font);

		// screen initialization
		screenState = 0;
		screenArr = new Screen[6];
		screenArr[0] = new StartScreen(this, 's', pastelPink); // start
		screenArr[1] = new DesktopScreen(this, 'd', pastelGreen); // desktop
		screenArr[2] = new EmailScreen(this, 'e', pastelPurple); // email
		screenArr[3] = new MusicScreen(this, 'm', pastelGreen); // music
		screenArr[4] = new EmailReadScreen(this, 'r', white);
		screenArr[5] = new AboutScreen(this, 'a', pastelPink);

		// screen = new StartScreen(this, 's', pastelPink);
		// desktopScreen = new DesktopScreen(this, 'd', pastelGreen);

		// buttons
		startButton = new Button(this, width / 2, height / 2);
		emailButton = new EmailButton(this, 100, 200, pastelRed);
		musicButton = new EmailButton(this, 100, 400, pastelBlue);
		aboutButton = new EmailButton(this, 100, 600, pastelPink);
		nameButton = new Button(this, 0, 0);
		backButton = new Button(this, 65, 780);
		backButton2 = new Button(this, 65, 780);
		backButton3 = new Button(this, 1800 - 500, 740);

		coldplay = new MusicButton(this, 200, 100, pastelBlue);
		countryRoads = new MusicButton(this, 400, 100, pastelOrange);
		queen = new MusicButton(this, 600, 100, pastelOrange);
		anime = new MusicButton(this, 800, 100, pastelRed);

		order = 3;

		// generators
		loadedText = new ArrayList<ArrayList<String>>();
		probGenDedman = new ProbabilityGenerator();
		probGenDining = new ProbabilityGenerator();
		possibleFirstString = new ArrayList<String>();
		eventText = new MarkovGeneratorN();
		eventSubject = new MarkovGeneratorN();
		diningText = new MarkovGeneratorN();
		jobText = new MarkovGeneratorN();
		jobLocation = new MarkovGeneratorN();
		genEmail = new ArrayList<ArrayList<String>>();
		emailText = new ArrayList<String>();
		subGenerated = new ArrayList<String>();

		// songs
//		int orderPitch = 2;
//		filePath = new ArrayList<String>(4);
//		filePath.add(getPath("mid/Coldplay - Viva La Vida.mid"));// coldplay
//		filePath.add(getPath("mid/John Denver - Take Me Home Country Roads.mid"));// country roads
//		filePath.add(getPath("mid/Queen - Bohemian Rhapsody.mid"));// queen
//		filePath.add(getPath("mid/Tokyo Ghoul - Unravel.mid"));// tokyo ghoul
//		midiNotes = new ArrayList<MidiFileToNotes>(4);
//		for (int i = 0; i < 4; i++) {
//			MidiFileToNotes path = new MidiFileToNotes(filePath.get(i));
//			midiNotes.add(path);
//			midiNotes.get(i).setWhichLine(0);
//		}
//		player = new MelodyPlayer(this, 100.0f);
//
//		pitch = new ArrayList<MarkovGeneratorN>(4);
//		rhythm = new ArrayList<MarkovGeneratorN>(4);
//		for (int i = 0; i < 4; i++) {// train generators
//			pitch.get(i).train(midiNotes.get(i).getPitchArray(), orderPitch);
//			rhythm.get(i).train(midiNotes.get(i).getRhythmArray(), orderPitch);
//		}

//		sf1 = new SoundFile(this, "data/coldplay.mp3");
//		sf2 = new SoundFile(this, "data/john denver.mp3");
//		sf3 = new SoundFile(this, "data/queen.mp3");
//		sf4 = new SoundFile(this, "data/anime.mp3");
//		player.setup();

		tokenizer();
		train();
		firstString();
		generate();
		for (int i = 0; i < 12; i++) {
			generateEventSubject();
		}
		EmailSmall tempSub;
		Email tempEmail;
		for (int i = 0; i < subGenerated.size(); i++) {// initialize emails
			tempSub = new EmailSmall(this);
			subjectArr.add(tempSub);
		}
		for (int i = 0; i < emailText.size(); i++) {
			tempEmail = new Email(this, 265, 210);
			allEmails.add(tempEmail);
		}
	}

	public void draw() {
		noStroke();
		screenArr[0].display();
		startButton.display("Click to start");
		// name = reply.getValue();
//		if (startButton.isOver()) {
//			//desktop();
//			screenState = 1;
//		}

//		System.out.println(screenState);
//		
		if (screenState == 1) {
			desktop();
		} else if (screenState == 2) {
			email();
		} else if (screenState == 3) {
			music();
		} else if (screenState == 4) {
			about();
		}

//		emailButton.isOver();

//		 //desktop buttons
//		if (emailButton.isOver()) {
//			// System.out.println("Email true!!");
//			//email();
//			screenState = 2;
//			// emailButton.reClick();
//		}
//		if (musicButton.isOver()) {
//			//music();
//			screenState = 3;
//			//System.out.println("music " + musicButton.getClicked());
//			// musicButton.reClick();
//		}
//		if (aboutButton.isOver()) {
//			//about();
//			screenState = 4;
//			//System.out.println("about " + aboutButton.getClicked());
//			// aboutButton.reClick();
//		}
//
//		// back buttons
//		if (backButton.isOver()) {
//			screenState = 1;
//			//desktop();
//			// System.out.println("Email " + emailButton.getClicked());
//		}
//		if (backButton2.isOver()) {
//			screenState = 1;
//			//desktop();
//		}
//		if (backButton3.isOver()) {
//			screenState = 1;
//			//desktop();
//		}
	}

	void actionPerformed(GUIEvent e) {
		if (e.getMessage().equals("Completed")) {
			l.setLabel(type.getValue());
		}
	}

	public void mouseClicked() {
		//System.out.println("mouse clicked func");
		if (startButton.isOver()) {
			// desktop();
			screenState = 1;
			//System.out.println("start button pressed");
		} else if (emailButton.isOver()) {
			//System.out.println("Email true!!");
			// email();
			screenState = 2;
			// emailButton.reClick();
		} else if (musicButton.isOver()) {
			// music();
			screenState = 3;
			// System.out.println("music " + musicButton.getClicked());
			// musicButton.reClick();
		} else if (aboutButton.isOver()) {
			// about();
			screenState = 4;
			// System.out.println("about " + aboutButton.getClicked());
			// aboutButton.reClick();
		}

		// back buttons
		else if (backButton.isOver()) {
			screenState = 1;
			// desktop();
			// System.out.println("Email " + emailButton.getClicked());
		} else if (backButton2.isOver()) {
			screenState = 1;
			// desktop();
		} else if (backButton3.isOver()) {
			screenState = 1;
			// desktop();
		}
	//System.out.println(screenState);
		
		for (int i = 0; i < emailText.size(); i++) {
			if (subjectArr.get(i).isOver()) {
				// System.out.println(i);
				fill(pastelPurple);
				rect(265, 140, 1200, 800);
				fill(0);
				text("To: tu@mail.com", 270, 160);
				text("From: tuvidacotidiana@mail.com ", 270, 180);
				fill(200);
				rect(265, 300, 1200, 500);
				allEmails.get(i).display();
			}
		}
	}

	public void start() {
		// screen.display();
//		screenArr[0].display();
//		startButton.display("Click to start");
//		if (startButton.isOver()) {
//			screenState = 1;
//		}
	}

	public void desktop() {
		// System.out.println("DESKTOP");
		screenArr[1].display(name);
		emailButton.display("Email");
		musicButton.display("Music Player");
		aboutButton.display("About");
	}

	public void email() {
		screenArr[2].display();
		//emailButton.reClick();
//		controller.add(type);
//		controller.add(l);
	//	type.addActionListener(this);

//		screenArr[2].init(subGenerated, emailText, subGenerated.size(), emailText.size());
//		for (int i = 0; i < subGenerated.size(); i++) {
//			screenArr[2].displayEmail(subGenerated.get(i), i);
//		}
//		screenArr[2].isOver(emailText);

		// initialize all emails
//		EmailSmall tempSub;
//		Email tempEmail;
//		for (int i = 0; i < subGenerated.size(); i++) {// initialize emails
//			tempSub = new EmailSmall(this);
//			subjectArr.add(tempSub);
//		}
//		for (int i = 0; i < emailText.size(); i++) {
//			tempEmail = new Email(this, 265, 210);
//			allEmails.add(tempEmail);
//		}

		// loop to display subjects
		for (int i = 0; i < subGenerated.size(); i++) {
			subjectArr.get(i).display(subGenerated.get(i), 265, (i * 60) + 140);
		}
		// display emails
		for (int i = 0; i < emailText.size(); i++) {
			allEmails.get(i).setMessage(emailText.get(i));
		}
		// check if the subject is clicked
		for (int i = 0; i < emailText.size(); i++) {
			if (subjectArr.get(i).isOver()) {
				// System.out.println(i);
				fill(pastelPurple);
				rect(265, 140, 1200, 800);
				fill(0);
				text("To: tu@mail.com", 270, 160);
				text("From: tuvidacotidiana@mail.com ", 270, 180);
				fill(200);
				rect(265, 300, 1200, 500);
				allEmails.get(i).display();
			}
		}
		backButton.display("Back");
//		if (backButton.isOver()) {
//			desktop2();
//			// System.out.println("Email back button is pressed");
//		}
//		
	}

	public void music() {
		screenArr[3].display();
		// musicButton.reClick();

		coldplay.display("Modern Pop");
		if (coldplay.isOver()) {
			// sf1.play();
//			player.setMelody(pitch.get(0).generateMultiple(20));
//			player.setRhythm(rhythm.get(0).generateMultiple(20));
//			isPlay = true;
		}
		countryRoads.display("Meme-y");
		if (countryRoads.isOver()) {
			// sf2.play();
//			player.setMelody(pitch.get(1).generateMultiple(20));
//			player.setRhythm(rhythm.get(1).generateMultiple(20));
//			isPlay = true;
		}
		queen.display("Classic Pop");
		if (queen.isOver()) {
			// sf3.play();
//			player.setMelody(pitch.get(2).generateMultiple(20));
//			player.setRhythm(rhythm.get(2).generateMultiple(20));
//			isPlay = true;
		}
		anime.display("Nerdy");
		if (anime.isOver()) {
			// sf4.play();
//			player.setMelody(pitch.get(3).generateMultiple(20));
//			player.setRhythm(rhythm.get(3).generateMultiple(20));
//			isPlay = true;
		}

		backButton2.display("Back");
//		if (backButton2.isOver()) {
//			// screenState = 1;
//			desktop2();
//		}

//		backButton.display("Back");
//		if (backButton.isOver()) {
//			screenArr[1].display(name);
//			emailButton.display("Email");
//			musicButton.display("Music Player");
//		}
	}

	public void about() {
		screenArr[5].display();
		backButton3.display("Back");
		// aboutButton.reClick();
//		if (screenArr[5].buttonOver()) {
//			desktop2();
//		}

		// backButton.display("Back");
//		if (backButton.isOver()) {
//			desktop();
//		}
	}

	public void desktop2() {
		System.out.println("DESKTOP 2");
		desktop();
//		screenArr[1].display(name);
//		emailButton.display("Email");
//		musicButton.display("Music Player");
//		aboutButton.display("About");
//
//		if (emailButton.isOver()) {
//			email();
//		}
//		if (musicButton.isOver()) {
//			music();
//		}
//		if (aboutButton.isOver()) {
//			about();
//		}
	}

	public void tokenizer() {
		loadNovel("data/dedman_emails_body.txt");
		loadNovel2("data/dedman_emails_subject.txt");
		loadNovel("data/dining_email.txt");
		loadNovel("data/job_title_email.txt");
		loadNovel("data/job_location.txt");
	}

	public void firstString() {
		possibleFirstString.add("The");
		possibleFirstString.add("Join");
		possibleFirstString.add("Come");
		possibleFirstString.add("This");
		possibleFirstString.add("You");
	}

	public void train() {
		probGenDedman.train(loadedText.get(0));
		eventText.train(loadedText.get(0), order);
		eventSubject.train(loadedText.get(1), order);
		probGenDining.train(loadedText.get(2));
		diningText.train(loadedText.get(2), order);
		jobText.train(loadedText.get(3), order);
		jobLocation.train(loadedText.get(4), order);
	}

	public void generate() {
		// generate initial string
		ArrayList<String> initString = new ArrayList<String>();
		int rand = (int) random(possibleFirstString.size());
		String firstGen = possibleFirstString.get(rand);
		initString.add(firstGen);
		String first = "";
		first = first + firstGen + " ";
		for (int i = 0; i < order; i++) {
			String gen = (String) eventText.generate(initString);
			initString.add(gen);
			first = first + gen + " ";
		}

		// generate moar
		ArrayList<ArrayList<String>> genArr = new ArrayList<ArrayList<String>>();
		int numGen = (int) random(10, 25);
		for (int i = 0; i < 12; i++) {
			genArr.add(eventText.generateMultiple(initString, numGen));
		}
//		for (int i = 5; i < 7; i++) {
//			genArr.add(jobText.generateMultiple(initString, numGen));
//		}
//		for(int i = 7; i < 10; i++) {
//			genArr.add(jobText.generateMultiple(initString, numGen));
//		}
		System.out.println(genArr.size());

		// add spaces
		String email = "";
		for (int i = 0; i < genArr.size() - 1; i++) {
			email = email + first;
			// System.out.println(genArr.get(i));
			ArrayList<String> tempGen = new ArrayList<String>();
			tempGen = genArr.get(i);
			for (int j = 0; j < genArr.get(i).size() - 1; j++) {
				email = email + tempGen.get(j) + " ";
				// email = email + genArr.get(i).get(j) + " ";
				// System.out.println(i + " email " + email);
			}
			// email = email + genArr.get(i).get(genArr.size() - 1) + "."; // end with
			// period
			// System.out.println(i + " email " + email);
			emailText.add(email); // add to entire array email
			email = ""; // clears email string
			// System.out.println("Email " + emailText.get(i));
		}
//		for (int i = 0; i < emailText.size(); i++) {
//			System.out.println(i + " " + emailText.get(i));
//		}
	}

	public void generateEventSubject() {
		// variables
		ProbabilityGenerator firstTokeSub = new ProbabilityGenerator();
		ArrayList<String> firstTokeSubject = new ArrayList<String>();
		ArrayList<String> gen = new ArrayList<String>();
		int rand = (int) random(1, 10);
		String subject = "";
		ArrayList<String> subjectArr = new ArrayList<String>();

		firstTokeSub.train(loadedText.get(1));
		firstTokeSubject = firstTokeSub.generateMultiple(2);
		gen = eventSubject.generateMultiple(firstTokeSubject, rand);
		for (int j = 0; j < gen.size(); j++) {
			subject = subject + gen.get(j) + " ";
		}
		subGenerated.add(subject);
	}

	void loadNovel(String p) {
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		tokens = new ArrayList<String>();

		try {
			List<String> lines = Files.readAllLines(path);

			for (int i = 0; i < lines.size(); i++) {

				TextTokenizer tokenizer = new TextTokenizer(lines.get(i));
				ArrayList<String> t = tokenizer.parseSearchText();
				tokens.addAll(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			println("Oopsie! We had a problem reading a file!");
		}
		// loadedText.set(num, tokens); //adds tokens to the slot at num
		loadedText.add(tokens);
	}

	void loadNovel2(String p) {
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		tokens = new ArrayList<String>();

		try {
			List<String> lines = Files.readAllLines(path);

			for (int i = 0; i < lines.size(); i++) {

				TextTokenizer2 tokenizer = new TextTokenizer2(lines.get(i));
				ArrayList<String> t = tokenizer.parseSearchText();
				tokens.addAll(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			println("Oopsie! We had a problem reading a file!");
		}
		// loadedText.set(num, tokens); //adds tokens to the slot at num
		loadedText.add(tokens);
	}

	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}
}// ends main
