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
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.transform.Rotate;
import javafx.geometry.Point2D;

public class CutScene{
	Text text;
	Pane screen;
	StackPane centered;
	
	public CutScene(Pane pane){
		screen=pane;
		centered = new StackPane();
		centered.setPrefWidth(1000);
		centered.setPrefHeight(1000);
		screen.getChildren().add(centered);
	}
	
	public void newScene(String s, double duration){
		Font f = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),80);
		text = new Text(s);
		text.setFont(f);
		text.setFill(Color.BLACK);
		centered.getChildren().add(text);
		Timeline endCheck = new Timeline(new KeyFrame(Duration.seconds(duration), ae -> centered.getChildren().remove(text)));
		endCheck.play();
	}
}