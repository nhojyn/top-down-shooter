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

public class Gun extends Pane{
	double locX; 
	double locY;
	Rectangle body;
	Rectangle tip;
	ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	
	Gun(){
		tip=new Rectangle(10,10);
		tip.setFill(Color.RED);
		body=new Rectangle(10,60);
		body.setFill(Color.BLUE);
		getChildren().addAll(body,tip);
		tip.setLayoutY(body.getHeight());
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
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void rotate(double newangle){
		//double angle;
	
		//Bounds boundsInScreen = localToScreen(getBoundsInLocal());
		
		//angle=Math.atan2(boundsInScreen.getMaxY()/2-y,boundsInScreen.getMaxX()/2-x);
		
		//System.out.println("max Y-"+boundsInScreen.getMaxY());
		//System.out.println("min Y-"+boundsInScreen.getMinY());
		//System.out.println(boundsInScreen.getMaxX());
		
		getTransforms().clear();
		Rotate rotation = new Rotate(Math.toDegrees(newangle)+90);
		getTransforms().add(rotation);
		
		
	}
	
	public void shoot(Player p, double mouseLocX, double mouseLocY){
		Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
		if(mouseLocX!=boundsInScene.getMinX()&&mouseLocY!=boundsInScene.getMinY()){
			double radius = 10;
			double sideA = mouseLocX - boundsInScene.getMinX()-radius/2;
			double sideB = mouseLocY - boundsInScene.getMinY()-radius/2;
			double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

			Bullet b = new Bullet(sideA/sideC*7,sideB/sideC*7,radius);

			//b.setLocation(x-b.getRadius(),y-b.getRadius());
			b.setLocation(boundsInScene.getMinX()-radius/2,boundsInScene.getMinY()-radius/2);
			bullets.add(b);
		
			TopDownShooter.playground.getChildren().add(b);

		}
		
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

}