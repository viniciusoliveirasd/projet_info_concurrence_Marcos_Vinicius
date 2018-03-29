

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

public class WordJudge implements SpellCheckListener {

	private SpellChecker spellChecker;
	private SpellChecker names_spellChecker;
	private List<String> misspelledWords;
	private static SpellDictionaryHashMap dictionaryHashMap;
	String word_to_be_searched;
	private float final_multiplier;

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


	public WordJudge(String word, SpellDictionaryHashMap dict, float finalMultiplier) {
		this.word_to_be_searched = word;
		dictionaryHashMap = dict;
		this.final_multiplier = finalMultiplier;
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
	public float getMultiplier(String word) {
		////System.out.println("--------------------start");
		
		List<String> misSpelledWords = getMisspelledWords(word);
		//System.out.println("misspelled lenght" + misSpelledWords.size());
		////		for (String string : misSpelledWords) {
		////			System.out.println(string);
		////		}
				
		////System.out.println(word);
		
		//Word is correct
		if(misSpelledWords.size() == 0) {
			//Check bonification
			////	System.out.println(this.final_multiplier);
			////    System.out.println("--------------------end");
			misSpelledWords.clear();
			System.out.println(this.final_multiplier);
			return this.final_multiplier;
		}else {
			////    System.out.println(1);
			misSpelledWords.clear();
			////System.out.println("--------------------end");
			return 1; // The word will not be affected by the feature in question 
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
		/*
		public static void main(String[] args) {
			
			File dict = new File("dictionary/dictionary.txt");
			
			//This is the most time consuming part of the Thread
			try {
				dictionaryHashMap = new SpellDictionaryHashMap(dict);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//End of the time-consuming thread
			System.out.println("Nipples");
			
			WordJudge teste_jazzySpellChecker = new WordJudge("drive" , dictionaryHashMap, 2);
			String to_be_checked = "drive";
			String line = teste_jazzySpellChecker.getCorrectedLine(to_be_checked);
			float points = teste_jazzySpellChecker.getMultiplier("drive");
			System.out.println(points);
			System.out.println(line);
		
		
		}

		*/
	}

