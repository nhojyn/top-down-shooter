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

public class Options extends VBox{
	private Text title;
	private Button Main;
	double width=300;
	double height=300;
	Button test;
	
	public Options(Button main){
		Main=main;
		setPrefWidth(width);
		setPrefHeight(height);
		String cssLayout = "-fx-border-color: pink;\n" +
                   "-fx-border-insets: 5 ;\n" +
                   "-fx-border-width: 5;\n" +
                   "-fx-border-style: solid;\n"+
                   "-fx-background-color: black;";
		
		setStyle(cssLayout);
		setSpacing(15);
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		title = new Text("Options");
		title.setFont(f1);
		title.setFill(Color.WHITE);
		
		test = new Button("test");
		getChildren().addAll(title,main);
	}
	
	public double getPresetWidth(){
		return width;
	}
	
	public double getPresetHeight(){
		return height;
	}
	
	public void resetButtons(){	
		getChildren().removeAll(Main,test);	
		getChildren().addAll(Main,test);	
	}
}