
//Wren Lee
//final project
//main file!

import processing.core.*;

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

	// text tokennsizer
	ArrayList<ArrayList<String>> loadedText; //arraylist of tokens
	private ArrayList<String> tokens;
	private static final String fPUNCTUATION = "\",.!?;:()/\\";
	private static final String fENDPUNCTUATION = ".!?;,";
	private static final String fREALENDPUNCTUATION = ".!?";
	private static final String fWHITESPACE = "\t\r\n ";

	int order; //order n value
	
	// music player
	boolean isPlay;
	MelodyPlayer player;
	MidiFileToNotes midiNotes;
	MarkovGeneratorN rhythm;
	MarkovGeneratorN pitch;

	//generators
	ProbabilityGenerator probGen;
	MarkovGeneratorN eventText;
	MarkovGeneratorN eventSubject;

	//buttons
	Button startButton;

	//scanner
	Scanner scanner;
	String name;

	public static void main(String[] args) {
		PApplet.main("LaVidaCotidianaMain");

	}

	public void settings() {
		size(1300, 800);
	}

	public void setup() {
		order = 3;
		
		loadedText = new ArrayList<ArrayList<String>>();
		probGen = new ProbabilityGenerator();
		eventText = new MarkovGeneratorN();
		eventSubject = new MarkovGeneratorN();
		
		loadNovel("data/dedman_emails_body.txt", 0);
		loadNovel("data/dedman_emails_subject.txt", 1);
		
		fill(120, 50, 240);
		background(100);

		// scanner
//		scanner = new Scanner(System.in);
//		name = scanner.nextLine();

//		String filePath = getPath("mid/name.mid"); // test file
//		midiNotes = new MidiFileToNotes(filePath);
//		midiNotes.setWhichLine(0);
//		player = new MelodyPlayer(this, 100.0f);
//		pitch = new MarkovGeneratorN<Integer>();
//		rhythm = new MarkovGeneratorN<Double>();
//		int orderPitch = 2;
//		int orderRhythm = 2;
//		pitch.train(midiNotes.getPitchArray(), orderPitch);
//		rhythm.train(midiNotes.getRhythmArray(), orderRhythm);
//		player.setup();
//		player.setMelody(pitch.generateMultiple(20));
//		player.setRhythm(rhythm.generateMultiple(20));

		// buttons
		startButton = new Button(this, width / 2 - 50, height / 2 + 100, 100, 50);

		//train
		probGen.train(loadedText.get(0));
		eventText.train(loadedText.get(0), order);
		eventSubject.train(loadedText.get(1), order);
		
		// generate initial string
		ArrayList<String> initString = new ArrayList<String>();
		String initGen = (String) probGen.generate(); //generate first token
		//System.out.println(gen);
		initString.add(initGen);
		System.out.println(initString);
		int i = 1;
		while(i < order) {
		//for (int i = 1; i < order; i++) {
			String gen = (String) eventText.generate(initString);
			initString.add(gen);
			int index = eventText.getSeqArr().indexOf(initString); //if it's not a sequence, reroll
			if(index == -1) {
				gen = (String) eventText.generate(initString);
				System.out.println(i + ": Reroll " + gen);
				initString.set(i, gen);
			}
			i++;
		} // generate initial string
		System.out.println("Init string " + initString);
	}

	public void draw() {
		startButton.display("Start");
		//eventText.train(tokens, order);
//		if(isPlay) {
//			player.play();
//		}

	}

	public void start() {

//		fill(pastelPink); // pastel pink
//		rect(0, 0, 1300, 800);
		//fill(255); // text fill
		// text("Type in your name then press the button to start", width / 2, height /
		// 2);

//		if (name.isEmpty() == false) {
//			desktop();
//		}
//		startButton.display("Start");
//		
//		if(startButton.isClicked) {
//			desktop();
//		}//is clicked
	}

	public void desktop() {
//		fill(200);
//		rect(0, 0, 1300, 50); // stripe at top

		fill(255);
		text("Welcome to La Vida Cotidiana, " + name + "!", 100, 10);
	}

	public void music() {

	}

	public void email() {

	}

	void loadNovel(String p, int num) {
		String filePath = getPath(p);
		Path path = Paths.get(filePath);
		tokens = new ArrayList<String>();

		try {
			List<String> lines = Files.readAllLines(path);

			for (int i = 0; i < lines.size(); i++) {

				TextTokenizer tokenizer = new TextTokenizer(lines.get(i));
				Set<String> t = tokenizer.parseSearchText();
				tokens.addAll(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			println("Oopsie! We had a problem reading a file!");
		}
		//loadedText.set(num, tokens); //adds tokens to the slot at num
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
