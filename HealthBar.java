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

public class HealthBar extends Pane{
	int total;
	int HP;
	Rectangle Outline;
	Rectangle HPbar;
	int size;
	
	public HealthBar(int t){
		total=t;
		Outline=new Rectangle(500,40);
		Outline.setFill(Color.BLACK);
		Outline.setArcWidth(20);
		Outline.setArcHeight(20);
		HPbar=new Rectangle(Outline.getWidth()-10,Outline.getHeight()-10);
		size=(int)HPbar.getWidth();
		HPbar.setArcWidth(20);
		HPbar.setArcHeight(20);
		HPbar.setFill(Color.GREEN);
		HPbar.setLayoutX(Outline.getWidth()/2-HPbar.getWidth()/2);
		HPbar.setLayoutY(Outline.getHeight()/2-HPbar.getHeight()/2);
		getChildren().addAll(Outline, HPbar);
	}
	
	public void setHP(int i){
		HPbar.setWidth((size/total)*i);
	}
}