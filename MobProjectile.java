import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

public abstract class MobProjectile extends Pane{
	double xSpeed;
	double ySpeed;
	Shape shell;
	
	public double getxSpeed(){return xSpeed;}
	public double getySpeed(){return ySpeed;}
	
	public void setLocation(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	public void move(){
		setLayoutX(getLayoutX() + xSpeed);
		setLayoutY(getLayoutY() + ySpeed);
	}
}
