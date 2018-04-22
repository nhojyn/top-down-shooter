import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;
public class HighScoreScreen extends Pane{
	VBox scoreList;
	HighScores hs;
	int location;
	
	HighScoreScreen(){
		scoreList = new VBox();
		
		hs = new HighScores();
		
		Text temp1 = new Text("Highscores");
		temp1.setFill(Color.YELLOW);
		temp1.setStyle("-fx-font: 17 arial;");
		
		scoreList.getChildren().add(temp1);
		
		for(int i = 0; i < hs.getHighScoresSize(); i++){
			scoreList.getChildren().add(new Text("#"+ (i+1) + "       " + hs.getHighScore(i)));
		}
		getChildren().add(scoreList);
	}
	
	
}