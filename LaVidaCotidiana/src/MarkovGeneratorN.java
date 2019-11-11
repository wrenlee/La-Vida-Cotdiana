
//Wren Lee
//Generic class with train and generate function for the markov generator

import java.util.ArrayList;

public class MarkovGeneratorN<T> extends ProbabilityGenerator {

	/*
	 * inherited variables from probability generator class ArrayList<T> inputArr =
	 * new ArrayList(); // arraylist to hold variable amount of input ArrayList<T>
	 * alphabetArr = new ArrayList(); //arraylist for generates tokens, based on
	 * alphabet ArrayList<Integer> alphabetCounts = new ArrayList<Integer>();
	 * //arraylist to hold the number of each unique token ArrayList<Float>
	 * probabilityArr = new ArrayList<Float>(); //arraylist to hold all the
	 * probabilities ArrayList<T> genArr = new ArrayList(); //arraylist to hold all
	 * of the generated values
	 */
	ArrayList<ArrayList<Integer>> transTable = new ArrayList(); // transition table 2d arraylist
	ArrayList<Float> markovProbabilityArr = new ArrayList<Float>();
	ArrayList<T> uniqueAlphabetSequence = new ArrayList(); // unique sequences

	ProbabilityGenerator<T> generator = new ProbabilityGenerator();
	MarkovGenerator<T> markovGen = new MarkovGenerator();

	int indexWithNoChance = -1;
	
	public ArrayList getSeqArr() {
		return uniqueAlphabetSequence;
	}

	public void train(ArrayList inArr, int orderN) {
		generator.train(inArr);
		markovGen.train(inArr);

		// variables
		int tokenIndex = 0; // index within the alphabet array, token index
		int rowIndex = 0; // the previous index, last index

		for (int i = orderN - 1; i < inArr.size() - 1; i++) {// stepping through input array, i = input index, current
																// index
			// System.out.println("Input Arr " + inArr);
			ArrayList<T> currentSeq = new ArrayList<T>(inArr.subList(i - (orderN - 1), i + 1)); // creates current
																								// sequence i, i +
																								// orderN
			// System.out.println("Current sequence " + currentSeq);

			// check currentSeq if in the unique alphabet sequence
			rowIndex = uniqueAlphabetSequence.indexOf(currentSeq); // initialization of token index to the first value
			// System.out.println("Check Seq " + checkSeq);
			if (rowIndex == -1) {
				rowIndex = uniqueAlphabetSequence.size();
				// System.out.println("rowIndex " + rowIndex);
				uniqueAlphabetSequence.add((T) currentSeq); // add current sequence to the unique sequences
				// create new rows based on how big the alphabet is
				ArrayList<Integer> row = new ArrayList(alphabetArr.size()); // makes the row
				for (int k = 0; k < alphabetArr.size(); k++) {
					row.add((int) 0.0f);
				} // add zeros to the existing array
				transTable.add(row); // add the row to the transition table
			} // no other token exist

			// find next token
			if (i + 1 < inArr.size()) {
				T nextToken = (T) inArr.get(i + 1); // next token
				// System.out.println("Next Token: " + nextToken);
				tokenIndex = alphabetArr.indexOf(nextToken);
				if (tokenIndex == -1) {
					tokenIndex = alphabetArr.size();
					alphabetArr.add(nextToken);
					// create new columns, horizontal
					for (int j = 0; j < uniqueAlphabetSequence.size(); j++) {
						// System.out.println("Adding the 0s");
						transTable.get(j).add((int) 0.0f); // adds 0s to the row which is the size of the token index
					} // adds to the column
				} // if the next token doesn't exist
			} // if there's another token

			// find token and update counts
			if (rowIndex >= 0) {
				float indexAdd;
				ArrayList<Integer> row = transTable.get(rowIndex); // accesses the row that corresponds to the last
																	// index row in the transition table
				// System.out.println("Row " + row);
				indexAdd = row.get(tokenIndex); // access column as determined by token index
				// System.out.println("Before " + row.get(tokenIndex));
				row.set(tokenIndex, (int) (indexAdd + 1)); // changes the index by adding one
				// System.out.println("After " + row.get(tokenIndex));
			} // if it already exists

			// rowIndex = tokenIndex;
		} // stepping through input array
			// System.out.println("Unique sequences " + uniqueAlphabetSequence);
			// System.out.println(alphabetArr);
			// System.out.println(uniqueAlphabetSequence);
	}// train function

	public void printTransTable(ArrayList row) {
		System.out.println(row);
	}// print transition table

	public void probabilities(ArrayList row, int index) {
		// System.out.println(row);
		markovProbabilityArr.clear();
		float totalCounts = 0;
		for (int i = 0; i < row.size(); i++) {
			totalCounts = (float) row.get(i) + totalCounts; // add all counts up
			// System.out.println("Total counts " + totalCounts);
		} // gets total
		if (totalCounts == 0) {
			indexWithNoChance = index;
			for (int i = 0; i < row.size(); i++) {
				markovProbabilityArr.add(0.0f);
			} // make all the probabilities 0
		} // total counts 0
		else {
			for (int j = 0; j < row.size(); j++) {
				markovProbabilityArr.add(((float) row.get(j) / totalCounts)); // add the probabilities from each of the
																				// counts
				// System.out.println("Prob " + (float) row.get(j) / totalCounts);
			} // gets probabilities
		} // if total counts isn't 0
		markovProbabilities();
	}// probabilities function

	public void markovProbabilities() {
		for (int i = 0; i < transTable.size(); i++) {
			ArrayList<Integer> tempArr = new ArrayList(); // makes temp array to hold values
			tempArr = transTable.get(i);
			probabilities(tempArr, i);
		} // loops through transition table and adds in the rows
	}// finds probabilities of markov generator

	public void printMarkovProbabilities() {
		for (int i = 0; i < uniqueAlphabetSequence.size(); i++) {
			probabilities(transTable.get(i), i);
			System.out.print(uniqueAlphabetSequence.get(i));
			System.out.println(markovProbabilityArr);
		} // loops through transition table and adds in the rows
	}// finds probabilities of markov generator

	// markov generator generate() comes from the prob gen's generate()
	public T generate(ArrayList initToken) { // generate one note first from the prob gen generate
		// System.out.println("Init token " + initToken);
		assert (markovProbabilityArr.size() > 1); // this object hasn't been trained

		T generated = null;
		int seqIndex = uniqueAlphabetSequence.indexOf(initToken);
		// System.out.println("Init token " + initToken);
		// System.out.println("seq index " + seqIndex);
		if (seqIndex == -1) {
			// calls markov 1 generator
			T firstToken = (T) initToken.get(0);
			//System.out.println("first token " + firstToken);
			generated = markovGen.generate(firstToken);
			System.out.println("not found");
		} // if not found
		else {
			// System.out.println("seq index " + seqIndex);
			// calls probability generator
			ArrayList<Integer> row = (transTable.get(seqIndex));
			// System.out.println(row);
			probabilities(row);
			//System.out.println(row);
			generated = (T) super.generate(probabilityArr);
			System.out.println("Found");
//				System.out.println("FOUND: " + seqIndex);
//				System.out.println("generated: " + generated);
//				System.out.println("row: " + row);
		} // if found

		if (seqIndex == indexWithNoChance) {
			markovGen.generate();
		} // if the row number has -1 probability
		return generated;
	}// generate functions

	public ArrayList<T> generateMultiple(ArrayList initToken, int n) { // generate multiple notes
		ArrayList<T> genArr = new ArrayList();
		for (int i = 0; i < n; i++) {
			//System.out.println("Init token " + initToken);
			T token = generate(initToken); // generate new token
			//System.out.println("Generated token " + token);
			initToken.remove(0); // remove first token
			initToken.add(token); // add new token to initial sequence
			genArr.add(token); // add new token to generated array
		}
		return genArr;
	}// generate multiple function

	public ArrayList<T> generateMultipleAndPrint(ArrayList initToken, int n) { // generate multiple notes
		ArrayList<T> genArr = new ArrayList();
		for (int i = 0; i < n; i++) {
			T token = generate(initToken); // generate new token
			initToken.remove(0); // remove first token
			initToken.add(token); // add new token to initial sequence
			genArr.add(token); // add new token to generated array
		}
		return genArr;
	}// generate multiple function

	public void unitTest1() {// print out transition table
		System.out.println("-----Transition Table-----");
		System.out.println(alphabetArr);
		for (int i = 0; i < alphabetArr.size(); i++) {
			System.out.print(uniqueAlphabetSequence.get(i));
			printTransTable(transTable.get(i));
		} // loop through alphabet array
		System.out.println("");
	}// unit test one: print probabilities

}// markov generator class definition ends