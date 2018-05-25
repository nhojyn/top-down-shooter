import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.scene.image.*;

public class LaserBoss extends Boss implements TrueBounds{
	private final int MAX_COOLDOWN = 250;
	int cooldown = 20; //it will attack every 20*MAX_COOLDOWN ms (so for 125, it shoots every 5 seconds)
	Rectangle west,north,east,south,hitbox;
	Swarm mobs;
	Pane main;
	double laserSpeedMod = 1.5;
	double hLaserSpeed = laserSpeedMod;
	double vLaserSpeed = -laserSpeedMod;
	UserInterface ui;
	double thickness = 30;
	MobLaserGun gun;
	ImageView imgview;
	ImageView enragedimg;
	boolean enraged;

	public LaserBoss(Pane main, Swarm s, UserInterface ui){
		super(ui);
		gun = new MobLaserGun(projectiles);
		attacks = true;
		shooting = true;
		this.ui = ui;
		mobs = s;
		this.main = main;
		health = 300;
		ui.addBossHP(health);
		points = 200;
		body = new Circle(main.getPrefWidth()/2,main.getPrefHeight()/2,70);
		body.setFill(Color.TRANSPARENT);
		getChildren().add(body);
		enraged = false;
		
		try{
			Image img = new Image("laser_0.png");
			imgview = new ImageView(img);
			imgview.setFitWidth(getBodyWidth());
			imgview.setFitHeight(getBodyHeight());
			imgview.setLayoutX(main.getPrefWidth()/2 - getBodyWidth()/2);
			imgview.setLayoutY(main.getPrefHeight()/2- getBodyHeight()/2);
			getChildren().add(imgview);
			Image en = new Image("laser_1.png");
			enragedimg = new ImageView(en);
			enragedimg.setFitWidth(getBodyWidth());
			enragedimg.setFitHeight(getBodyHeight());
		}
		catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}	
		enraged = false;
		
		hitbox = new Rectangle(main.getPrefWidth()/2-60, main.getPrefHeight()/2-60, 120,120);
		front = new Rectangle(main.getPrefWidth()/2-100,main.getPrefHeight()/2-100,200,200);
		front.setFill(Color.TRANSPARENT);
		middle = new Rectangle(main.getPrefWidth()/2,main.getPrefHeight()/2,5,5);
		middle.setFill(Color.TRANSPARENT);
		west = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,main.getPrefHeight()/2-((Circle)body).getRadius()-thickness, thickness,2 * ((Circle)body).getRadius()+2*thickness);
		north = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,main.getPrefHeight()/2-((Circle)body).getRadius()-thickness, 2 * ((Circle)body).getRadius() + 2*thickness,thickness);
		east = new Rectangle(main.getPrefHeight()/2+((Circle)body).getRadius(),main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,thickness,2 * ((Circle)body).getRadius()+2*thickness);
		south = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,main.getPrefHeight()/2+((Circle)body).getRadius(), 2 * ((Circle)body).getRadius()+2*thickness,thickness);
		// west = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-11,main.getPrefHeight()/2-((Circle)body).getRadius(), 10,2 * ((Circle)body).getRadius());
		// north = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius(),main.getPrefHeight()/2-((Circle)body).getRadius()-11, 2 * ((Circle)body).getRadius(),10);
		// east = new Rectangle(main.getPrefHeight()/2+((Circle)body).getRadius()+1,main.getPrefHeight()/2-((Circle)body).getRadius(),10,2 * ((Circle)body).getRadius());
		// south = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius(),main.getPrefHeight()/2+((Circle)body).getRadius()+1, 2 * ((Circle)body).getRadius(),10);
		getChildren().addAll(front,middle,west,north,east,south,gun);
		gun.setLayoutX(500);
		gun.setLayoutY(500);
		projectiles.add(new Laser(0,0,1000,10,Color.RED)); //horizontal
		projectiles.add(new Laser(0,0,10,1000,Color.RED)); //vertical
		setPrefSize(1000,1000);
	}

	public void chase(Player p){
		if(!spawned){
			spawn(p);
			switchOpenFace();
		}

		moveTwoLasers();
		gun.move();
		cooldown--;
		if(cooldown <= 0){
			attack(p);
		}

	}

	public void attack(Player p){
		boolean attacked = false;
		while(!attacked){
			int attackNum = (int)(Math.random()*6) + 1;
			switch (attackNum){
				case 1: case 5: attacked = attack1();
						break;
				case 2: attacked = attack2();
						break;
				case 3: case 6: attacked = attack3(p);
						break;
				case 4: attacked = attack4();
						break;
			}
		}
		cooldown = MAX_COOLDOWN;
	}

	//reverses directions of lasers
	private boolean attack1(){
		getChildren().add(enragedimg);
		enragedimg.setLayoutX(main.getPrefWidth()/2 - getBodyWidth()/2);
		enragedimg.setLayoutY(main.getPrefHeight()/2- getBodyHeight()/2);
		Timeline toggle = new Timeline(new KeyFrame(Duration.millis(1000),ae -> switchLasers()));
		toggle.setCycleCount(1);
		toggle.play();
		return true;
	}

	/**
	 * spawns 2 laser machines (only if there aren't already 4).
	 * the laserMachines can go into the boss and not get hit but that's not a bug, the player should
	 * try to redirect the LaserMachine so that they can kill it
	*/
	private boolean attack2(){
		if(mobs.getSwarm().size() > 4){
			return false;
		}
		mobs.spawnLaserSwarm(main,2);
		return true;
	}

	//spawns a LaserBullet in all 4 cardinal directions
	private boolean attack3(Player p){
		gun.shoot(p);
		return true;
	}

	private boolean attack4(){
		return false;
	}
	private void moveTwoLasers(){
		MobProjectile h = projectiles.get(0);
		MobProjectile v = projectiles.get(1);
		h.setLayoutY(h.getLayoutY() + hLaserSpeed);
		v.setLayoutX(v.getLayoutX() + vLaserSpeed);
		if(h.getLayoutY()<0){
			hLaserSpeed = (Math.random()*2 + 1)*laserSpeedMod;
		}
		if(h.getLayoutY()>main.getPrefHeight()){
			hLaserSpeed = -(Math.random()*2 + 1)*laserSpeedMod;
		}
		if(v.getLayoutX()<0){
			vLaserSpeed = (Math.random()*2 + 1)*laserSpeedMod;
		}
		if(v.getLayoutX()>main.getPrefWidth()){
			vLaserSpeed = -(Math.random()*2 + 1)*laserSpeedMod;
		}
	}
	private void switchOpenFace(){
		getChildren().remove(west);
		getChildren().remove(east);
		getChildren().remove(north);
		getChildren().remove(south);
		west = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,main.getPrefHeight()/2-((Circle)body).getRadius()-thickness, thickness,2 * ((Circle)body).getRadius()+2*thickness);
		north = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,main.getPrefHeight()/2-((Circle)body).getRadius()-thickness, 2 * ((Circle)body).getRadius() + 2*thickness,thickness);
		east = new Rectangle(main.getPrefHeight()/2+((Circle)body).getRadius(),main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,thickness,2 * ((Circle)body).getRadius()+2*thickness);
		south = new Rectangle(main.getPrefHeight()/2-((Circle)body).getRadius()-thickness,main.getPrefHeight()/2+((Circle)body).getRadius(), 2 * ((Circle)body).getRadius()+2*thickness,thickness);
		int open = (int)(Math.random()*4) + 1;
		switch(open){
			case 1: getChildren().addAll(north,east,south);
							west.setLayoutX(-1000.0);
							west.setLayoutY(-1000.0);
						  break;
			case 2: getChildren().addAll(west,east,south);
							north.setLayoutX(-1000.0);
							north.setLayoutY(-1000.0);
						  break;
			case 3: getChildren().addAll(west,north,south);
							east.setLayoutX(-1000.0);
							east.setLayoutY(-1000.0);
						  break;
			case 4: getChildren().addAll(west,north,east);
							south.setLayoutX(-1000.0);
							south.setLayoutY(-1000.0);
						  break;
		}
	}
	public void spawn(Player p){
		super.spawn(p);
		getChildren().add(projectiles.get(0));
		getChildren().add(projectiles.get(1));
	}

	//override to make sure it only gets hit on open side
	public void collideWithBullet(Player p){ //NOTE: now takes a player instead of gun
		Gun g = p.getGun();
		ArrayList<Bullet> b = g.getBullets();
		if(b.size() > 0){
			//Change: moved b1 outside of loop to make loop more efficient
			Bounds b1 = hitbox.localToScene(hitbox.getBoundsInLocal());

			for(int i = b.size()-1; i >= 0; i--){
				//creates bounds to check if the bullet (b2) intersects body (b1)-- body is a rectangle right now
				Bounds b2 = g.getBullets().get(i).localToScene(g.getBullets().get(i).getBoundsInLocal());
				//when collides removeBullet(Bullet b) removes bullet from playground and arraylist bullets and then subtracts health
				if(b2.intersects(east.localToScene(east.getBoundsInLocal())) || b2.intersects(west.localToScene(west.getBoundsInLocal())) || b2.intersects(south.localToScene(south.getBoundsInLocal()))|| b2.intersects(north.localToScene(north.getBoundsInLocal()))){
					g.removeBullet(b.get(i));
				}
				else if(b1.intersects(b2)){
					//adding knockback to the mob, temporarily sets speed to 0 so they dont walk during animation
					if(knockback){
						double temp = speedModifier;
						speedModifier = 0;
						animatedKnockback(p.getLocX(), p.getLocY(), b.get(i).getKnockBack()); //last number should eventually be p.getGun().getKnockback()
						speedModifier = temp;
					}
					//NOTE: now each bullet has their own damage
					health = health - b.get(i).getDamage();
					g.removeBullet(b.get(i));
					ui.setBossHP(health);
					switchOpenFace();
				}
			}
		}
	}

	//override to make sure knocback works fine, brute forced so needs to change if position/screen size changes
	public void knockbackPlayer(Player p){
		p.animatedKnockback(500, 500, 150);
	}

	public double getBodyHeight(){
		return ((Circle)body).getRadius()*2;
	}
	public double getBodyWidth(){
		return ((Circle)body).getRadius()*2;
	}
	
	private void switchLasers(){
		hLaserSpeed = -hLaserSpeed;
		vLaserSpeed = -vLaserSpeed;
		getChildren().remove(enragedimg);
	}	
}
