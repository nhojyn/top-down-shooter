import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
/* Splitter is a mob that splits and gets faster,less hp, and smaller each split 
*/	
public class Splitter extends Mob implements TrueBounds{
	int size;
	
	public Splitter(int s){
		super();
		size=s;
		speedModifier=2.0/size;
		//The total amount of points by a splitter is currently size*(log2(size)+1)
		points=size;
		body= new Rectangle(20*s,20*s);
		body.setFill(Color.RED);
		knockback = true;
		health = s*3;
		attacks = false;
		shooting = false;
		front = new Rectangle(((Rectangle)body).getWidth()/2-2.5,((Rectangle)body).getHeight()-5, 5,5);
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
}