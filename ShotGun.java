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

public class ShotGun extends Gun{

	//TODO: NEEDS TO CHECK FOR AMMO LEFT 
	ShotGun(ArrayList<Bullet> b,double radius){
		super(b,radius);
		ammo = 30;
		fireRate=.7;
		tip=new Rectangle(15,15);
		tip.setFill(Color.TRANSPARENT);
		body=new Rectangle(15,60);
		body.setFill(Color.GREEN);
		getChildren().addAll(body,tip);
		tip.setLayoutY(body.getHeight());
	}
	
	public void shoot(Player p, double mouseLocX, double mouseLocY){
		if(ammo > 0){
			Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
			if(mouseLocX!=boundsInScene.getMinX()&&mouseLocY!=boundsInScene.getMinY()){
			//	double radius = 10;
			//	double sideA = mouseLocX - boundsInScene.getMinX()-radius/2;
			//	double sideB = mouseLocY - boundsInScene.getMinY()-radius/2;
			
				//radius of bullet: 
				double sideA = mouseLocX - boundsInScene.getMinX()-5/2;
				double sideB = mouseLocY - boundsInScene.getMinY()-5/2;
				double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));
				//spawns 5 bullets and then stops the loop
				for(int i=0;i<5;i++){
					ShotGunBullet b = new ShotGunBullet(sideA/sideC*5,sideB/sideC*5);
					
					b.setLocation(boundsInScene.getMinX()-b.getRadius()/2,boundsInScene.getMinY()-b.getRadius()/2);
				
					bullets.add(b);
					TopDownShooter.playground.getChildren().addAll(b);
				}
				ammo--;
			}
		}
		
	}
	
}
