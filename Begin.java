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

public class Begin {
	
	 static int WINDOW_WIDTH = 450;
	 static int WINDOW_HEIGHT = 220;
	 
	 Text count;
	 Stage primaryStage;
	 Text points;
	 Text timeLeft;
	 TextField wordsEntry;
	 Text lastWord;
	 String player;
	    
	
	Begin(Text count,Stage stage,Text points,Text timeLeft, TextField wordsEntry,Text lastWord,String player){
		this.count = count;
		primaryStage = stage;
		this.points = points;
		this.timeLeft = timeLeft;
		this.wordsEntry = wordsEntry;
		this.lastWord = lastWord;
		this.player = player;
	}
	
	public  void restartGame()
	{
		GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        Text count = new Text();
        count.setId("count");
        grid1.add(count, 0, 0, 2, 1);
        Scene scene1 = new Scene(grid1, 450, 220);
        scene1.getStylesheets().add(preGame.class.getResource("sheet.css").toExternalForm());
        primaryStage.setScene(scene1); 
		new Counter(count,primaryStage,game(),points,timeLeft,wordsEntry,lastWord,player).start();
	}
	
	private  Scene game()
    {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 40, 40, 40));
        
        grid.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        grid.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setHalignment(HPos.RIGHT);
        leftCol.setHgrow(Priority.NEVER);

        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(leftCol, rightCol);

        points = new Text(); 
        points.setText("Score: 0");
        points.setId("gamePoints");
        grid.add(points, 0, 0);
        
        lastWord = new Text(); 
        lastWord.setText("Last word: ");
        lastWord.setId("lastWord");
        grid.add(lastWord, 1, 0); 
        
        wordsEntry = new TextField();
        grid.add(wordsEntry, 0, 1,3,1);
        
        timeLeft = new Text();
        timeLeft.setText("60");
        timeLeft.setId("gameTime");
        grid.add(timeLeft, 1, 2);
       
        return new Scene(grid, 450, 220);
    }
	
}
