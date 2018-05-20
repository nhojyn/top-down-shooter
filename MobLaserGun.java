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

public class MobLaserGun extends MobGun{

	double laserSpeed = 4;

	MobLaserGun(ArrayList<MobProjectile> b){
		super();
		bullets = b;
		tip = new Rectangle(4,4);
		getChildren().add(tip);
	}
// public LaserBulet(double x, double y, double width, double height,
// Color color, double xv, double yv){
	public void shoot(Player p){
		LaserBullet west = new LaserBullet(0,0,50,10,Color.RED,-laserSpeed,0);
		LaserBullet east = new LaserBullet(0,0,50,10,Color.RED,laserSpeed,0);
		LaserBullet north = new LaserBullet(0,0,10,50,Color.RED,0,-laserSpeed);
		LaserBullet south = new LaserBullet(0,0,10,50,Color.RED,0,laserSpeed);
		bullets.add(west);
		bullets.add(east);
		bullets.add(north);
		bullets.add(south);
		west.setLocation(450,495);
		east.setLocation(500,495);
		north.setLocation(495,450);
		south.setLocation(495,500);
		TopDownShooter.playground.getChildren().addAll(west,east,north,south);
	}

	public void move(){
		for(int i=bullets.size()-1;i>=2;i--){
			if(bullets.get(i).getLayoutX()<0||bullets.get(i).getLayoutY()<0||bullets.get(i).getLayoutX()>TopDownShooter.playground.getWidth()||bullets.get(i).getLayoutY()>TopDownShooter.playground.getHeight()){
				TopDownShooter.playground.getChildren().remove(bullets.get(i));
				bullets.remove(i);
			}
		}
		for(int i=bullets.size()-1;i>=2;i--){
			bullets.get(i).move();
		}
	}

}
