import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public abstract class Bullet extends Pane{
	double xSpeed;
	double ySpeed;
	Circle shell;
	double speedMultiplier;
	int damage;
	public Bullet(double xS, double yS){
		xSpeed=xS;
		ySpeed=yS;
	}
	
	public Bullet(){
		
	}
	//getters and setters
	public double getxSpeed(){return xSpeed;}
	public double getySpeed(){return ySpeed;}
	public double getRadius(){return shell.getRadius();}
	public double getCenterX(){return shell.getCenterX();}
	public double getCenterY(){return shell.getCenterY();}
	public int getDamage(){return damage;}
	public void setxSpeed(double xS){
		xSpeed=xS;
	}
	
	public void setySpeed(double yS){
		ySpeed=yS;
	}
	public void setRadius(double r){
		shell.setRadius(r);
	}
	public void setLocation(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	public void move(){
		setLayoutX(getLayoutX() + xSpeed*speedMultiplier);
		setLayoutY(getLayoutY() + ySpeed*speedMultiplier);
	}

}