import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;

public class Boss extends Mob implements TrueBounds{
	
	//fields
	boolean spawned;
	
	//constructor
	public Boss(){
		spawned = false;
		knockback = false;
		attacks = true;
		shooting = false;
		double speedModifier = 0;
		isBoss = true;
	}
	
	//methods
	public void spawn(Player p){
		spawned = true;
		knockbackPlayer(p);
	}
	
	public void knockbackPlayer(Player p){
		p.animatedKnockback(getLayoutX() + getBodyWidth()/2, getLayoutY() + getBodyHeight()/2, 100);
	}
	
	public double getBodyHeight(){
		return 0;
	}
	
	public double getBodyWidth(){
		return 0;
	}
}