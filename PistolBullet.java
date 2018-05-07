import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class PistolBullet extends Bullet{
	
	public PistolBullet(double xS, double yS){
		super(xS, yS);
		speedMultiplier = 1;
		damage = 4;
		shell = new Circle(9);
		shell.setFill(Color.BLACK);
		getChildren().add(shell);
		setPrefSize(10,10);
		shell.setCenterX(10);
		shell.setCenterY(10);
	}

	

}