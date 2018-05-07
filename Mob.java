import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;

public abstract class Mob extends Pane implements TrueBounds{
	
	//fields
	int health; //actually takes health+1 hits to kills (e.g. a mob with 2 health takes 3 bullets to kill)
	int points;
	Shape body;
	Rectangle front; //indicates where front of mob is
	Rectangle middle;
	double speedModifier; //total distance that it walks per cycle
	boolean knockback;
	boolean attacks; //shows whether or not the mob shoots and projectiles
	boolean shooting; //shows whether or not the mob is CURRENTLY shooting
	
	//NOTE: all projectiles should start well outside the player pane (-10000,-10000) just in case
	ArrayList<MobProjectile> projectiles = new ArrayList<MobProjectile>();
	
	public Mob(){
		
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
	public int getPoints(){
		return points;
	}
	public Shape getBody(){
		return body;
	}
	public Rectangle getFront(){
		return front;
	}
	public boolean getAttacks(){
		return attacks;
	}
	public boolean getKnockback(){
		return knockback;
	}
	public boolean getShooting(){
		return shooting;
	}
	public double getAbsoluteMiddleX(){
		Bounds boundsInScene = middle.localToScene(middle.getBoundsInLocal());
		return boundsInScene.getMinX();
	}
	public double getAbsoluteMiddleY(){
		Bounds boundsInScene = middle.localToScene(middle.getBoundsInLocal());
		return boundsInScene.getMinY();
	}
	public ArrayList<MobProjectile> getProjectiles(){
		return projectiles;
	}

	//methods
	public void chase(Player p){
		double px = p.getLocX() - getLayoutX() - front.getLayoutX() - body.getLayoutX();
		double py = p.getLocY() - getLayoutY() - front.getLayoutY() - body.getLayoutY();
		
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
			//Change: moved b1 outside of loop to make loop more efficient
			Bounds b1 = body.localToScene(body.getBoundsInLocal());
			for(int i = b.size()-1; i >= 0; i--){
				//creates bounds to check if the bullet (b2) intersects body (b1)-- body is a rectangle right now
				Bounds b2 = g.getBullets().get(i).localToScene(g.getBullets().get(i).getBoundsInLocal());
				//when collides removeBullet(Bullet b) removes bullet from playground and arraylist bullets and then subtracts health
				if(b1.intersects(b2)){
					//NOTE: now each bullet has their own damage 
					health = health - b.get(i).getDamage();
					g.removeBullet(b.get(i));
					//adding knockback to the mob, temporarily sets speed to 0 so they dont walk during animation
					if(knockback){
						double temp = speedModifier;
						speedModifier = 0;
						animatedKnockback(p.getLocX(), p.getLocY(), 20); //last number should eventually be p.getGun().getKnockback()
						speedModifier = temp;
					}
				}
			}
		}
	}

	public double getBodyHeight(){
		return 0;
	}
	
	public double getBodyWidth(){
		return 0;
	}
	
	/* For mobs that will shoot stuff, attack is called, but only if the boolean attacks is true
	 *
	*/
	public void attack(){
		
	}
}
