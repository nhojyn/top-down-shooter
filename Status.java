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

	Player player;
	private Text healthTxt;
	private Text scoreTxt;
	private Text ammoTxt;
	Pane overlay;
	private HealthBar healthBar;
	public Status(Pane ol, Player p){
		player=p;
		overlay = ol;
		healthTxt = new Text("Health: " + player.getHealth());
		scoreTxt = new Text("Score: " + player.getScore());
		ammoTxt = new Text("Ammo: " + player.getGun().getAmmo());
		getChildren().addAll(healthTxt, scoreTxt,ammoTxt);
		
	}

	public void setHealthTxt(int h){healthTxt.setText("Health: " + h);}
	public void setScoreTxt(int s){scoreTxt.setText("Score: " + s);}
	public void setAmmoTxt(int a){ammoTxt.setText("Ammo: " + player.getGun().getAmmo());}

	public void reset(){
		healthTxt.setText("Health: " + player.getHealth());
		scoreTxt.setText("Score: " + player.getScore());
		ammoTxt.setText("Ammo: " + player.getGun().getAmmo());
	}
}
