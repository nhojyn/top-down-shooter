import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class ShotGunBullet extends Bullet{

	public ShotGunBullet(double xS, double yS){
		super(xS+2*Math.random()-1, yS+2*Math.random()-1);
		speedMultiplier = 1;
		radius = 5;
		shell = new Circle(radius);
		shell.setFill(Color.BLACK);
		getChildren().add(shell);
		setPrefSize(10,10);
		shell.setCenterX(10);
		shell.setCenterY(10);
	}

	

}