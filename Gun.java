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
	Pane Playground;
	ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	Gun(Pane p){
		Playground=p;
		setWidth(5);
		setHeight(40);
		setFill(Color.BLUE);
		Timeline move = new Timeline(new KeyFrame(Duration.millis(5), ae -> move()));
		move.setCycleCount(Animation.INDEFINITE);
		move.play();
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
	
	public void shoot(Player p, double mouseLocX, double mouseLocY){

		double rad = Math.toRadians(p.getAngle());
		double x = p.getLayoutX();
		double y = p.getLayoutY();
		
		double sideA = mouseLocX - x;
		double sideB = mouseLocY - y;
		double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

		Bullet b = new Bullet(sideA/sideC*7,sideB/sideC*7);

		b.setLocation(x,y);
		bullets.add(b);
	
		Playground.getChildren().add(b);
		
	}
	
	public void move(){
		for(int i=bullets.size()-1;i>=0;i--){
			//NEED TO FIX THIS TO CALCULATE THE ACTUAL WIDTH AND HEIGHT OF THE SCREEN
			//IN ORDER TO FIX THE SCENE NEEDS TO BE SET BEFORE move() IS CALLED
			if(bullets.get(i).getLayoutX()<0||bullets.get(i).getLayoutY()<0||bullets.get(i).getLayoutX()>1080||bullets.get(i).getLayoutY()>1920){
				Playground.getChildren().remove(bullets.get(i));
				bullets.remove(i);
			}
		}
		for(int i=bullets.size()-1;i>=0;i--){
			bullets.get(i).move();
		}
	}

}