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
	Pane overlay;
	Button Mainmenu;
	Button pauseBtn;
	Player player;
	Status stats;
	private HealthBar healthBar;
	Options options;
	TopDownShooter game;
	
	UserInterface(Pane ol, Button main,TopDownShooter TDS ,Player p){
		player=p;
		overlay=ol;
		Mainmenu=main;
		game=TDS;
		
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		
		options = new Options(Mainmenu);
		options.setLayoutX(game.getWidth()/2-options.getPresetWidth()/2);
		options.setLayoutY(game.getHeight()/2-options.getPresetHeight()/2);
		
		pauseBtn = new Button();
		pauseBtn.setFont(f1);
		pauseBtn.setText("pause");
		pauseBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				if(pauseBtn.getText().equals("pause")){
					game.pause();
					overlay.getChildren().add(options);
					pauseBtn.setText("play");
				}else if(pauseBtn.getText().equals("play")){
					game.resume();
					overlay.getChildren().remove(options);
					pauseBtn.setText("pause");
				}else{
					System.out.println("error");
				}
				//stage.setScene(main);
			}
		});
		
		stats = new Status(overlay,player);
		stats.setLayoutX(150);
		healthBar = new HealthBar(player.getHealth());
		healthBar.setLayoutX(250);
		overlay.getChildren().addAll(pauseBtn,stats,healthBar);

		
	}
	
	public Status getStatus(){
		return stats;
	}
	
	public HealthBar getHealthBar(){
		return healthBar;
	}
	
	public void setScore(int score){
		stats.setScoreTxt(score);
	}
	
	public void reset(){
		game.resume();
		overlay.getChildren().remove(options);
		pauseBtn.setText("pause");
	}
}