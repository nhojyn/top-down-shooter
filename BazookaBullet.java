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
		
		speedMultiplier = .1;
		shell = new Circle(15);
		shell.setFill(Color.ORANGE);
		getChildren().add(shell);
		setPrefSize(30,30);
		shell.setCenterX(15);
		shell.setCenterY(15);
		
		//change cyclecount to control how big the explosion is
		explosionTimer = new Timeline(new KeyFrame(Duration.millis(20),ae -> explode()));
		explosionTimer.setCycleCount(75);
	}
	

	//stop bullet and increases the size of bullet until its 65 or greater then remove
	public void explode(){
		setxSpeed(0);
		setySpeed(0);
		setRadius(getRadius()+2);
	}
}