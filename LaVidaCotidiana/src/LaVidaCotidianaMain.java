
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

import ddf.minim.*;
import ddf.minim.effects.*;

public class LaVidaCotidianaMain extends PApplet {
	float screenWidth;
	float screenHeight;
	
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

	PImage cat;

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
	EmailButton coldplay;
	EmailButton countryRoads;
	EmailButton anime;
	Button backButton;
	EmailButton aboutButton;
	Button backButton2;
	Button backButton3;
	Button stopButton;

	SoundFile sf1;
	SoundFile sf2;
	SoundFile sf4;
	boolean coldPlay = false;
	boolean roadPlay = false;
	boolean animePlay = false;
	
	boolean markovLoaded = false; 
	boolean soundLoaded = false; 

	public static void main(String[] args) {
		PApplet.main("LaVidaCotidianaMain");

	}

	public void settings() {
		fullScreen();
	}

	public void setup() {
		screenWidth = width;
		screenHeight = height;
		
		cat = loadImage("data/computer cat.gif");

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
		
		System.out.println("loading...");
		
		// buttons
		startButton = new Button(this, width / 2, height / 2);
		emailButton = new EmailButton(this, 100, 200, pastelRed);
		musicButton = new EmailButton(this, 100, 400, pastelBlue);
		aboutButton = new EmailButton(this, 100, 600, pastelPink);
		nameButton = new Button(this, 0, 0);
		backButton = new Button(this, 65, 780);
		backButton2 = new Button(this, 65, 780);
		backButton3 = new Button(this, 65, 780);

		coldplay = new EmailButton(this, 200, 100, pastelBlue);
		countryRoads = new EmailButton(this, 400, 100, pastelOrange);
		anime = new EmailButton(this, 600, 100, pastelOrange);
		stopButton = new Button(this, 800, 100);

		order = 3;
		
		System.out.println("loading...");
		
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
		
		System.out.println("loading...");

		PApplet parent = this; 
		
		(new Thread(new Runnable(){
			   public void run(){
					sf1 = new SoundFile(parent, "data/coldplay.mp3");
					sf2 = new SoundFile(parent, "data/road.mp3");
					sf4 = new SoundFile(parent, "data/anime.mp3");
					soundLoaded = true; 
					}
			})).start();
		
		(new Thread(new Runnable(){
			   public void run(){
		
		tokenizer();
		train();
		firstString();
		generate();
		for (int i = 0; i < 20; i++) {
			generateEventSubject();
		}
		EmailSmall tempSub;
		Email tempEmail;
		for (int i = 0; i < subGenerated.size(); i++) {// initialize emails
			tempSub = new EmailSmall(parent);
			subjectArr.add(tempSub);
		}
		for (int i = 0; i < emailText.size(); i++) {
			tempEmail = new Email(parent, screenWidth * (265/1440.0f), screenHeight * (210/800.0f));
			allEmails.add(tempEmail);
		}
		 markovLoaded = true; 
		
			}
		})).start();
	}

	public void draw() {
		noStroke();
		screenArr[0].display();
		if( !markovLoaded || !soundLoaded)
			startButton.display("Loading!");
		else
			startButton.display("Click to start");

		if (screenState == 1) {
			desktop();
		} else if (screenState == 2) {
			email();
		} else if (screenState == 3) {
			music();
		} else if (screenState == 4) {
			about();
		}
	}

	public void mouseClicked() {
		if( !markovLoaded || !soundLoaded)
			return; 

		
		if (startButton.isOver()) {
			screenState = 1;
		} else if (emailButton.isOver()) {
			screenState = 2;
		} else if (musicButton.isOver()) {
			screenState = 3;
		} else if (aboutButton.isOver()) {
			screenState = 4;
		}

		// back buttons
		else if (backButton.isOver()) {
			screenState = 1;
		} else if (backButton2.isOver()) {
			screenState = 1;
		} else if (backButton3.isOver()) {
			screenState = 1;
		} else if (stopButton.isOver()) {
			System.out.println("Is over music stop");
			coldPlay = false;
			roadPlay = false;
			animePlay = false;
		}

		if (coldPlay && (sf1.isPlaying() == false) && (sf2.isPlaying() == false) && (sf4.isPlaying() == false)) {
			sf1.play();
		} else if (coldPlay == false) {
			sf1.stop();
		}
		if (roadPlay && (sf1.isPlaying() == false) && (sf2.isPlaying() == false) && (sf4.isPlaying() == false)) {
			sf2.play();
		} else if (roadPlay == false) {
			sf2.stop();
		}
		if (animePlay && (sf1.isPlaying() == false) && (sf2.isPlaying() == false) && (sf4.isPlaying() == false)) {
			sf4.play();
		} else if (animePlay == false) {
			sf4.stop();
		}
	}

	public void desktop() {
		screenArr[1].display();
		emailButton.display("Email");
		musicButton.display("Music Player");
		aboutButton.display("About");
	}

	public void email() {	
		screenArr[2].display();
		// loop to display subjects
		for (int i = 0; i < subGenerated.size(); i++) {
			subjectArr.get(i).display(subGenerated.get(i), screenWidth * (265/1440.0f), (i * 60) + (screenHeight * (140/900.0f)));
		}
		// display emails
		for (int i = 0; i < emailText.size(); i++) {
			allEmails.get(i).setMessage(emailText.get(i));
		}
		// check if the subject is clicked
		for (int i = 0; i < emailText.size(); i++) {
			if (subjectArr.get(i).isOver()) {
				fill(pastelPurple);
				rect(screenWidth * (265/1440.0f), screenHeight * (140/900.0f), 1200, 800);
				fill(0);
				text("To: tu@correoelectronico.com", screenWidth * (265/1440.0f), (screenHeight * (160/900.0f)));
				text("From: tuvidacotidiana@correoelectronico.com ", screenWidth * (265/1440.0f), screenHeight * (190/ 900.0f));
				fill(pastelBlue);
				rect(screenWidth * (265/1440.0f), screenHeight * (300/900.0f), 1200, 500);
				allEmails.get(i).display();
				image(cat, screenWidth * (300/1440.0f), screenHeight * (310/900.0f));

			}
		}
		backButton.display("Back");
	}

	public void music() {
		screenArr[3].display();

		coldplay.display("Nostalgia");
		if (coldplay.isOver()) {
			coldPlay = true;
		}
		countryRoads.display("Meme-y");
		if (countryRoads.isOver()) {
			roadPlay = true;
		}
		anime.display("Nerdy");
		if (anime.isOver()) {
			animePlay = true;
		}

		backButton2.display("Back");
		stopButton.display("Stop the music");
	}

	public void about() {
		screenArr[5].display();
		backButton3.display("Back");
	}

	public void desktop2() {
		desktop();
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
		for (int i = 0; i < 20; i++) {
			genArr.add(eventText.generateMultiple(initString, numGen));
		}
		System.out.println(genArr.size());

		// add spaces
		String email = "";
		for (int i = 0; i < genArr.size() - 1; i++) {
			email = email + first;
			ArrayList<String> tempGen = new ArrayList<String>();
			tempGen = genArr.get(i);
			for (int j = 0; j < genArr.get(i).size() - 1; j++) {
				email = email + tempGen.get(j) + " ";
			}
			emailText.add(email); // add to entire array email
			email = ""; // clears email string
		}
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
