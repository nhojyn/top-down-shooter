import java.util.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.*; 
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScoreCounter extends Pane{
	int score;
	Text scoreNum;
	
	public ScoreCounter(int i){
		score=i;
		
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),30);
		scoreNum= new Text("Score "+Integer.toString(score));
		scoreNum.setFill(Color.BLACK);
		scoreNum.setFont(f1);
		scoreNum.setLayoutX(30);
		scoreNum.setLayoutY(43);
		
		getChildren().add(scoreNum);
	}
	
	public void setScoreNum(int i){
		scoreNum.setText("Score "+Integer.toString(i));
	}
}