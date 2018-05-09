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
		shell = new Circle(8);
		shell.setFill(Color.RED);
		getChildren().add(shell);
		setPrefSize(15,15);
		shell.setCenterX(15);
		shell.setCenterY(15);
		knockBack = 0;
		explosionTimer = null;
	}

	

}