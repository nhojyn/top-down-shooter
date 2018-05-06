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

public class UserInterface{
	Pane playground;
	Button Mainmenu;
	Player player;
	Status stats;
	private HealthBar healthBar;
	
	UserInterface(Pane pg, Button main,Player p){
		player=p;
		playground=pg;
		Mainmenu=main;
		stats = new Status(playground,player);
		healthBar = new HealthBar(player.getHealth());
		healthBar.setLayoutX(300);
		playground.getChildren().addAll(main,stats,healthBar);
		
	}
	
	public Status getStatus(){
		return stats;
	}
	
	public HealthBar getHealthBar(){
		return healthBar;
	}
}