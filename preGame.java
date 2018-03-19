

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;




/**
 *
 * @author Marcos Moreira
 */
public class preGame extends Application {
    
    int playerBestScore = 20;
    int bestScore = 50;
    int WINDOW_WIDTH = 450;
    int WINDOW_HEIGHT = 220;
    
    Text count;
    Stage primaryStage;
    Scene gameScene;
    Text points;
    Text lastWord;
    Text timeLeft;
    TextField wordsEntry;
    
    String player;
    
    
    @Override
    public void start(Stage primaryStage) {
        String playerName;
        
        primaryStage.setTitle("SpeedTyper");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 40, 40, 40));

        Text sceneTitle = new Text();
        sceneTitle.setText("SpeedTyper");
        sceneTitle.setId("gameTitle");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Text firstName = new Text("Your name:");
        firstName.setId("nameLabel");
        grid.add(firstName, 1, 1);
        

        TextField firstTextField = new TextField();
        firstTextField.setOnAction(e -> {
        	 player = firstTextField.getCharacters().toString();
             GridPane grid1 = new GridPane();
             grid1.setAlignment(Pos.CENTER);
             Text count = new Text();
             count.setId("count");
             grid1.add(count, 0, 0, 2, 1);
             Scene scene1 = new Scene(grid1, 450, 220);
             scene1.getStylesheets().add(preGame.class.getResource("sheet.css").toExternalForm());
             primaryStage.setScene(scene1); 
             new Counter(count,primaryStage,game(),points,timeLeft,wordsEntry,lastWord,player).start();

            });
        grid.add(firstTextField, 2, 1);

   
        grid.setGridLinesVisible(false);

        Button btn = new Button("Start Game");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BASELINE_CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4,2,1);
        
     
        

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 player = firstTextField.getCharacters().toString();
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
        });
       
        //Create first scene
        Scene scene = new Scene(grid, 450, 220);
        scene.getStylesheets().add(preGame.class.getResource("sheet.css").toExternalForm());
        primaryStage.setScene(scene); 
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("SpeedTyper.png"))); 
        primaryStage.show();
    }
    
    private Scene game()
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
