import java.util.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.* ;
import javafx.scene.input.* ;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.transform.Rotate;
import javafx.geometry.Point2D;

public class BulletBossGun extends MobGun{

	double bulletSpeedMod = 12;
	double bulletSize = 6;

	public BulletBossGun(ArrayList<MobProjectile> b){
		super();
		bullets = b;
		tip = new Rectangle(4,4);
		getChildren().add(tip);
	}

	public void shoot(double x, double y){
		Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
		double sideA = x - boundsInScene.getMinX()-9/2;
		double sideB = y - boundsInScene.getMinY()-9/2;
		double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

		MobBullet b = new MobBullet(0,0,bulletSize,sideA/sideC*bulletSpeedMod,sideB/sideC*bulletSpeedMod,Color.RED);
		b.setLocation(boundsInScene.getMinX() - boundsInScene.getWidth()/2-b.getRadius()/2,boundsInScene.getMinY() - boundsInScene.getHeight()/2-b.getRadius()/2);
		bullets.add(b);
		TopDownShooter.playground.getChildren().add(b);
	}

	public void shoot(Player p){
			Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
			double sideA = p.getLocX() - boundsInScene.getMinX()-9/2;
			double sideB = p.getLocY() - boundsInScene.getMinY()-9/2;
			double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

			MobBullet b = new MobBullet(0,0,bulletSize,sideA/sideC*bulletSpeedMod,sideB/sideC*bulletSpeedMod,Color.RED);
			b.setLocation(boundsInScene.getMinX() - boundsInScene.getWidth()/2-b.getRadius()/2,boundsInScene.getMinY() - boundsInScene.getHeight()/2-b.getRadius()/2);
			bullets.add(b);
			TopDownShooter.playground.getChildren().add(b);
	}

}
