
//Wren Lee
//Tokenizer code from Dr. Brown

import java.util.*;

public class TextTokenizer {

	public TextTokenizer(String aSearchText) {
		if (aSearchText == null) {
			throw new IllegalArgumentException("Search Text cannot be null.");
		}
		fSearchText = aSearchText;
	}

	public ArrayList<String> parseSearchText() {
		ArrayList<String> result = new ArrayList<>();

		boolean returnTokens = true;
		String currentDelims = fWHITESPACE_AND_QUOTES_PUNCTUATION;
		StringTokenizer parser = new StringTokenizer(fSearchText, currentDelims, true);

		String token = null;
		while (parser.hasMoreTokens()) {
			token = parser.nextToken(currentDelims);
			if (!isWhitespace(token) && !fWHITESPACE_AND_QUOTES_PUNCTUATION.contains(token)) {// && !(isHash(token))) {
				addNonTrivialWordToResult(token, result); // totally misnomer bc this adds ALL words now
			}
		}
		return result;
	}

	// PRIVATE
	private String fSearchText;
	private static final ArrayList<String> fCOMMON_WORDS = new ArrayList<>();

	private static final String fWHITESPACE_AND_QUOTES_PUNCTUATION = " \t\r\n\",!?;:()/\\—";
	//ORIGINAL: private static final String fWHITESPACE_AND_QUOTES_PUNCTUATION = " \t\r\n\",.!?;:()/\\—";
	// private static final String fWHITESPACE_AND_QUOTES_PUNCTUATION = "
	// \t\r\n\",.!?;:()/\\";
	private static final String fWHITESPACE = " \t\r\n";
	private static final String fQUOTES_ONLY = "\"";

	private static final String fHASHES = "#@";

	/** Very common words to be excluded from searches. */
	static {
		fCOMMON_WORDS.add("a");
		fCOMMON_WORDS.add("and");
		fCOMMON_WORDS.add("be");
		fCOMMON_WORDS.add("for");
		fCOMMON_WORDS.add("from");
		fCOMMON_WORDS.add("has");
		fCOMMON_WORDS.add("i");
		fCOMMON_WORDS.add("in");
		fCOMMON_WORDS.add("is");
		fCOMMON_WORDS.add("it");
		fCOMMON_WORDS.add("of");
		fCOMMON_WORDS.add("on");
		fCOMMON_WORDS.add("to");
		fCOMMON_WORDS.add("the");
	}

	/**
	 * Use to determine if a particular word entered in the search box should be
	 * discarded from the search.
	 */
	private boolean isCommonWord(String aSearchTokenCandidate) {
		return fCOMMON_WORDS.contains(aSearchTokenCandidate);
	}

	private boolean textHasContent(String aText) {
		return (aText != null) && (!aText.trim().equals(""));
	}

	private void addNonTrivialWordToResult(String aToken, ArrayList<String> aResult) {
		aResult.add(aToken.trim());
	}

	private boolean isWhitespace(String aToken) {
		return fWHITESPACE.contains(aToken);
	}

	private boolean isHash(String aToken) {
		String first = aToken.substring(0, 1);
		return fHASHES.contains(first);
	}

}
