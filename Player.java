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

public class Player extends Pane{
	Circle body;
	Circle eye;
	ArrayList<Gun> guns;
	double angle;
	Pane Playground;
	boolean invincible;
	int health, score, currentGun, heal, healthcap;
	Rectangle hitbox;
	boolean delayOffBlink;

	Player(Pane p, ArrayList<Bullet> b){
		delayOffBlink=true;
		health = 10;
		healthcap = 10;
		invincible=false;
		score = 0;
		currentGun = 0;
		heal = 2;
		Playground=p;
		body = new Circle(30);
		body.setFill(Color.BLACK);
		eye = new Circle(5);
		eye.setFill(Color.BLUE);
		eye.setLayoutY(getLayoutY()+body.getRadius()-eye.getRadius());
		hitbox = new Rectangle(body.getCenterX()-body.getRadius()/1.414, body.getCenterY()-body.getRadius()/1.414, 1.414*body.getRadius(), 1.414*body.getRadius());
		getChildren().add(hitbox);
		hitbox.setFill(Color.TRANSPARENT);

		getChildren().addAll(body,eye);

		//order matters, which ever gun is next is unlocked(pistol and shotgun are always unlocked)
		guns = new ArrayList<Gun>();
		guns.add(new Pistol(b,body.getRadius()));
		guns.add(new ShotGun(b,body.getRadius()));
		guns.add(new Sniper(b,body.getRadius()));
		guns.add(new Bazooka(b,body.getRadius()));
		guns.add(new MachineGun(b,body.getRadius()));
		guns.add(new FlameThrower(b,body.getRadius()));

		getChildren().add(guns.get(currentGun));
		unlockNextGun();
		unlockNextGun();
	}

	public int getHealth(){
		return health;
	}
	public void setHealth(int i){
		health = i;
	}
	public int getScore(){
		return score;
	}
	public double getAngle(){
		return angle;
	}
	public Gun getGun(){
		return guns.get(currentGun);
	}
	public int getCurrentGun(){
		return currentGun;
	}
	public double getLocX(){
		return getLayoutX();
	}
	public double getLocY(){
		return getLayoutY();
	}
	public Circle getBody(){
		return body;
	}
	public ArrayList<Gun> getGuns(){
		return guns;
	}

	public void move(int x, int y){
		setLayoutX(getLayoutX()+x);
		setLayoutY(getLayoutY()+y);
		guns.get(currentGun).setLocX(getLayoutX()+x+50);
		guns.get(currentGun).setLocY(getLayoutY()+y);
	}

	public void rotate(double x, double y){

		angle=Math.atan2(getLayoutY()-y,getLayoutX()-x);
		double gunAngle=Math.atan2(getLayoutY()-y-Math.sin(angle+Math.PI/2)*50,getLayoutX()-x-Math.cos(angle+Math.PI/2)*50)-angle-Math.PI/2;

		getTransforms().clear();

		getTransforms().add(new Rotate(Math.toDegrees(angle)+90));
		guns.get(currentGun).rotate(gunAngle);
	}

	public void reset(){
		guns.get(currentGun).clearBullets();
		for(int i=0;i<guns.size();i++){
			guns.get(i).resetAmmoToCap();
		}
		health=10;
		score=0;
		lockAllGuns();
	}

	public boolean collideWithMob(Mob m){
		if(m.isBoss() && !invincible){
			return collideWithBoss((Boss)m);
		}
		if(!invincible){
			Bounds b1 = m.getFront().localToScene(m.getFront().getBoundsInLocal());
			Bounds b2 = body.localToScene(body.getBoundsInLocal());
			double distance = Math.sqrt(Math.pow(b1.getMinX()-getLayoutX(),2)+Math.pow(b1.getMinY()-getLayoutY(),2));
			//Bug: HITS THE PLAYER BEFORE ACTUALLY TOUCHING
			if(distance<body.getRadius()){
				health--;
				return true;
			}
		}
		return false;
	}

	private boolean collideWithBoss(Boss b){
		Bounds b1 = b.getFront().localToScene(b.getFront().getBoundsInLocal());
		Bounds b2 = hitbox.localToScene(hitbox.getBoundsInLocal());
		if(b1.intersects(b2)){
			health--;
			b.knockbackPlayer(this);
			return true;
		}
		return false;
	}

	//TODO: make this cleaner
	public boolean collideWithProjectile(MobProjectile p){
		if(!invincible){
			Bounds b1 = p.localToScene(p.getBoundsInLocal());
			Bounds b2 = body.localToScene(body.getBoundsInLocal());
			Bounds b3 = hitbox.localToScene(hitbox.getBoundsInLocal());
			if(b1.intersects(b3)){
				health--;
				return true;
			}
		}
		return false;
	}

	public void grantInvincibility(double sec){
		invincible=true;
		//the immunity buff sets it to red only if its black
		if(body.getFill() == Color.BLACK){
			body.setFill(Color.RED);
		}
		Timeline delay = new Timeline(new KeyFrame(Duration.millis(sec*1000),ae -> invincibilityOff()));
		delay.play();
	}

	private void invincibilityOff(){
		invincible=false;
		body.setFill(Color.BLACK);
	}

	public void addToScore(int i){
		score += i;
	}

	//removes the current gun and then changes to the new one
	public void changeGun(int i,UserInterface ui){
		if(guns.get(i).getUnlocked()){
			getChildren().remove(guns.get(currentGun));
			getChildren().add(guns.get(i));
			currentGun = i;
			ui.getGunDisplay().setGunText(guns.get(i).getName());
			ui.getAmmoCounter().setAmmoNum(guns.get(i).getAmmo());
		}
	}

	public void pause(){
		for(int i=0;i<guns.size();i++){
			guns.get(i).pause();
		}
	}

	public void play(){
		for(int i=0;i<guns.size();i++){
			guns.get(i).play();
		}
	}



	/* Knockback method but animated (same thing as mob but it stops at the edge of the playground)
	 *
	*/
	public void animatedKnockback(double x, double y, double m){
		Timeline delay = new Timeline(new KeyFrame(Duration.millis(5),ae -> knockback(x, y, m/30)));
		delay.setCycleCount(30);
		delay.play();
	}

	private void knockback(double x, double y, double m){
		double px = x - getLayoutX();
		double py = y - getLayoutY();

		double distance = Math.pow(((px*px) + (py*py)),0.5);

		move((int)(-px*m/distance),(int)(-py*m/distance));

	}

	public void blink(double mouseX, double mouseY){
		if(delayOffBlink){
			double sideA = mouseX - getLayoutX();
			double sideB = mouseY - getLayoutY();
			double sideC = Math.sqrt(Math.pow(sideA,2) + Math.pow(sideB,2));
			int numImg=5;
			int distance=150;
			double finalXdis = sideA/sideC*distance;
			double finalYdis = sideB/sideC*distance;
			//Next four if statements are so they dont blink off the screen
			if(finalXdis+getLayoutX()+body.getRadius()>Playground.getWidth()){
				finalXdis=Playground.getWidth()-getLayoutX()-body.getRadius();
			}
			if(finalXdis+getLayoutX()-body.getRadius()<0){
				finalXdis=-getLayoutX()+body.getRadius();
			}
			if(finalYdis+getLayoutY()+body.getRadius()>Playground.getHeight()){
				finalYdis=Playground.getHeight()-getLayoutY()-body.getRadius();
			}
			if(finalYdis+getLayoutY()-body.getRadius()<0){
				finalYdis=-getLayoutY()+body.getRadius();
			}
			ArrayList<Circle> afterImages = new ArrayList<Circle>();
			for(int i=1;i<numImg+1;i++){
				Circle afterImage = new Circle(body.getRadius());
				afterImages.add(afterImage);
				afterImage.setFill(body.getFill());
				afterImage.setLayoutX(getLayoutX()+finalXdis*i/(numImg+1));
				afterImage.setLayoutY(getLayoutY()+finalYdis*i/(numImg+1));
				afterImage.setOpacity(.6-.4/i);
				Playground.getChildren().add(afterImage);
			}
			Timeline delete = new Timeline(new KeyFrame(Duration.millis(70),ae -> deleteAfterImages(afterImages)));
			delete.setCycleCount(numImg);
			delete.play();
			setLayoutX(getLayoutX()+finalXdis);
			setLayoutY(getLayoutY()+finalYdis);
			delayOffBlink=false;
			Timeline delay = new Timeline(new KeyFrame(Duration.seconds(1.5),ae -> delayOffBlink=true));
			delay.play();
		}
	}

	private void deleteAfterImages(ArrayList<Circle> AfImg){
		Playground.getChildren().remove(AfImg.get(0));
		AfImg.remove(0);
	}

	public void heal(){
		health += heal;
	}
	
	//loops through the guns and checks for the next locked gun and unlocks it 
	public void unlockNextGun(){
		boolean unlockedAGun = false;
		int i = 2;
		while(!unlockedAGun){
			if(guns.get(i).getUnlocked() == false){
				guns.get(i).unlock();
				unlockedAGun = true;	
				//stops the loop if there is no more guns to unlock 
			}else if(i > guns.size()){
				break;
			}
			i++;
		}
	}
	
 	private void lockAllGuns(){
 		for(int i = 0; i < guns.size(); i++){
 			guns.get(i).lock();
 		}
 	}
 	
 	public void buffAllDamage(){
 		for(int i = 0; i < guns.size(); i++){
 			
 		}
 	}
}
