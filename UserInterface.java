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
	AmmoCounter ammoCounter;
	ScoreCounter scoreCounter;
	BossHealthBar bossHealthBar;
	
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
					options.resetButtons();
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
		int spacing = 15;
		pauseBtn.setLayoutX(spacing);
		pauseBtn.setLayoutY(spacing);
		
		//stats = new Status(overlay,player);
		//stats.setLayoutX(150);
		healthBar = new HealthBar(player.getHealth());
		healthBar.setLayoutX(game.getWidth()/2-healthBar.getBarWidth()/2-145);
		
		ammoCounter = new AmmoCounter(player.getGun().getAmmo());
		ammoCounter.setLayoutX(game.getWidth()-330);
		ammoCounter.setLayoutY(-42);
		
		scoreCounter = new ScoreCounter(0);
		scoreCounter.setLayoutX(game.getWidth()-180);
		
		overlay.getChildren().addAll(pauseBtn,healthBar,ammoCounter,scoreCounter);
		
	}
	/*
	public Status getStatus(){
		return stats;
	}
	*/
	public HealthBar getHealthBar(){
		return healthBar;
	}
	
	public AmmoCounter getAmmoCounter(){
		return ammoCounter;
	}
	
	public void setScore(int score){
		scoreCounter.setScoreNum(score);
	}
	
	public void addBossHP(int i){
		bossHealthBar = new BossHealthBar(i);
		bossHealthBar.setLayoutX(80);
		bossHealthBar.setLayoutY(960);
		overlay.getChildren().add(bossHealthBar);
	}
	
	public void removeBossHP(){
		if(bossHealthBar != null){
			overlay.getChildren().remove(bossHealthBar);
			bossHealthBar = null;
		}
	}
	
	public void setBossHP(int i){
		if(bossHealthBar != null){
			bossHealthBar.setHP(i);
		}
	}
	
	public void reset(){
		game.resume();
		//stats.reset();
		healthBar.setHP(player.getHealth());
		overlay.getChildren().remove(options);
		pauseBtn.setText("pause");
	}
}