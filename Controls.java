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
import javafx.scene.control.Label;

public class Controls{
	boolean delayOff;
	boolean goNorth, goSouth, goEast, goWest, leftClick, rightClick;
	double mouseY, mouseX;
	Scene mainGame;
	Player player;
	Pane playground;
	boolean isPaused;
	UserInterface ui;
	
	public Controls(Scene mg, Player p, Pane pg,UserInterface userint){
		delayOff=true;
		isPaused=false;
		
		mainGame = mg;
		
		ui=userint;
		
		player = p;
		
		playground = pg;
					
		mg.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
				//keys 1, 2, 3, 4, 5 changes weapons
				//TODO: FINISH MORE GUNS TO ASSIGN FOR DIGIT CLICKED
				if(!isPaused){
					if(event.getCode()==KeyCode.DIGIT1||event.getCode()==KeyCode.DIGIT2||event.getCode()==KeyCode.DIGIT3||event.getCode()==KeyCode.DIGIT4||event.getCode()==KeyCode.DIGIT5||event.getCode()==KeyCode.DIGIT6||event.getCode()==KeyCode.DIGIT7||event.getCode()==KeyCode.DIGIT8){
						player.changeGun(Integer.parseInt(event.getText())-1);
						ui.getAmmoCounter().setAmmoNum(player.getGun().getAmmo());
					}
				}
            }
        });
		
		mg.setOnKeyReleased(new EventHandler<KeyEvent>() {
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
				if(!isPaused){
					int dx = 0, dy = 0;
					
					if(player.getLayoutY()>0){
						if (goNorth) dy -= 2;
					}
					if(player.getLayoutY()<playground.getWidth()){
						if (goSouth) dy += 2;
					}
					if(player.getLayoutX()<playground.getHeight()){
						if (goEast)  dx += 2;
					}
					if(player.getLayoutX()>0){
						if (goWest)  dx -= 2;
					}

					player.move(dx, dy);
					player.rotate(mouseX,mouseY);
					//left mousekey is held down shoot, else stop
					if(leftClick){
						shoot();
					}
				}
            }
        };
        timer.start();
		
		mg.setOnMouseMoved(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent event) {
			mouseX = event.getX();
			mouseY = event.getY();
		  }
		});		
		
		//when holding down and moving mouse 
		mg.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent event) {
			mouseY = event.getY();
			mouseX = event.getX();
		  }
		});
		
		//left click to shoot a bullet
		mg.setOnMousePressed(new EventHandler<MouseEvent>() {
			
		  @Override public void handle(MouseEvent event){
			if(event.isPrimaryButtonDown()){
				leftClick = true;
			}
			if(event.isSecondaryButtonDown()){
				rightClick = true;
			}
		  }
		});		
		
		mg.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
		  @Override public void handle(MouseEvent event){
			if(!event.isPrimaryButtonDown()){
				leftClick = false;

			}
			if(!event.isSecondaryButtonDown()){
				rightClick = false;
			}
		  }
		});
		
		
	}
		
	public void updateMouse(double x, double y){
		mouseX = x;
		mouseY = y;
	}
	
	private void shoot(){
		if(delayOff){
			player.getGun().shoot(player, mouseX, mouseY);
			ui.getAmmoCounter().setAmmoNum(player.getGun().getAmmo());
			delayOff=false;
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(player.getGun().getFireRate()*1000),ae -> delayOff=true));
			delay.play();
		}
	}
	
	private void blink(){
		if(delayOff){
			player.setLayoutX(player.getLayoutX());
			delayOff=false;
			Timeline delay = new Timeline(new KeyFrame(Duration.millis(player.getGun().getFireRate()*1000),ae -> delayOff=true));
			delay.play();
		}
	}
	
	public void pause(){
		isPaused=true;
	}
	
	public void play(){
		isPaused=false;
	}

}