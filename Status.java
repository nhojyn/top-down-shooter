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


public class Status extends VBox{

	private Text healthTxt;
	private Text scoreTxt;
	Pane playground;
	private HealthBar healthBar;
	
	public Status(Pane pg, Player p){
		setLayoutX(150);
		playground = pg;
		healthTxt = new Text("Health: " + p.getHealth());
		scoreTxt = new Text("Score: " + p.getScore());
		getChildren().addAll(healthTxt, scoreTxt);
		
	}

	public void setHealthTxt(int h){healthTxt.setText("Health: " + h);}
	public void setScoreTxt(int s){scoreTxt.setText("Score: " + s);}

	
}
