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
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.transform.Rotate;
import javafx.geometry.Point2D;

public abstract class Gun extends Pane{
	double locX; 
	double locY;
	Rectangle body;
	Rectangle tip;
	ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	int ammo;
	double fireRate;
	Timeline move;
	
	Gun(){
		move = new Timeline(new KeyFrame(Duration.millis(5), ae -> move()));
		move.setCycleCount(Animation.INDEFINITE);
		move.play();
		setLayoutX(-50);
	}
	
	public void setLocX(double x1){
		locX = x1;
	}
	public void setLocY(double y1){
		locY = y1;
	}
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	public double getFireRate(){
		return fireRate;
	}
	
	public void rotate(double newangle){
		
		getTransforms().clear();
		Rotate rotation = new Rotate(Math.toDegrees(newangle)+90);
		getTransforms().add(rotation);
		
	}
	
	public void move(){
		for(int i=bullets.size()-1;i>=0;i--){
			if(bullets.get(i).getLayoutX()<0||bullets.get(i).getLayoutY()<0||bullets.get(i).getLayoutX()>TopDownShooter.playground.getWidth()||bullets.get(i).getLayoutY()>TopDownShooter.playground.getHeight()){
				TopDownShooter.playground.getChildren().remove(bullets.get(i));
				bullets.remove(i);
			}
		}
		for(int i=bullets.size()-1;i>=0;i--){
			bullets.get(i).move();
		}
	}
	
	public void clearBullets(){
		for(int i=0;i<bullets.size();i++){
			TopDownShooter.playground.getChildren().remove(bullets.get(i));
		}
		bullets.clear();
	}
	
	public void removeBullet(Bullet b){
		TopDownShooter.playground.getChildren().remove(b);
		bullets.remove(b);
	}
	
	public abstract void shoot(Player p, double mouseLocX, double mouseLocY);
	
	public void pause(){
		move.pause();
	}
	
	public void play(){
		move.play();
	}
}