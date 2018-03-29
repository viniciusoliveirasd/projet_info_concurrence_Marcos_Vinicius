

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;
import com.swabunga.spell.event.TeXWordFinder;

public class JazzySpellChecker implements SpellCheckListener {

	private SpellChecker spellChecker;
	private SpellChecker names_spellChecker;
	private List<String> misspelledWords;
	

	/**
	 * get a list of misspelled words from the text
	 * @param text
	 */
	public List<String> getMisspelledWords(String text) {
		StringWordTokenizer texTok = new StringWordTokenizer(text,
				new TeXWordFinder());
		spellChecker.checkSpelling(texTok); 
		return misspelledWords;
	}

	private static SpellDictionaryHashMap dictionaryHashMap;

	//We will need  to move it to the main thread of the speedtyper
	static{

		File dict = new File("dictionary/dictionary.txt");

		
		try {
			dictionaryHashMap = new SpellDictionaryHashMap(dict);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize(){
		spellChecker = new SpellChecker(dictionaryHashMap);

		spellChecker.addSpellCheckListener(this);

	}


	public JazzySpellChecker() {
		
		misspelledWords = new ArrayList<String>();

		initialize();
	}

	/**
	 * correct the misspelled words in the input String and return the result
	 */
	public String getCorrectedLine(String line){
		List<String> misSpelledWords = getMisspelledWords(line);

		for (String misSpelledWord : misSpelledWords){
			List<String> suggestions = getSuggestions(misSpelledWord);
			if (suggestions.size() == 0)
				continue;
			String bestSuggestion = suggestions.get(0);
			line = line.replace(misSpelledWord, bestSuggestion);
		}
		return line;
	}

	
	// Function to return points
	public float getPointsOfWord(String word) {
		//int points = 0;
		//float twitter_multiplicator= 1;
		//float single_character_multiplicator; 
		//float movie_title_multiplicator; 
		//float spotify; 

		List<String> misSpelledWords = getMisspelledWords(word);
		
		//Word is correct
		
		
		
		if(misSpelledWords.size() == 0) {
			//Check bonification
			float length_word = (float)word.length();
			return (length_word);
		}else {
			return (-1*word.length()); //Define if a wrong word is going to take minus points
		}
		
		/*
		for (String misSpelledWord : misSpelledWords){
			System.out.println(misSpelledWord);
			System.out.println("pato");
			
			List<String> suggestions = getSuggestions(misSpelledWord);
			for (String suggestion : suggestions) {
				System.out.println("---" + suggestion);
			}
			//It means it is a good word 
			if (suggestions.size() == 0) {
				//add bonification functions
				float length_word = (float)word.length();
				return (length_word)*(twitter_multiplicator);
			}
			else {
			
			}
			///Maybe we save to show the errors at the end
			//String bestSuggestion = suggestions.get(0);
			//line = line.replace(misSpelledWord, bestSuggestion);
		}
		*/
	}
		public String getCorrectedText(String line){
			StringBuilder builder = new StringBuilder();
			String[] tempWords = line.split(" ");
			for (String tempWord : tempWords){
				if (!spellChecker.isCorrect(tempWord)){
					List<Word> suggestions = spellChecker.getSuggestions(tempWord, 0);
					if (suggestions.size() > 0){
						builder.append(spellChecker.getSuggestions(tempWord, 0).get(0).toString());
					}
					else
						builder.append(tempWord);
				}
				else {
					builder.append(tempWord);
				}
				builder.append(" ");
			}
			return builder.toString().trim();
		}


		public List<String> getSuggestions(String misspelledWord){
			@SuppressWarnings("unchecked")
			List<Word> su99esti0ns = spellChecker.getSuggestions(misspelledWord, 0);
			List<String> suggestions = new ArrayList<String>();
			for (Word suggestion : su99esti0ns){
				suggestions.add(suggestion.getWord());
			}
			return suggestions;
		}


		@Override
		public void spellingError(SpellCheckEvent event) {
			event.ignoreWord(true);
			misspelledWords.add(event.getInvalidWord());
			
		}

		public static void main(String[] args) {
			System.out.println("before");
			JazzySpellChecker jazzySpellChecker = new JazzySpellChecker();
			System.out.println("after");
			String to_be_checked = "duck";
			String line = jazzySpellChecker.getCorrectedLine(to_be_checked);
			float points = jazzySpellChecker.getPointsOfWord(to_be_checked);
			System.out.println(points);
			System.out.println(line);
			System.out.println("waiting");
			String to_be_checked_2 = "drive";
			String line_2 = jazzySpellChecker.getCorrectedLine(to_be_checked_2);
			float points_2 = jazzySpellChecker.getPointsOfWord(to_be_checked_2);
			System.out.println(points_2);
			System.out.println(line_2);
		
		}
	}