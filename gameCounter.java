

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Marcos Moreira
 */
public class gameCounter extends Thread {
	
	
    Stage primaryStage;
    Scene gameScene;
    Text points;
    Text lastWord;
    Text timeLeft;
    TextField wordsEntry;
    ConcurrentLinkedQueue<Double> newPoints;
    ConcurrentLinkedQueue<String> words;
    final int GAME_TIME = 5;
    Text count;
    pointsDB ranking;
    private static final int BEST_MARKS = 5;
     
    gameCounter(Stage stage,Text points, Text timeLeft,TextField wordsEntry,Text lastWord,Text count,pointsDB ranking){
        super();
        this.points = points;
        this.timeLeft = timeLeft;
        primaryStage = stage;
        this.wordsEntry = wordsEntry;
        newPoints = new ConcurrentLinkedQueue<Double>() ;
        words = new ConcurrentLinkedQueue<String>() ;
        this.lastWord = lastWord;
        this.count = count;
        this.ranking = ranking;
    }
    
    public void run()
    {
         double currentPoints = 0;
         String entryText = "";
         String aux;
         wordsProcessor checker = new wordsProcessor(newPoints,words);
         checker.start();
         
         //play music
         String uriString = new File("pirates.mp3").toURI().toString();
         MediaPlayer player = new MediaPlayer( new Media(uriString));
         player.play();
         
         //game's main loop
         long startTime = System.currentTimeMillis();
         while(System.currentTimeMillis()-startTime< GAME_TIME * 1000)
         {
             int timeS = GAME_TIME -  (int)(System.currentTimeMillis()-startTime)/1000;
             if(timeS <10)
            	 timeLeft.setText("0"+Integer.toString(timeS));
             else timeLeft.setText(Integer.toString(timeS));;
             try {
                 Thread.sleep(100);
             } catch (InterruptedException ex) {
                 Logger.getLogger(gameCounter.class.getName()).log(Level.SEVERE, null, ex);
             }
             aux = wordsEntry.getText();
             if(aux.length() != 0)
             {
             aux = aux.substring(entryText.length(),aux.length());
             if(aux.contains(" "))
             {
                words.add(aux.substring(0,aux.indexOf(" ")));
                entryText = entryText + aux.substring(0,aux.indexOf(" ")+1);
             }
             if(!newPoints.isEmpty()){
            	 double point = newPoints.poll();
            	 lastWord.setText("Last word: "+point);
                 currentPoints += point;
                 points.setText("Score: "+  currentPoints);
             }
             }
         }
         
         //stop the processor 
         checker.stopProcessor();
         try {
			checker.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
         
        //update the database
        ranking.setNewPoints(currentPoints);
        ranking.update();
         
        Platform.runLater(new Runnable() {
    @Override
    public void run() {
        Scene endGame = endGame(ranking);
        endGame.getStylesheets().add(gameCounter.class.getResource("sheet.css").toExternalForm());
        primaryStage.setScene(endGame);
    }
});
    }
    
     private Scene endGame(pointsDB ranking)
    {
         GridPane grid = new GridPane();
         grid.setHgap(10);
         grid.setVgap(10);
         grid.setPadding(new Insets(10, 10, 10, 10));
         grid.add(points, 0, 0);
         Button btnR = new Button("Restart Game");
         btnR.setId("restartButton");
         HBox hbBtn = new HBox(10);
         hbBtn.setAlignment(Pos.BASELINE_CENTER);
         hbBtn.getChildren().add(btnR);
         grid.add(hbBtn,2,0,2,1);
         btnR.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
            	 Begin newGame = new Begin(count,primaryStage,points,timeLeft,wordsEntry,lastWord,ranking.getPlayer());
                 newGame.restartGame();
             }
         });
         
         //print the ranking
         playerBest[] best = ranking.getBest();
         Text aux;
         for(int i = 0; i < BEST_MARKS;i++)
         {
        	 aux = new Text(best[i].name+"	"+best[i].points);
        	 aux.setId("rankingText");
        	 grid.add(aux,0, i+1, 3, 1);
         }
        
         
         //close the database
         try {
			ranking.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
         return new Scene(grid, 450, 220);
    }

}
