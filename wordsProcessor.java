

import java.util.concurrent.ConcurrentLinkedQueue;

import com.swabunga.spell.engine.SpellDictionaryHashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcos Moreira
 */
public class wordsProcessor extends Thread{
    
    ConcurrentLinkedQueue<Double> newPoints;
    ConcurrentLinkedQueue<String> words;
    //alteracao adicionar hashmaps
    private SpellDictionaryHashMap englishWord_dictionaryHashMap;
    private SpellDictionaryHashMap englishName_dictionaryHashMap;
    //Initialize unitary threads (one thread for each feature
    Thread_feature englishDictionary_thread;
    Thread_feature englishNameDictionary_thread;
    boolean onGame;
    
    wordsProcessor( ConcurrentLinkedQueue<Double> newPoints,ConcurrentLinkedQueue<String> words,
    		SpellDictionaryHashMap englishWord_dictionaryHashMap, SpellDictionaryHashMap englishName_dictionaryHashMap )
    {
        super();
        this.newPoints = newPoints;
        this.words = words;
        onGame = true;
        this.englishName_dictionaryHashMap = 
        this.englishWord_dictionaryHashMap = englishWord_dictionaryHashMap;
        englishDictionary_thread = new Thread_feature(null, englishWord_dictionaryHashMap, onGame, 2);
        englishNameDictionary_thread = new Thread_feature(null, englishName_dictionaryHashMap, onGame, (float)0.5);
        englishDictionary_thread.start();
        englishNameDictionary_thread.start();
        
    }
    
    public void run()
    {
        
    	
    	while(true)
        {
        	if(!onGame)
        		break;
              if(!words.isEmpty())
              {
            	  String aux = words.poll();
            	  if(aux.length()>0)
            		  newPoints.add(judge(aux));  
            	
              }
                
        }
    	
    	englishDictionary_thread.stopThread();
    	englishNameDictionary_thread.stopThread();
    	
          
    }
    
    public void stopProcessor()
    {
    	onGame = false;
    }
    
    double judge(String word)
    {
        float word_lenght = word.length();
    	englishDictionary_thread.change_word(word);
    	englishNameDictionary_thread.change_word(word);
    	float multiplier_english_word = englishDictionary_thread.get_word_multiplier(); 
        float multiplier_english_name = englishNameDictionary_thread.get_word_multiplier();
    	return word_lenght*multiplier_english_name*multiplier_english_word;
    }
    
}