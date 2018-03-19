


import java.util.concurrent.ConcurrentLinkedQueue;

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
    boolean onGame;
    
    wordsProcessor( ConcurrentLinkedQueue<Double> newPoints,ConcurrentLinkedQueue<String> words)
    {
        super();
        this.newPoints = newPoints;
        this.words = words;
        onGame = true;
    }
    
    public void run()
    {
        while(true)
        {
        	if(!onGame)
        		return;
              if(!words.isEmpty())
              {
            	  String aux = words.poll();
            	  if(aux.length()>0)
            		  newPoints.add(judge(aux));  
              }
                
        }
          
    }
    
    public void stopProcessor()
    {
    	onGame = false;
    }
    
    double judge(String word)
    {
        return word.length() - 4;
    }
    
}
