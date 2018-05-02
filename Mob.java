import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

import javafx.scene.transform.Rotate;

//eventually this class should be abstract
public class Mob extends Pane{
	
	//fields
	int health;
	Rectangle body;
	Rectangle front; //indicates where front of mob is
	double speedModifier = 1.0; //total distance that it walks per cycle
	int health;
	boolean knockback;

	
	public Mob(){
		body = new Rectangle(0,0,50,50);
		body.setFill(Color.BLUE);
		getChildren().add(body);
		knockback = true;
		
		health = 50;
		
		front = new Rectangle(body.getWidth()/2-2.5, body.getHeight()-5, 5,5);
		getChildren().add(front);
		health = 0;
		
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
	

	public Rectangle getBody(){return body;}

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
	
	private void rotate(double x, double y){
		double angle = Math.atan2(getLayoutY()-y,getLayoutX()-x);
		getTransforms().clear();
		getTransforms().add(new Rotate(Math.toDegrees(angle)+90));
	}
	
	public void collideWithBullet(Gun g){
		ArrayList<Bullet> b = g.getBullets();
		if(b.size() > 0){
			for(int i = b.size()-1; i >= 0; i--){
				//creates bounds to check if the bullet (b2) intersects body (b1)-- body is a rectangle right now
				Bounds b1 = body.localToScene(body.getBoundsInLocal());
				Bounds b2 = g.getBullets().get(i).localToScene(g.getBullets().get(i).getBoundsInLocal());
				//when collides removeBullet(Bullet b) removes bullet from playground and arraylist bullets and then subtracts health
				if(b1.intersects(b2)){
					g.removeBullet(b.get(i));
					health--;
				}
			}
		}
	}

}
