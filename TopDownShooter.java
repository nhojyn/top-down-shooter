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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class TopDownShooter{
	Player player;
	BorderPane screen;
	public static Pane playground;
	Scene mainGame;
	Stage stage;
	boolean goNorth, goSouth, goEast, goWest, leftClick, rightClick;
	double mouseY, mouseX;
	Timeline mouseTimer, shootTimer, collision;
	Button mainMenu;
	boolean delayOff;
	Swarm mobs;
	
	TopDownShooter(Stage s,Button main){
		delayOff=true;
		stage=s;
		mainMenu=main;
		
		screen=new BorderPane();
		
		playground = new Pane();
		playground.setPrefWidth(1000);
		playground.setPrefHeight(1000);
		screen.setCenter(playground);
		
		player = new Player(playground);
		playground.getChildren().addAll(player,mainMenu);
		
		mainGame = new Scene(screen);
		
		shootTimer= new Timeline(new KeyFrame(Duration.millis(100), ae -> player.getGun().shoot(player,mouseX,mouseY)));			
		shootTimer.setCycleCount(Animation.INDEFINITE);
					
		//checks if mob collides with bullet
		collision = new Timeline(new KeyFrame(Duration.millis(10), ae -> bulletToMobChecker()));			
		collision.setCycleCount(Animation.INDEFINITE);
		collision.play();
		
		mainGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:    goNorth = true;
						break;
                    case S:  goSouth = true; 
						break;
                    case A:  goWest  = true; 
						break;
                    case D: goEast  = true; 
						break;
                }
            }
        });
		
		mainGame.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:    goNorth = false; 
						break;
                    case S:  goSouth = false; 
						break;
                    case A:  goWest  = false; 
						break;
                    case D: goEast  = false; 
						break;
                }
            }
        });
		
		AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 2;
                if (goSouth) dy += 2;
                if (goEast)  dx += 2;
                if (goWest)  dx -= 2;

                player.move(dx, dy);
				player.rotate(mouseX,mouseY);
				//left mousekey is held down shoot, else stop
				if(leftClick){
					shoot();
				}
            }
        };
        timer.start();
		
		mainGame.setOnMouseMoved(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent event) {
			mouseX = event.getX();
			mouseY = event.getY();
		  }
		});		
		
		//when holding down and moving mouse 
		mainGame.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent event) {
			mouseY = event.getY();
			mouseX = event.getX();
		  }
		});
		
		//left click to shoot a bullet
		mainGame.setOnMousePressed(new EventHandler<MouseEvent>() {
			
		  @Override public void handle(MouseEvent event){
			if(event.isPrimaryButtonDown()){
				leftClick = true;
			}
			if(event.isSecondaryButtonDown()){
				rightClick = true;
			}
		  }
		});		
		
		mainGame.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
		  @Override public void handle(MouseEvent event){
			if(!event.isPrimaryButtonDown()){
				leftClick = false;

			}
			if(!event.isSecondaryButtonDown()){
				rightClick = false;
			}
		  }
		});
		
		//create swarm and test it
		mobs = new Swarm(4);
		Timeline mobMovement = new Timeline(new KeyFrame(Duration.millis(20),ae -> mobs.swarmPlayer(player)));
		mobMovement.setCycleCount(Animation.INDEFINITE);
		Button spawnBtn = new Button();
		playground.getChildren().add(spawnBtn);
		spawnBtn.setText("Spawn round");
		spawnBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnSwarm(playground);				
				mobMovement.play();
			}
		});
		
		//test knockback
		Button knockBtn = new Button();
		playground.getChildren().add(knockBtn);
		knockBtn.setLayoutX(100);
		knockBtn.setText("knockback");
		knockBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobMovement.pause();
				mobs.knockbackMobsAnimated(player, 300);
				mobMovement.play();
			}
		});
	}
	
	public void play(){
		stage.setScene( mainGame );
		player.setLayoutX(playground.getWidth()/2);
		player.setLayoutY(playground.getHeight()/2);
	}
	
	private void shoot(){
		if(delayOff){
			player.getGun().shoot(player,mouseX,mouseY);
			delayOff=false;
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(1),ae -> delayOff=true));
			delay.play();
		}
	}
	
	public void updateMouse(double x, double y){
		mouseX = x;
		mouseY = y;
	}
	public void bulletToMobChecker(){
		//if there are mobs, check for each mob if they collided with bullet 
		if(mobs.getSwarm().size() > 0){
			for(int i = 0; i < mobs.getSwarm().size(); i++){
				//colldeWithBullet is in Mob class
				mobs.getSwarm(i).collideWithBullet(player.getGun());
				if(mobs.getSwarm(i).getHealth() < 0){
					playground.getChildren().remove(mobs.getSwarm(i));
					mobs.getSwarm().remove(mobs.getSwarm(i));
				}
			}
		}
	}
}
