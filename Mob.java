import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;

import javafx.scene.transform.Rotate;

//eventually this class should be abstract
public class Mob extends Pane{
	
	//fields
	int health; //actually takes health+1 hits to kills (e.g. a mob with 2 health takes 3 bullets to kill)
	Rectangle body;
	Rectangle front; //indicates where front of mob is
	Rectangle middle;
	double speedModifier = 1.0; //total distance that it walks per cycle
	boolean knockback;

	
	public Mob(){
		body = new Rectangle(0,0,50,50);
		body.setFill(Color.BLUE);
		getChildren().add(body);
		knockback = true;
		health = 2;
		front = new Rectangle(body.getWidth()/2-2.5, body.getHeight()-5, 5,5);
		middle = new Rectangle(body.getWidth()/2, body.getHeight()/2, 5,5);
		middle.setFill(Color.RED);
		getChildren().addAll(front,middle);
		
	}
	
	//setters and getters
	public double getLocX(){
		return getLayoutX();
	}
	public double getLocY(){
		return getLayoutY();
	}
	public int getHealth(){
		return health;
	}
	public Rectangle getBody(){
		return body;
	}
	public double getAbsoluteMiddleX(){
		Bounds boundsInScene = middle.localToScene(middle.getBoundsInLocal());
		return boundsInScene.getMinX();
	}
	public double getAbsoluteMiddleY(){
		Bounds boundsInScene = middle.localToScene(middle.getBoundsInLocal());
		return boundsInScene.getMinY();
	}

	//methods
	public void chase(Player p){
		double px = p.getLocX()- getLayoutX();
		double py = p.getLocY()- getLayoutY();
		
		double distance = Math.pow(((px*px) + (py*py)),0.5);
		
		move(px*speedModifier/distance,py*speedModifier/distance);
		
		rotate(p.getLocX(),p.getLocY());
	}
	
	public void move(double x, double y){
		setLayoutX(getLayoutX()+x);
		setLayoutY(getLayoutY()+y);
	}
	
	/* Knocks back mob at angle or at vector from (x,y) to mob with magnitude m
	 *
	*/
	public void knockback(double angle, double m){
		
	}
	
	public void knockback(double x, double y, double m){
		double px = x - getLayoutX();
		double py = y - getLayoutY();
		
		double distance = Math.pow(((px*px) + (py*py)),0.5);
		
		move(-px*m/distance,-py*m/distance);
		
	}
	
	/* Knockback method but animated (similar to ones in Swarm class)
	 *
	*/
	public void animatedKnockback(double x, double y, double m){
		Timeline delay = new Timeline(new KeyFrame(Duration.millis(5),ae -> knockback(x, y, m/20)));
		delay.setCycleCount(20);
		delay.play();	
	}
	
	private void rotate(double x, double y){
		double angle = Math.atan2(getLayoutY()-y,getLayoutX()-x);
		getTransforms().clear();
		getTransforms().add(new Rotate(Math.toDegrees(angle)+90));
	}
	
	public void collideWithBullet(Player p){ //NOTE: now takes a player instead of gun
		Gun g = p.getGun();
		ArrayList<Bullet> b = g.getBullets();
		if(b.size() > 0){
			for(int i = b.size()-1; i >= 0; i--){
				//creates bounds to check if the bullet (b2) intersects body (b1)-- body is a rectangle right now
				Bounds b1 = body.localToScene(body.getBoundsInLocal());
				Bounds b2 = g.getBullets().get(i).localToScene(g.getBullets().get(i).getBoundsInLocal());
				//when collides removeBullet(Bullet b) removes bullet from playground and arraylist bullets and then subtracts health
				if(b1.intersects(b2)){
					
					//adding knockback to the mob, temporarily sets speed to 0 so they dont walk during animation
					double temp = speedModifier;
					speedModifier = 0;
					animatedKnockback(p.getLocX(), p.getLocY(), 20);
					speedModifier = temp;
					
					g.removeBullet(b.get(i));
					health--;
				}
			}
		}
	}

}
