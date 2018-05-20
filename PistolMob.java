import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;

public class PistolMob extends Mob implements TrueBounds{

	//fields
	MobGun gun;
	private final int MAX_COOLDOWN = (int)((Math.random())*100) + 100 ; //100 to 199
	int cooldown = MAX_COOLDOWN; //it will shoot every 20*MAX_COOLDOWN ms (so for 125, it shoots every 2.5 seconds)
	Pane pg;
	int moveCase = (int)(Math.random()*8);
	
	public PistolMob(Pane main){
		pg = main;
		projectiles = new ArrayList<MobProjectile>();
		body = new Circle(0,0,30);
		body.setFill(Color.RED);
		getChildren().add(body);
		knockback = true;
		health = 15;
		attacks = true;
		shooting = true;
		speedModifier = 1;
		points= 20;
		middle = new Rectangle(0,0,1,1);
		front = new Rectangle(0,0,1,1);
		middle.setFill(Color.RED);
		getChildren().add(middle);
		getChildren().add(front);
		gun = new MobGun(projectiles, getBodyWidth());
		getChildren().add(gun);
	}

	public void chase(Player p){
		if(projectiles.size() == 0){
			shooting = false;
		}
		else{
			shooting = true;
		}
		rotate(p.getLocX(), p.getLocY());
		gun.move();

		double px = p.getLocX()- getLayoutX();
		double py = p.getLocY()- getLayoutY();
		double distance = Math.pow(((px*px) + (py*py)),0.5);


	// movement: mob tries to stay near edges, and randomly moves around
	// if statements adjust the mob to stay in screen
		if(getLayoutX() < 0){
			moveCase = (int)(Math.random()*8);
			while (moveCase == 3 || moveCase == 5 || moveCase == 7){
				moveCase = (int)(Math.random()*8);
			}
		}
		if(getLayoutX() > pg.getPrefWidth()){
			moveCase = (int)(Math.random()*8);
			while (moveCase == 2 || moveCase == 4 || moveCase == 6){
				moveCase = (int)(Math.random()*8);
			}
		}
		if(getLayoutY() < 0){
			moveCase = (int)(Math.random()*8);
			while (moveCase == 1 || moveCase == 6 || moveCase == 7){
				moveCase = (int)(Math.random()*8);
			}
		}
		if(getLayoutX() > pg.getPrefHeight()){
			moveCase = (int)(Math.random()*8);
			while (moveCase == 0 || moveCase == 4 || moveCase == 5){
				moveCase = (int)(Math.random()*8);
			}
		}

		switch(moveCase){ //one case for each of the 8 cardinal directions
			case 0: move(0,speedModifier); //south
							break;
			case 1: move(0,-speedModifier); //north
							break;
			case 2: move(speedModifier,0); //east
							break;
			case 3: move(-speedModifier,0); //west
							break;
			case 4: move(speedModifier,speedModifier); // SE
							break;
			case 5: move(-speedModifier,speedModifier); //SW
							break;
			case 6: move(speedModifier,-speedModifier); //NE
							break;
			case 7: move(-speedModifier,-speedModifier); //NW
							break;

		}
		cooldown--;
		if(cooldown <= 0){
			attack(p);
			moveCase = (int)(Math.random()*8);
		}

	}

	private void attack(Player p){
		gun.shoot(p);
		cooldown = MAX_COOLDOWN;
	}

	private void rotate(double x, double y){
		double angle=Math.atan2(getLayoutY()-y,getLayoutX()-x);
		double gunAngle=Math.atan2(getLayoutY()-y-Math.sin(angle+Math.PI/2)*50,getLayoutX()-x-Math.cos(angle+Math.PI/2)*50)-angle-Math.PI/2;
		getTransforms().clear();
		getTransforms().add(new Rotate(Math.toDegrees(angle)+90));
		gun.rotate(gunAngle);
	}

	public double getBodyHeight(){
		return ((Circle)body).getRadius();
	}

	public double getBodyWidth(){
		return ((Circle)body).getRadius();
	}
}
