import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class SniperBullet extends Bullet{
	
	public SniperBullet(double xS, double yS){
		super(xS, yS);
		speedMultiplier = .6;
		damage = 25;
		shell = new Circle(7);
		shell.setFill(Color.BROWN);
		getChildren().add(shell);
		setPrefSize(10,10);
		shell.setCenterX(10);
		shell.setCenterY(10);
		
		explosionTimer = null;
	}

	

}