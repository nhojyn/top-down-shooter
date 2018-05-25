import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.scene.image.*;

/* Currently, LaserMachine is a mob that will follow the player, but only in one direction at a time (i.e only up/down
 * OR left/right). It will periodically change colors and fire lasers. It should not collide with any
 * other mobs, , nor with other laser machines.
 *
 * Known issues: -- does not collide with player (probably has to do with "front" not working); (maybe just turn this
 *  			 into a feature?)
 *
 *				 -- if the LaserMachine is killed, windows will throw a IndexOutOfBoundsException when collisionChecker
 *				 tries to check for its lasers. However, nothing breaks because the object is already deleted, so its
 *				 not really an issue? It just makes the console really ugly.
*/
public class LaserMachine extends Mob implements TrueBounds{

	private final int MAX_COOLDOWN = 125;
	int cooldown = MAX_COOLDOWN; //it will shoot every 20*MAX_COOLDOWN ms (so for 125, it shoots every 2.5 seconds)
	ImageView i1;
	ImageView i2;
	
	public LaserMachine(){
		body = new Circle(0,0,20);
		body.setFill(Color.GREEN);
		getChildren().add(body);
		knockback = false;
		health = 10;
		attacks = true;
		speedModifier = 1;
		points= 10;
		shooting = false;
		middle = new Rectangle(0,0,1,1);
		front = new Rectangle(0,0,1,1);
		middle.setFill(Color.RED);
		getChildren().add(middle);
		getChildren().add(front);
		projectiles.add(new Laser(-5,-1000,10,2000,Color.RED));
		projectiles.add(new Laser(-1000,-5,2000,10,Color.RED));
		try{
			Image image1 = new Image("laser_0.png");
			i1 = new ImageView(image1);
			i1.setFitWidth(getBodyWidth()*2);
			i1.setFitHeight(getBodyHeight()*2);
			i1.setLayoutX(-getBodyWidth());
			i1.setLayoutY(-getBodyHeight());
			getChildren().add(i1);
			
			Image image2 = new Image("laser_1.png");
			i2 = new ImageView(image2);
			i2.setFitWidth(getBodyWidth()*2);
			i2.setFitHeight(getBodyHeight()*2);
			i2.setLayoutX(-getBodyWidth());
			i2.setLayoutY(-getBodyHeight());
		}
		catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}	
	}
	
	public double getBodyHeight(){
		return ((Circle)body).getRadius();
	}
	public double getBodyWidth(){
		return ((Circle)body).getRadius();
	}
	public double getRadius(){
		return ((Circle)body).getRadius();
	}

	public void chase(Player p){
		double px = p.getLocX()- getLayoutX();
		double py = p.getLocY()- getLayoutY();
		double distance = Math.pow(((px*px) + (py*py)),0.5);

		if(Math.abs(px) > Math.abs(py)){
			if(py>0){
				move(0,speedModifier);
			}else{
				move(0,-speedModifier);
			}
		}else if (Math.abs(px) <= Math.abs(py)){
			if(px>0){
				move(speedModifier,0);
			}else{
				move(-speedModifier,0);
			}
		}
		cooldown--;
		if(cooldown <= 0){
			attack();
		}
	}

	public void attack(){
		speedModifier = 0;
		//body.setFill(Color.RED);
		if(cooldown == 0){
			getChildren().add(i2);
		}
		if(cooldown == -25){
			shooting = true;
			getChildren().add(projectiles.get(0));
			getChildren().add(projectiles.get(1));
		}
		if(cooldown <= -50){
			shooting = false;
			getChildren().removeAll(projectiles.get(0),projectiles.get(1));
			//body.setFill(Color.GREEN);
			getChildren().remove(i2);
			speedModifier = 1;
			cooldown = MAX_COOLDOWN;
		}
	}

}
