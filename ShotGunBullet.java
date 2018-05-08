import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class ShotGunBullet extends Bullet{
	double spray=3;
	
	public ShotGunBullet(double xS, double yS){
		super();
		setxSpeed(xS+spray*Math.random()-spray/2);
		setySpeed(yS+spray*Math.random()-spray/2);
		damage = 3;
		speedMultiplier = .4;
		shell = new Circle(5);
		shell.setFill(Color.BLACK);
		getChildren().add(shell);
		setPrefSize(10,10);
		shell.setCenterX(10);
		shell.setCenterY(10);
		
		explosionTimer = null;
	}

	

}