import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
/* Bouncer is a mob that bounces off the wall
*/	
public class Bouncer extends Mob{
	int size;
	int xSpeed;
	int ySpeed;
	int minSpeed;
	Pane playground;
	public Bouncer(Pane pg){
		super();
		size=25;
		playground = pg;
		xSpeed = 3;
		ySpeed = 3;
		minSpeed = 1;
		points=size;
		body= new Rectangle(size,size);
		body.setFill(Color.PURPLE);
		knockback = false;
		health = 15;
		attacks = false;
		shooting = false;
		front = new Rectangle(((Rectangle)body).getWidth()/2,((Rectangle)body).getHeight()/2,10,10);
		front.setFill(Color.YELLOW);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 0.01,0.01);
		middle.setFill(Color.BLUE);
		getChildren().addAll(body,front,middle);
	}

	public double getBodyHeight(){
		return ((Rectangle)body).getHeight();
	}
	public double getBodyWidth(){
		return ((Rectangle)body).getWidth();
	}
	
	public int getSize(){
		return size;
	}
	
	//doesn't actually chase the player but instead bounces kinda doesnt logically make sense to take it the player but it is needed to override the method
	public void chase(Player p){
		setLayoutX(getLayoutX()+xSpeed);
		setLayoutY(getLayoutY()+ySpeed);
		if(getLayoutY() > playground.getHeight() - size){
			ySpeed = -(int)(Math.random()*4)-minSpeed;
		}else if(getLayoutX() > playground.getWidth() - size){
			xSpeed = -(int)(Math.random()*4)-minSpeed;
		}else if(getLayoutX() < 0){
			xSpeed = (int)(Math.random()*4)+minSpeed;
		}else if(getLayoutY() < 0){
			ySpeed = (int)(Math.random()*4)+minSpeed;
		}
		
	}
	
}