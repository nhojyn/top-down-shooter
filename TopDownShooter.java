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
	Pane playground;
	Scene mainGame;
	Stage stage;
	boolean goNorth, goSouth, goEast, goWest, leftClick, rightClick;
	double mouseY, mouseX;
	Timeline mouseTimer, shootTimer;
	TopDownShooter(Stage s){
		stage=s;
		
		screen=new BorderPane();
		
		playground = new Pane();
		playground.setPrefWidth(1000);
		playground.setPrefHeight(1000);
		screen.setCenter(playground);
		
	//	Gun gun = new Gun();
	//	playground.getChildren().add(gun);
	//	gun.setLayoutX(500);
	//	gun.setLayoutY(500);
		
		player = new Player(playground);
		playground.getChildren().add(player);
		
		mainGame = new Scene(screen);
		
		shootTimer= new Timeline(new KeyFrame(Duration.millis(100), ae -> player.getGun().shoot(player,mouseX,mouseY)));			
		shootTimer.setCycleCount(Animation.INDEFINITE);
				
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
				//left mousekey is held down shoot, else stop
				if(leftClick){
					shootTimer.play();
					player.rotate(mouseX,mouseY);
				}
				else shootTimer.stop();
				
            }
        };
        timer.start();
		
		mainGame.setOnMouseMoved(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent event) {
			mouseX = event.getX();
			mouseY = event.getY();
			
			player.rotate(mouseX,mouseY);
		  }
		});		
		
		//when holding down and moving mouse 
		mainGame.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent event) {
			mouseY = event.getY();
			mouseX = event.getX();
			
			player.rotate(mouseX,mouseY);
		  }
		});
		
		//left click to shoot a bullet
		mainGame.setOnMousePressed(new EventHandler<MouseEvent>() {
			
		  @Override public void handle(MouseEvent event){
			if(event.isPrimaryButtonDown()){
		//		mouseTimer = new Timeline(new KeyFrame(Duration.millis(150), ae -> updateMouse(mouseY, mouseX)));
			//	mouseTimer.setCycleCount(Animation.INDEFINITE);
				System.out.println("left pressed");
				player.getGun().shoot(player,mouseX,mouseY);
				leftClick = true;
				
			//	mouseTimer.play();

			}
			if(event.isSecondaryButtonDown()){
				System.out.println("right pressed");
				rightClick = true;
			}
		  }
		});		
		
		mainGame.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
		  @Override public void handle(MouseEvent event){
			if(!event.isPrimaryButtonDown()){
				System.out.println("left released");
				leftClick = false;

			}
			if(!event.isSecondaryButtonDown()){
				System.out.println("right released//");
				rightClick = false;
			}
		  }
		});
		
		//create swarm and test it
		Swarm mobs = new Swarm(4);
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
	}
	
	public void play(){
		stage.setScene( mainGame );
		player.setLayoutX(playground.getWidth()/2);
		player.setLayoutY(playground.getHeight()/2);
	}
	
	public void updateMouse(double x, double y){
		mouseX = x;
		mouseY = y;
	}
}
