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

public class MobGun extends Pane{

	double locX;
	double locY;
	Rectangle body;
	Rectangle tip;
	ArrayList<MobProjectile> bullets;
	Timeline move;

	MobGun(){
		move = new Timeline(new KeyFrame(Duration.millis(5), ae -> move()));
		move.setCycleCount(Animation.INDEFINITE);
	}

	MobGun(ArrayList<MobProjectile> b,double length /* of the mob */){
		bullets = b;
		move = new Timeline(new KeyFrame(Duration.millis(5), ae -> move()));
		move.setCycleCount(Animation.INDEFINITE);

		setLayoutX(-length);

		tip=new Rectangle(8,8);
		tip.setFill(Color.BLUE);
		body=new Rectangle(8,25);
		body.setFill(Color.BLACK);
		getChildren().addAll(body,tip);
		tip.setLayoutY(body.getHeight());


	}

	public void setLocX(double x1){
		locX = x1;
	}
	public void setLocY(double y1){
		locY = y1;
	}
	public ArrayList<MobProjectile> getBullets(){
		return bullets;
	}
	public Rectangle getTip(){
		return tip;
	}

	public void rotate(double newangle){

		getTransforms().clear();
		Rotate rotation = new Rotate(Math.toDegrees(newangle)+90);
		getTransforms().add(rotation);

	}

	public void move(){
		for(int i=bullets.size()-1;i>=0;i--){
			if(bullets.get(i).getLayoutX()<0||bullets.get(i).getLayoutY()<0||bullets.get(i).getLayoutX()>TopDownShooter.playground.getWidth()||bullets.get(i).getLayoutY()>TopDownShooter.playground.getHeight()){
				TopDownShooter.playground.getChildren().remove(bullets.get(i));
				bullets.remove(i);
			}
		}
		for(int i=bullets.size()-1;i>=0;i--){
			bullets.get(i).move();
		}
	}

	public void clearBullets(){
		for(int i=0;i<bullets.size();i++){
			TopDownShooter.playground.getChildren().remove(bullets.get(i));
		}
		bullets.clear();
	}

	public void removeBullet(MobBullet b){
		bullets.remove(b);
	}

	public void shoot(Player p){
			Bounds boundsInScene = tip.localToScene(tip.getBoundsInLocal());
			double sideA = p.getLocX() - boundsInScene.getMinX()-9/2;
			double sideB = p.getLocY() - boundsInScene.getMinY()-9/2;
			double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));

			MobBullet b = new MobBullet(0,0,8,sideA/sideC*5,sideB/sideC*5,Color.RED);
			b.setLocation(boundsInScene.getMinX() - boundsInScene.getWidth()/2-b.getRadius()/2,boundsInScene.getMinY() - boundsInScene.getHeight()/2-b.getRadius()/2);
			bullets.add(b);
			TopDownShooter.playground.getChildren().add(b);
		}

	public void pause(){
		move.pause();
	}

	public void play(){
		move.play();
	}
}
