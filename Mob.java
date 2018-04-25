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
	
	public Mob(){
		body = new Rectangle(0,0,50,50);
		body.setFill(Color.BLUE);
		getChildren().add(body);
		
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
	
	//methods
	public void chase(Player p){
		double px = p.getLocX()- getLayoutX();
		double py = p.getLocY()- getLayoutY();
		
		double distance = Math.pow(((px*px) + (py*py)),0.5);
		
		move(px*speedModifier/distance,py*speedModifier/distance);
		
		rotate(p.getLocX(),p.getLocY());
	}
	
	private void move(double x, double y){
		setLayoutX(getLayoutX()+x);
		setLayoutY(getLayoutY()+y);
	}
	
	private void rotate(double x, double y){
		double angle = Math.atan2(getLayoutY()-y,getLayoutX()-x);
		getTransforms().clear();
		getTransforms().add(new Rotate(Math.toDegrees(angle)+90));
	}
}
