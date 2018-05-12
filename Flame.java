import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class Flame extends Bullet{
	double spray=3.5;
	public Flame(double xS, double yS){
		super(xS, yS);
		speedMultiplier = .05;
		setxSpeed(xS+spray*Math.random()-spray/2);
		setySpeed(yS+spray*Math.random()-spray/2);
		damage = 1;
		shell = new Circle(5);
		shell.setFill(Color.RED);
		getChildren().add(shell);
		setPrefSize(15,15);
		shell.setCenterX(15);
		shell.setCenterY(15);
		knockBack = 0;
		explosionTimer = null;
		distanceLimit = 200;
	}

	public void move(){
		//uses distance formula to subtract off distance limit
		double oldX = getLayoutX();
		double oldY = getLayoutY();
		
		setLayoutX(getLayoutX() + xSpeed*speedMultiplier);
		setLayoutY(getLayoutY() + ySpeed*speedMultiplier);
		
		double newX = getLayoutX();
		double newY = getLayoutY();
		distanceLimit -= Math.sqrt(Math.pow(newX-oldX,2)+Math.pow(newY-oldY,2));
	
	}

}