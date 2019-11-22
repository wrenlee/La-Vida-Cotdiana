
//Wren Lee
//final project
//main file!

import processing.core.*;
import interfascia.*;

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

	// screens
	int screenState;
	Screen[] screenArr;
	PImage background;
	Screen screen;

	// button
	Button startButton;
	EmailButton emailButton;
	MusicButton musicButton;
	Button nameButton;
	MusicButton coldplay;
	MusicButton countryRoads;
	MusicButton queen;
	MusicButton anime;
	Button backButton;

	String name = "";

	public static void main(String[] args) {
		PApplet.main("LaVidaCotidianaMain");

	}

	public void settings() {
		// size(1300, 800);
		fullScreen();
	}

	public void setup() {
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
		
		screen = new StartScreen(this, 's', pastelPink);

		// buttons
		startButton = new Button(this, width / 2, height / 2);
		emailButton = new EmailButton(this, 100, 200, pastelRed);
		musicButton = new MusicButton(this, 100, 500, pastelBlue);
		nameButton = new Button(this, 0, 0);
		backButton = new Button(this, 70, 40);
		coldplay = new MusicButton(this, 500, 300, pastelBlue);
		countryRoads = new MusicButton(this, 500, 500, pastelOrange);
		queen = new MusicButton(this, 800, 300, pastelOrange);
		anime = new MusicButton(this, 800, 500, pastelRed);

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
		int orderPitch = 2;
		int orderRhythm = 2;
		filePath = new ArrayList<String>(4);
		filePath.add(getPath("mid/Coldplay - Viva La Vida.mid"));// coldplay
		filePath.add(getPath("mid/John Denver - Take Me Home Country Roads.mid"));// country roads
		filePath.add(getPath("mid/Queen - Bohemian Rhapsody.mid"));// queen
		filePath.add(getPath("mid/Tokyo Ghoul - Unravel.mid"));// tokyo ghoul
		midiNotes = new ArrayList<MidiFileToNotes>(4);
		for (int i = 0; i < 4; i++) {
			MidiFileToNotes path = new MidiFileToNotes(filePath.get(i));
			midiNotes.add(path);
			midiNotes.get(i).setWhichLine(0);
		}
		player = new MelodyPlayer(this, 100.0f);

		pitch = new ArrayList<MarkovGeneratorN>(4);
		rhythm = new ArrayList<MarkovGeneratorN>(4);
//		for (int i = 0; i < 4; i++) {// train generators
//			pitch.get(i).train(midiNotes.get(i).getPitchArray(), orderPitch);
//			rhythm.get(i).train(midiNotes.get(i).getRhythmArray(), orderPitch);
//		}
		player.setup();

		tokenizer();
		train();
		firstString();
		generate();
		for (int i = 0; i < 8; i++) {
			generateEventSubject();
		}
	}

	public void draw() {
		noStroke();

		//start();
	//	if (screenState == 0) {
			start();
	//	}
//		} else if (screenState == 1) {
//			desktop();
//		} else if (screenState == 2) {
//			email();
//		} else if (screenState == 3) {
//			music();
//		}
//		else if (screenState == 4) {
//			about();
//		}
//		
//		screenArr[0].display();
//		startButton.display("Click to start");
//		if (startButton.isOver()) {
//			screenArr[1].display();
//			emailButton.display("Email");
//			musicButton.display("Music Player");
//			if (emailButton.isOver()) {
//				screenArr[2].display();
//				screenArr[2].init(subGenerated, emailText, subGenerated.size(), emailText.size());
//				for (int i = 0; i < subGenerated.size(); i++) {
//					screenArr[2].displayEmail(subGenerated.get(i), i);
//				}
//				// screenArr[2].isOver(emailText);
//				backButton.display("Back");
//				if (backButton.isOver()) {
//					screenArr[1].display();
//					emailButton.display("Email");
//					musicButton.display("Music Player");
//				}
//			} else if (musicButton.isOver()) {
//				screenArr[3].display();
//				coldplay.display("Modern Pop");
//				if (coldplay.isOver()) {
//					player.setMelody(pitch.get(0).generateMultiple(20));
//					player.setRhythm(rhythm.get(0).generateMultiple(20));
//					isPlay = true;
//				}
//				countryRoads.display("Meme-y");
//				if (countryRoads.isOver()) {
//					player.setMelody(pitch.get(1).generateMultiple(20));
//					player.setRhythm(rhythm.get(1).generateMultiple(20));
//					isPlay = true;
//				}
//				queen.display("Classic Pop");
//				if (queen.isOver()) {
//					player.setMelody(pitch.get(2).generateMultiple(20));
//					player.setRhythm(rhythm.get(2).generateMultiple(20));
//					isPlay = true;
//				}
//				anime.display("Nerdy");
//				if (anime.isOver()) {
//					player.setMelody(pitch.get(3).generateMultiple(20));
//					player.setRhythm(rhythm.get(3).generateMultiple(20));
//					isPlay = true;
//				}
//				backButton.display("Back");
//				if (backButton.isOver()) {
//					screenArr[1].display(name);
//					emailButton.display("Email");
//					musicButton.display("Music Player");
//				}
//
//				backButton.display("Back");
//				if (backButton.isOver()) {
//					screenState = 1;
//				}
//			}
		}

//		if (isPlay) {
//			player.play();
//		}
	//}

	public void start() {
		screen.display();
//		screenArr[0].display();
//		startButton.display("Click to start");
//		if (startButton.isOver()) {
//			screenState = 1;
//		}
	}
//
//	public void desktop() {
//		screenArr[1].display(name);
//		emailButton.display("Email");
//		musicButton.display("Music Player");
//		if (emailButton.isOver()) {
//			screenState = 2;
//		} else if (musicButton.isOver()) {
//			screenState = 3;
//		}
//	}
//
//	public void email() {
//		screenArr[2].display();
//		screenArr[2].init(subGenerated, emailText, subGenerated.size(), emailText.size());
//		for (int i = 0; i < subGenerated.size(); i++) {
//			screenArr[2].displayEmail(subGenerated.get(i), i);
//		}
//		// screenArr[2].isOver(emailText);
//		backButton.display("Back");
//		if (backButton.isOver()) {
//			screenState = 1;
//		}
//
//	}
//
//	public void music() {
//		screenArr[3].display();
//		coldplay.display("Modern Pop");
//		if (coldplay.isOver()) {
//			player.setMelody(pitch.get(0).generateMultiple(20));
//			player.setRhythm(rhythm.get(0).generateMultiple(20));
//			isPlay = true;
//		}
//		countryRoads.display("Meme-y");
//		if (countryRoads.isOver()) {
//			player.setMelody(pitch.get(1).generateMultiple(20));
//			player.setRhythm(rhythm.get(1).generateMultiple(20));
//			isPlay = true;
//		}
//		queen.display("Classic Pop");
//		if (queen.isOver()) {
//			player.setMelody(pitch.get(2).generateMultiple(20));
//			player.setRhythm(rhythm.get(2).generateMultiple(20));
//			isPlay = true;
//		}
//		anime.display("Nerdy");
//		if (anime.isOver()) {
//			player.setMelody(pitch.get(3).generateMultiple(20));
//			player.setRhythm(rhythm.get(3).generateMultiple(20));
//			isPlay = true;
//		}
//		backButton.display("Back");
//		if (backButton.isOver()) {
//			screenArr[1].display(name);
//			emailButton.display("Email");
//			musicButton.display("Music Player");
//		}
//
//		backButton.display("Back");
//		if (backButton.isOver()) {
//			screenState = 1;
//		}
//	}
//	
//	public void about() {
//		screenArr[4].display();
//		backButton.display("Back");
//		if (backButton.isOver()) {
//			screenState = 1;
//		}
//	}

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
		String email = "";
		email = email + firstGen + " ";
		for (int i = 0; i < order; i++) {
			String gen = (String) eventText.generate(initString);
			initString.add(gen);
			email = email + gen + " ";
		}

		// generate moar
		ArrayList<ArrayList<String>> genArr = new ArrayList<ArrayList<String>>();
		int numGen = (int) random(10, 50);
		for (int i = 0; i < 5; i++) {
			genArr.add(eventText.generateMultiple(initString, numGen));
		}
//		for (int i = 5; i < 7; i++) {
//			genArr.add(jobText.generateMultiple(initString, numGen));
//		}
//		for(int i = 7; i < 10; i++) {
//			genArr.add(jobText.generateMultiple(initString, numGen));
//		}
		// System.out.println(genArr);

		// add spaces
		for (int i = 0; i < genArr.size(); i++) {
			for (int j = 0; j < genArr.get(i).size() - 1; j++) {
			}
			email = email + genArr.get(genArr.size() - 1) + "."; // end with period
			emailText.add(email); // add to entire array email
		}
	}

	public void generateEventSubject() {
		// variables
		ProbabilityGenerator firstTokeSub = new ProbabilityGenerator();
		ArrayList<String> firstTokeSubject = new ArrayList<String>();
		ArrayList<String> gen = new ArrayList<String>();
		int rand = (int) random(1, 5);
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
