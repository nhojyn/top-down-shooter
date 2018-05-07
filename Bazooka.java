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

public class Bazooka extends Gun{
	Timeline explosionTimer;
	Bazooka(ArrayList<Bullet> b){
		super(b);
		fireRate=0.5;
		tip=new Rectangle(20,20);
		tip.setFill(Color.RED);
		body=new Rectangle(20,60);
		body.setFill(Color.RED);
		getChildren().addAll(body,tip);
		tip.setLayoutY(body.getHeight());
	}
	public void shoot(Player p, double mouseLocX, double mouseLocY){
		Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
		if(mouseLocX!=boundsInScene.getMinX()&&mouseLocY!=boundsInScene.getMinY()){
		//	double radius = 10;
		//	double sideA = mouseLocX - boundsInScene.getMinX()/2;
		//	double sideB = mouseLocY - boundsInScene.getMinY()-radius/2;
		
			double sideA = mouseLocX - boundsInScene.getMinX();
			double sideB = mouseLocY - boundsInScene.getMinY();
			double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

			BazookaBullet b = new BazookaBullet(sideA/sideC*7,sideB/sideC*7);

			//b.setLocation(x-b.getRadius(),y-b.getRadius());
			b.setLocation(boundsInScene.getMinX()-b.getRadius()/2,boundsInScene.getMinY()-b.getRadius()/2);
			bullets.add(b);
	
			TopDownShooter.playground.getChildren().add(b);

		}
		
	}
	//before removing the bullet, make the bullet explode
	public void removeBullet(Bullet b){//NOTE: removeBullet() will be called whenever colliding
		//conditional is used to make sure the timeline isnt remade and the size of the bullet should be at default 15
		if(b.getRadius() == 15){
			//change to 16 to fix bug where it collides more than once running explode()
			b.setRadius(16);
			explosionTimer = new Timeline(new KeyFrame(Duration.millis(20),ae -> explode(b)));
			explosionTimer.setCycleCount(Animation.INDEFINITE);
			explosionTimer.play();
		}
	}

	//stop bullet and increases the size of bullet until its 65 or greater then remove
	public void explode(Bullet b){
		b.setxSpeed(0);
		b.setySpeed(0);
		if(b.getRadius() < 65){
			b.setRadius(b.getRadius()+2);
		}else{
			TopDownShooter.playground.getChildren().remove(b);
			bullets.remove(b);
		}
		
	}
	
}