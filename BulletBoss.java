import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.scene.image.*;

/**
 * So this boss is reaaaaaallly hard right now but its meant to be a final boss
 * and they should be using the highest dps guns against it. The difficulty mainly
 * comes from the cooldown between attacks being really short (they are shorter/longer
 * depending on the attack).
*/

public class BulletBoss extends Boss implements TrueBounds{
	private final int MAX_COOLDOWN = 250;
	int cooldown = 5; //it will attack every 20*MAX_COOLDOWN ms (so for 125, it shoots every 5 seconds)
	Swarm mobs;
	Pane main;
	UserInterface ui;
	double thickness = 30;
	BulletBossGun gun;
	int attack2StartingWidth = 0;
	ImageView imgview;;

	public BulletBoss(Pane main, Swarm s, UserInterface ui){
		super(ui);
		gun = new BulletBossGun(projectiles);
		attacks = true;
		shooting = true;
		this.ui = ui;
		mobs = s;
		this.main = main;
		health = 500;
		ui.addBossHP(health);
		points = 1000;
		body = new Rectangle(0,0,100,100);
		body.setFill(Color.TRANSPARENT);
		getChildren().add(body);
		front = (Rectangle)body; //may cause issues
		middle = new Rectangle(getBodyWidth(),getBodyHeight(),1,1);
		getChildren().addAll(middle,gun);
		gun.setLayoutX(getBodyWidth());
		gun.setLayoutY(getBodyHeight());
		try{
			Image img = new Image("bulletboss.png");
			imgview = new ImageView(img);
			imgview.setFitWidth(getBodyWidth());
			imgview.setFitHeight(getBodyHeight());
			getChildren().add(imgview);
		}
		catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}

	}

	public void setCooldown(int a){
		cooldown = a;
	}
	public void chase(Player p){
		if(!spawned){
			spawn(p);
		}
		gun.move();
		cooldown--;
		if(cooldown <= 0){
			cooldown = attack(p);
		}
	}

	public int attack(Player p){
		boolean attacked = false;
		while(!attacked){
			int attackNum = (int)(Math.random()*4) + 1;
			switch (attackNum){
				case 1:  attacked = attack1(MAX_COOLDOWN, p);
						if(attacked){return MAX_COOLDOWN/2;}
						else{break;}
				case 2: attacked = attack2(10);
						if(attacked){return MAX_COOLDOWN/4;}
						else{break;}
				case 3: attacked = attack3();
						if(attacked){return MAX_COOLDOWN/2;}
						else{break;}
				case 4: attacked = attack4();
						if(attacked){return MAX_COOLDOWN/2;}
						else{break;}
			}
		}
		return 0;
	}

	//constant spray of bullets towards player
	private boolean attack1(int MAX_COOLDOWN, Player p){
		Timeline spray = new Timeline(new KeyFrame(Duration.millis(50),ae -> gun.shoot(p.getLocX() + Math.random()*40 - 20,p.getLocY() + Math.random()*40 - 20)));
		spray.setCycleCount(MAX_COOLDOWN/5);
		spray.play();
		return true;
	}

	//cirlce of bullets that player has to blink over. width is the width between bullets
	/**
	 * this wasn't really inteded but this attacks is really hard to dodge, might be
	 * because the hitboxes are janky, or because the attack itself is just hard to dodge.
	*/
	private boolean attack2(int width){
		for(int x = 0; x <= main.getPrefWidth()*0.5; x += width){
			gun.shoot(x,main.getPrefHeight()/2);
		}
		for(int y = 0; y <= main.getPrefHeight()*0.5; y+= width){
			gun.shoot(main.getPrefWidth()/2, y);
		}
		return true;
	}

	//alternates between circles of bullets that are dodgeable
	private boolean attack3(){
		Timeline spray = new Timeline(new KeyFrame(Duration.millis(20*MAX_COOLDOWN/10),ae -> alternateAttack2(150)));
		spray.setCycleCount(8);
		spray.play();
		return true;
	}

	//shoots bullets at set distances
	private boolean attack4(){
		Timeline spray = new Timeline(new KeyFrame(Duration.millis(20*MAX_COOLDOWN/120),ae -> attack2(150)));
		spray.setCycleCount(60);
		spray.play();
		return true;
	}

	public double getBodyHeight(){
		return ((Rectangle)(body)).getHeight();
	}

	public double getBodyWidth(){
		return ((Rectangle)(body)).getWidth();
	}

	private void alternateAttack2(int width){
		if(attack2StartingWidth == 0){
			attack2StartingWidth = width/2;
		}
		else{
			attack2StartingWidth = 0;
		}
		for(int x = -attack2StartingWidth; x <= main.getPrefWidth()*0.5; x += width){
			gun.shoot(x,main.getPrefHeight()/2);
		}
		for(int y = -attack2StartingWidth; y <= main.getPrefHeight()*0.5; y+= width){
			gun.shoot(main.getPrefWidth()/2, y);
		}

	}
}
