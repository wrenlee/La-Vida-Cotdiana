
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
	int pastelRed = color(255, 183, 178);
	int pastelOrange = color(255, 218, 193);
	int pastelYellow = color(226, 240, 203);
	int pastelPurple = color(219, 196, 223);
	int pastelBlue = color(199, 206, 234);

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
	MidiFileToNotes midiNotes;
	MarkovGeneratorN rhythm;
	MarkovGeneratorN pitch;

	ArrayList<String> possibleFirstString;

	// generators
	ProbabilityGenerator probGen;
	MarkovGeneratorN eventText;
	MarkovGeneratorN eventSubject;

	// screens
	int screenState;
	Screen[] screenArr;

	// button
	Button startButton;

	// mouse
	Mouse mouse;

	// scanner
	Scanner scanner;
	String name;

	public static void main(String[] args) {
		PApplet.main("LaVidaCotidianaMain");

	}

	public void settings() {
		size(1300, 800);
	}

	public void setup() {
		// screen initialization
		screenState = 0;
		screenArr = new Screen[4];
		screenArr[0] = new StartScreen(this, 's', pastelPink); // start
		screenArr[1] = new DesktopScreen(this, 'd', pastelGreen); // desktop

		// buttons
		startButton = new Button(this, width / 2 - 50, height / 2 - 25);
		mouse = new Mouse(this);

		order = 2;

		loadedText = new ArrayList<ArrayList<String>>();
		probGen = new ProbabilityGenerator();
		possibleFirstString = new ArrayList<String>();
		eventText = new MarkovGeneratorN();
		eventSubject = new MarkovGeneratorN();

		// scanner
//		scanner = new Scanner(System.in);
//		name = scanner.nextLine();

		// songs
		filePath = new ArrayList<String>();
//		filePath.add(getPath("mid/Coldplay - Viva La Vida.mid"));//coldplay
//		filePath.add(getPath("mid/John Denver - Take Me Home Country Roads.mid"));//country roads
//		filePath.add(getPath("mid/Queen - Bohemian Rhapsody,mid"));//queen
//		filePath.add(getPath("mid/Tokyo Ghoul - Unravel.mid"));//tokyo ghoul

		// midinotes array
		// one player
		// pitch array
		// rhythm array

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

		tokenizer();
		train();
		firstString();
		generateEvent();
	}

	public void draw() {
//		if(isPlay) {
//			player.play();
//		}

		// screenArr[0].display();
		if (screenState == 0) {
			screenArr[0].display();
			startButton.display("Click to start");
			if (startButton.isOver()) {
				screenState = 1;
			}
		}
		//System.out.println(screenState);
//		} else if (screenState == 1) {
//			desktop();
//		}

		// mouse
		mouse.display();
	}

	public void start() {
		// screenArr[0].display();
		// startButton.display("Click to start");
//		if (startButton.isOver()) {
//			screenState = 1;
//		}
	}
//
//	public void desktop() {
//		screenArr[1].display();
//	}

	public void tokenizer() {
		loadNovel("data/dedman_emails_body.txt");
		// loadNovel("data/dedman_emails_subject.txt");
	}

	public void firstString() {
		possibleFirstString.add("The");
		possibleFirstString.add("Join");
		possibleFirstString.add("Come");
		possibleFirstString.add("This");
		possibleFirstString.add("You");
	}

	public void train() {
		probGen.train(loadedText.get(0));
		eventText.train(loadedText.get(0), order);
		// eventSubject.train(loadedText.get(1), 1);
	}

	public void generateEvent() {
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

		// int index = eventText.getSeqArr().indexOf(initString); // if it's not a
		// sequence, reroll
		// if (index == -1) {
		// ArrayList<String> lastToken = new ArrayList<String>(); //last token
		// lastToken.add(initString.get(i - 1));
		// ArrayList<String> gen = new ArrayList<String>();
		// String gen = eventText.generateMultiple(lastToken, order);
		// System.out.println(gen);
		// }
//			else {
//				String gen = (String) eventText.generate(initString);
//				initString.add(gen);
		// }
		// i++;
		// } // generate initial string

		// System.out.print(initString);

		// generate moar
		ArrayList<String> genArr = new ArrayList<String>();
		int numGen = (int) random(10, 50);
		genArr = eventText.generateMultiple(initString, numGen);
		// System.out.println(genArr);

//		for (int k = 0; k < initString.size(); k++) {
//			genArr.add(initString.get(k));
//		}

		// add a period
//		for (int i = 0; i < genArr.size(); i++) {
//			ArrayList<Character> charArr = new ArrayList<Character>();
//			String tempStr = genArr.get(i);
//			// System.out.println(tempStr.charAt(i));
//			// System.out.println("Temp String " + tempStr);
//			// System.out.println(tempStr.length());
//			for (int j = 0; j < tempStr.length(); j++) {
//				charArr.add(tempStr.charAt(j));
//				// System.out.println("Char Arr at " + charArr);
//				float randomNum = random(20);
//				charArr.get(j);
//				if (Character.isUpperCase(charArr.get(j)) == true && randomNum > 5) {
//					// System.out.println("PERIOD");
//					genArr.set(j, genArr.get(i) + "."); // add a period
////					if (i <= genArr.size()) {//capitalize next word
////						String nextWrd = genArr.get(i + 1); // next word
////						Character nextChar = nextWrd.charAt(0);
////						//System.out.println(nextWrd);
////						//System.out.println(nextChar);
////						Character.toUpperCase(nextChar);
////						//System.out.println(nextChar);
////					}
//				}
//			}
//			// some exceptions
//			if (genArr.get(i) == "https") {
//				genArr.set(i, "https://");
//			} // https//
//			else if (genArr.get(i) == "www") {
//				genArr.set(i, "www.");
//			} // www.
//			else if (genArr.get(i) == "org") {
//				genArr.set(i, ".org");
//			} // .org
//			else if (genArr.get(i) == "com") {
//				genArr.set(i, ".com");
//			} // .com
//			else if (genArr.get(i) == "edu") {
//				genArr.set(i, ".edu");
//			} // .com
//		} // gen array loop

		// add spaces
		for (int j = 0; j < genArr.size() - 1; j++) {
			email = email + genArr.get(j) + " ";
		}
		email = email + genArr.get(genArr.size() - 1) + "."; // end with period
		System.out.println("Email: " + email);
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
