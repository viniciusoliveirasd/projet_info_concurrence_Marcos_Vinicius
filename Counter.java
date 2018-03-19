

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Marcos Moreira
 */
public class Counter extends Thread {
    Text count;
    Stage primaryStage;
    Scene gameScene;
    Text points;
    Text lastWord;
    Text timeLeft;
    TextField wordsEntry;
    String player;
    pointsDB ranking;
    
    int WINDOW_WIDTH = 450;
    int WINDOW_HEIGHT = 220;
    
    Counter(Text count,Stage stage,Scene scene,Text points,Text timeLeft, TextField wordsEntry,Text lastWord,String player){
        super();
        this.count = count;
        primaryStage = stage;
        gameScene = scene;
        this.points = points;
        this.timeLeft = timeLeft;
        this.wordsEntry = wordsEntry;
        this.lastWord = lastWord;
        this.player = player;
    }
    
    public void run()
    {
    	
    	//Load the databases
    	Thread loader = new Thread( new Runnable() {
    	    @Override
    	    public void run() {
    	    	try {
					ranking = new pointsDB(player);
				} catch (IOException e) {
					e.printStackTrace();
				}
    	    }
    	});
    	loader.start();
    	
    	//Do the counter
        for(int i = 3; i>=1;i--)
        {
            count.setText("0"+Integer.toString(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Counter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Confirm that databases were loaded
        try {
			loader.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        gameCounter game = new gameCounter(primaryStage,points,timeLeft,wordsEntry,lastWord,count,ranking);
        game.start();
        
        Platform.runLater(new Runnable() {
    @Override
    public void run() {
    	gameScene.getStylesheets().add(Counter.class.getResource("sheet.css").toExternalForm());
        primaryStage.setScene(gameScene);
    }
});
    }
    
  //Creates the game scene
    
        
    
}
