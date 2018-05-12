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

public abstract class Bullet extends Pane{
	double xSpeed;
	double ySpeed;
	Circle shell;
	double speedMultiplier;
	int damage;
	Timeline explosionTimer;
	double distanceLimit, knockBack;
	
	public Bullet(double xS, double yS){
		xSpeed=xS;
		ySpeed=yS;
		distanceLimit=0;
		//default knockback
		knockBack = 20;
	}
	
	public Bullet(){
		
	}
	//getters and setters
	public double getxSpeed(){return xSpeed;}
	public double getySpeed(){return ySpeed;}
	public double getRadius(){return shell.getRadius();}
	public double getCenterX(){return shell.getCenterX();}
	public double getCenterY(){return shell.getCenterY();}
	public int getDamage(){return damage;}
	public Timeline getExplosionTimer(){return explosionTimer;}
	public double getKnockBack(){return knockBack;}
	public double getDistanceLimit(){return distanceLimit;}
	public void setxSpeed(double xS){
		xSpeed=xS;
	}
	
	public void setySpeed(double yS){
		ySpeed=yS;
	}
	public void setRadius(double r){
		shell.setRadius(r);
	}
	public void setLocation(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	public void move(){
		setLayoutX(getLayoutX() + xSpeed*speedMultiplier);
		setLayoutY(getLayoutY() + ySpeed*speedMultiplier);
	}

}