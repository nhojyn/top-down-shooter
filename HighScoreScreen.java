import java.util.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.* ; 
import javafx.scene.input.* ;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.*;
public class HighScoreScreen extends VBox{
	HighScores highScores;
	Scene highScoreScreen, main;
	Font f = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),30);
	Stage stage;
	Text title;
	StackPane centeredTitle;
	Button mainBtn;
	
	HighScoreScreen(HighScores hs,Stage s, Scene m){
		stage = s;
		highScores = hs;
		main=m;
		highScoreScreen = new Scene(this, 1000, 1000);
		title = new Text("High Scores");
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),60);
		title.setFill(Color.WHITE);
		title.setFont(f1);
		centeredTitle = new StackPane();
		centeredTitle.getChildren().add(title);
		setStyle("-fx-background-color: black");
		setSpacing(15);
		mainBtn = new Button();
		mainBtn.setFont(f);
		mainBtn.setText("Main Menu");
		mainBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				stage.setScene(main);
			}
		});
	}
	
	public void updateHighScores(){
		getChildren().clear();
		getChildren().add(centeredTitle);
		StackPane centered;
		for(int i=0;i<highScores.getHighScoresSize();i++){
			centered=new StackPane();
			Text t = new Text();
			if(highScores.getHighScoreName(i).equals("")){
				t.setText(Integer.toString(highScores.getHighScoreNum(i)));
			}else{
				t.setText(highScores.getHighScoreName(i)+"        "+highScores.getHighScoreNum(i));
			}
			t.setFill(Color.WHITE);
			t.setFont(f);
			centered.getChildren().add(t);
			getChildren().add(centered);
		}
		centered=new StackPane();
		centered.getChildren().add(mainBtn);
		getChildren().add(centered);
	}
	
	public void setToScene(){
		stage.setScene(highScoreScreen);
	}
}