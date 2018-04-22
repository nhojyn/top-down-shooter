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

public class Gun extends Rectangle{
	double locX; 
	double locY;
	Gun(){
		setWidth(5);
		setHeight(40);
		setFill(Color.BLUE);
		

	}
	
	public void setLocX(double x1){
		locX = x1;
	}
	public void setLocY(double y1){
		locY = y1;
	}
	
	//currently does not work
	public void rotate(double x, double y){
		double angle;
	
		Bounds boundsInScreen = localToScreen(getBoundsInLocal());
		
		//angle=Math.atan2(getLayoutY()-y,getLayoutX()-x+getWidth()/2);
		
		angle=Math.atan2(boundsInScreen.getMinY()/2-y,boundsInScreen.getMinX()/2-x);
		
		System.out.println(boundsInScreen.getMinY());
		System.out.println(boundsInScreen.getMinX());
		
		getTransforms().clear();
		getTransforms().add(new Rotate(Math.toDegrees(angle)+90,getWidth()/2,0));
		
		
	}
	
	public void shoot(Pane playGround, Player p, double mouseLocX, double mouseLocY){
		Bullet b = new Bullet();

		double rad = Math.toRadians(p.getAngle());
		double x = p.getLayoutX();
		double y = p.getLayoutY();
		
		double sideA = mouseLocX - x;
		double sideB = mouseLocY - y;
		double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));
		
		
		Timeline translate = new Timeline(new KeyFrame(Duration.millis(5), ae -> b.move(sideA/sideC*7,sideB/sideC*7)));
		translate.setCycleCount(Animation.INDEFINITE);
		translate.play();

		//doesnt spawn where the tip of the gun is
	//	double x2 = (x * Math.cos(rad) - y * Math.sin(rad));
	//	double y2 = (x * Math.sin(rad) + y * Math.cos(rad));
		b.setLocation(x,y);
		
		//printout spawning spot
		//System.out.println("x: " + x2 + "\ny:  " + y2);
	
		playGround.getChildren().add(b);
		
	}

}