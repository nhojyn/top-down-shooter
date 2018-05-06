import java.util.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;

public class ZombieMob extends Mob implements TrueBounds{
	
	public ZombieMob(){
		speedModifier = 1;
		body = new Rectangle(0,0,50,50);
		body.setFill(Color.BLUE);
		getChildren().add(body);
		knockback = true;
		health = 2;
		attacks = false;
		front = new Rectangle(((Rectangle)body).getWidth()/2-2.5,((Rectangle)body).getHeight()-5, 5,5);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		middle.setFill(Color.RED);
		getChildren().addAll(front,middle);
	}
	
	public double getBodyHeight(){
		return ((Rectangle)body).getHeight();
	}
	public double getBodyWidth(){
		return ((Rectangle)body).getWidth();
	}
	
}