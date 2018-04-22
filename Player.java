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

public class Player extends Pane{
	Circle body;
	Gun gun;
	double angle;
	
	Player(){
		//setPrefSize(100,100);
		
		body = new Circle(50);
		body.setFill(Color.BLACK);
		//body.setCenterX(getPrefWidth()/2);
		//System.out.println(getPrefWidth() + " " + getPrefHeight());
		//body.setCenterY(getPrefHeight()/2);
		//setStyle("-fx-background-color: blue;");
		
		getChildren().add(body);
		gun = new Gun();
		gun.setLayoutX(-50);
		getChildren().add(gun);
	}
	
	public double getAngle(){
		return angle;
	}
	public Gun getGun(){
		return gun;
	}
	public double getLocX(){
		return getLayoutX();
	}
	public double getLocY(){
		return getLayoutY();
	}
	
	public void move(int x, int y){
		setLayoutX(getLayoutX()+x);
		setLayoutY(getLayoutY()+y);
		gun.setLocX(getLayoutX()+x+50);
		gun.setLocY(getLayoutY()+y);
	}

	public void rotate(double x, double y){
			
		angle=Math.atan2(getLayoutY()-y,getLayoutX()-x);
		
		getTransforms().clear();
		
		getTransforms().add(new Rotate(Math.toDegrees(angle)+90));
		//gun.rotate(x,y);
	}
	

}