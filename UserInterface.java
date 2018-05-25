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
	GunDisplay gunDisplay;
	Text gameOver;
	Rectangle fade;
	int fadeSpeed=50;
	
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
		healthBar.setLayoutX(game.getWidth()/2-healthBar.getBarWidth()/2-50);
		healthBar.setLayoutY(-5);
		
		ammoCounter = new AmmoCounter(player.getGun().getAmmo());
		ammoCounter.setLayoutX(game.getWidth()/2-healthBar.getBarWidth()/2-50);
		ammoCounter.setLayoutY(0);
		
		gunDisplay = new GunDisplay(player.getGun().getName());
		gunDisplay.setLayoutX(game.getWidth()/2-healthBar.getBarWidth()/2+65);
		gunDisplay.setLayoutY(55);
		
		scoreCounter = new ScoreCounter(0);
		scoreCounter.setLayoutX(game.getWidth()-180);
		
		overlay.getChildren().addAll(healthBar,ammoCounter,scoreCounter,gunDisplay,pauseBtn);
		
		fade = new Rectangle(TDS.getWidth(), TDS.getHeight());
		fade.setFill(Color.BLACK);
		fade.setOpacity(0);
		
	}
	/*
	public Status getStatus(){
		return stats;
	}
	*/
	public GunDisplay getGunDisplay(){
		return gunDisplay;
	}
	
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
		bossHealthBar.setLayoutY(900);
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
	
	public void gameOver(){
		Font f =Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"), 200);
		gameOver = new Text("Game Over");
		gameOver.setFill(Color.BLACK);
		gameOver.setFont(f);
		gameOver.setLayoutX(overlay.getWidth()/2-gameOver.getLayoutBounds().getWidth()/2);
		gameOver.setLayoutY(overlay.getHeight()/2);
		overlay.getChildren().add(gameOver);
		Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2),ae -> fadeOut()));
		delay.play();
	}
	
	public void gameWin(){
		Font f =Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"), 200);
		gameOver = new Text("You  Win");
		gameOver.setFill(Color.BLACK);
		gameOver.setFont(f);
		gameOver.setLayoutX(overlay.getWidth()/2-gameOver.getLayoutBounds().getWidth()/2);
		gameOver.setLayoutY(overlay.getHeight()/2);
		overlay.getChildren().add(gameOver);
		Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2),ae -> fadeOut()));
		delay.play();
	}
	
	public void fadeOut(){
		overlay.getChildren().add(fade);
		Timeline fadeAni = new Timeline(new KeyFrame(Duration.millis(fadeSpeed),ae -> animateFade()));
		fadeAni.setCycleCount(50);
		fadeAni.play();
		fadeAni.setOnFinished(e -> overlay.getChildren().remove(fade));
	}
	
	private void animateFade(){
		fade.setOpacity(fade.getOpacity()+0.02);
	}
	
	public double getFadeTime(){
		return fadeSpeed/1000.0*50.0+2;
	}
	
	public void reset(){
		game.resume();
		//stats.reset();
		healthBar.setHP(player.getHealth());
		scoreCounter.setScoreNum(player.getScore());
		overlay.getChildren().remove(options);
		if(gameOver != null){
			overlay.getChildren().remove(gameOver);
		}
		if(fade != null){
			fade.setOpacity(0);
		}
		pauseBtn.setText("pause");
		removeBossHP();
	}
}