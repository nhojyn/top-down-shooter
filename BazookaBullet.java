import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
public class BazookaBullet extends Bullet{
	public BazookaBullet(double xS, double yS){
		super();
		setxSpeed(xS);
		setySpeed(yS);

		damage = 1;
		
		speedMultiplier = 0.1;
		shell = new Circle(15);
		shell.setFill(Color.ORANGE);
		getChildren().add(shell);
		setPrefSize(30,30);
		shell.setCenterX(15);
		shell.setCenterY(15);
		
	}
	

}