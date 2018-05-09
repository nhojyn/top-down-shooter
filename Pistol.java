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

public class Pistol extends Gun{
	//NOTE: THIS IS THE PRIMARY GUN AND SHOULD HAVE INFINITE AMMO
	Pistol(ArrayList<Bullet> b){
		super(b);
		ammo=9999;
		fireRate=.3;
		tip=new Rectangle(10,10);
		tip.setFill(Color.RED);
		body=new Rectangle(10,60);
		body.setFill(Color.BLUE);
		getChildren().addAll(body,tip);
		tip.setLayoutY(body.getHeight());
	}
	public void shoot(Player p, double mouseLocX, double mouseLocY){
		Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
		if(mouseLocX!=boundsInScene.getMinX()&&mouseLocY!=boundsInScene.getMinY()){
		//	double radius = 10;
		//	double sideA = mouseLocX - boundsInScene.getMinX()/2;
		//	double sideB = mouseLocY - boundsInScene.getMinY()-radius/2;
		
			double sideA = mouseLocX - boundsInScene.getMinX()-9/2;
			double sideB = mouseLocY - boundsInScene.getMinY()-9/2;
			double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

			PistolBullet b = new PistolBullet(sideA/sideC*5,sideB/sideC*5);
			//b.setLocation(x-b.getRadius(),y-b.getRadius());
			b.setLocation(boundsInScene.getMinX()-b.getRadius()/2,boundsInScene.getMinY()-b.getRadius()/2);
			bullets.add(b);
	
			TopDownShooter.playground.getChildren().add(b);

		}
		
	}
	
}