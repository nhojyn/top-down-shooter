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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.geometry.Point2D;
import javafx.geometry.*;
public class TopDownShooter{
	Player player;
	BorderPane screen;
	public static Pane playground;
	Scene mainGame;
	MainMenu mainMenu;
	Stage stage;
	Swarm mobs;
	UserInterface ui;
	ParticleEffects pe;
	VBox devTools;
	Controls control;
	AnimationTimer mobMovement;
	AnimationTimer collision;
	ArrayList<Bullet> bullets;
	ArrayList<PickUp> pickups;
	RoundList roundList;
	Pane overlay;
	StackPane centered;
	HighScores highScores;
	SetHighScores setHighScores;
	//width and height of main screen
	int width = 1000;
	int height = 1000;

	private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;

	TopDownShooter(Stage s,Button main,MainMenu mm,HighScores hs){
		stage=s;
		highScores=hs;
		mainMenu=mm;

		setHighScores = new SetHighScores(stage, highScores, mainMenu);


		screen=new BorderPane();

		//devTools holds all of the developer tools
		devTools = new VBox();
		devTools.setStyle("-fx-background-color: black");
		devTools.setPrefWidth(220);
		String cssLayout = "-fx-border-color: pink;\n" +
                   "-fx-border-insets: 5 ;\n" +
                   "-fx-border-width: 5;\n" +
                   "-fx-border-style: solid;\n"+
                   "-fx-background-color: black;";

		devTools.setStyle(cssLayout);
		devTools.setSpacing(15);
		screen.setRight(devTools);

		//label is a frameRate meter
        Label label = new Label();
        AnimationTimer frameRateMeter = new AnimationTimer() {

            @Override
            public void handle(long now) {
                long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = now ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    label.setText(String.format("Current frame rate: %.3f", frameRate));
                }
            }
        };
        frameRateMeter.start();
		label.setTextFill(Color.web("#FFFFFF"));
		devTools.getChildren().add(label);

		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		Text devTitle = new Text("Dev Tools");
		devTitle.setFont(f1);
		devTitle.setFill(Color.WHITE);
		devTools.getChildren().add(devTitle);

		//overlay and stackpane are so that the ui is over the stuff in the playground
		overlay = new Pane();
		overlay.setPrefWidth(width);
		overlay.setPrefHeight(height);

		centered = new StackPane();
		centered.setPrefWidth(width);
		centered.setPrefHeight(height);

		playground = new Pane();
		playground.setPrefWidth(width);
		playground.setPrefHeight(height);

		centered.getChildren().addAll(playground,overlay);
		screen.setCenter(centered);

		pe=new ParticleEffects(playground);

		bullets = new ArrayList<Bullet>();

		pickups = new ArrayList<PickUp>();

		player = new Player(playground, bullets);
		playground.getChildren().addAll(player);

		ui=new UserInterface(overlay,main,this,player);

		mainGame = new Scene(screen);

		//Moved all the controls in Controls class
		control = new Controls(mainGame, player, playground,ui);

		//checks if mob collides with bullet
		collision= new AnimationTimer(){
			public void handle(long l){
				playerCollisionChecker();
				projectileCollisionChecker();
			}
		};
		collision.start();

		mobs = new Swarm();

		mobMovement= new AnimationTimer(){
			public void handle(long l){
				mobs.swarmPlayer(player);
			}
		};
		mobMovement.start();
		//mobMovement = new Timeline(new KeyFrame(Duration.millis(20),ae -> mobs.swarmPlayer(player)));
		//mobMovement.setCycleCount(Animation.INDEFINITE);
		Button spawnLaserBtn = new Button();
		devTools.getChildren().add(spawnLaserBtn);
		spawnLaserBtn.setText("Spawn Laser Machine");
		spawnLaserBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnLaserSwarm(playground,1);
				//mobMovement.play();
			}
		});

		//create zombie swarm and test it
		Button spawnZombieBtn = new Button();
		devTools.getChildren().add(spawnZombieBtn);
		spawnZombieBtn.setText("Spawn Zombie");
		spawnZombieBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnZombieSwarm(playground,4);
				//mobMovement.play();
			}
		});


		Button spawnSplitterBtn = new Button();
		devTools.getChildren().add(spawnSplitterBtn);
		spawnSplitterBtn.setText("Spawn Splitter");
		spawnSplitterBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnSplitterSwarm(playground,8);
				//mobMovement.play();
			}
		});

		//spawns pistol mobs
		Button spawnPistolMobBtn = new Button();
		devTools.getChildren().add(spawnPistolMobBtn);
		spawnPistolMobBtn.setText("Spawn PistolMobs");
		spawnPistolMobBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnPistolMobSwarm(playground,4);
			}
		});

		//test knockback
		Button knockBtn = new Button();
		devTools.getChildren().add(knockBtn);
		knockBtn.setLayoutX(100);
		knockBtn.setText("knockback");
		knockBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				knockBackMobs();
			}
		});
		
		//test unlock weapon
		Button unlockGunBtn = new Button();
		devTools.getChildren().add(unlockGunBtn);
		unlockGunBtn.setLayoutX(100);
		unlockGunBtn.setText("Unlock next gun");
		unlockGunBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				player.unlockNextGun();
			}
		});

		//test upgradeBlink drop
		Button upgradeBlinkBtn = new Button();
		devTools.getChildren().add(upgradeBlinkBtn);
		upgradeBlinkBtn.setLayoutX(100);
		upgradeBlinkBtn.setText("Drop upgrade blink item");
		upgradeBlinkBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				BlinkUpgradeDrop temp = new BlinkUpgradeDrop(playground);
				pickups.add(temp);
				temp.setLoc(500,500);
			}
		});
			
		//test upgradeBlink drop
		Button upgradeSpeedBtn = new Button();
		devTools.getChildren().add(upgradeSpeedBtn);
		upgradeSpeedBtn.setLayoutX(100);
		upgradeSpeedBtn.setText("Drop upgrade speed item");
		upgradeSpeedBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				SpeedUpgradeDrop temp = new SpeedUpgradeDrop(playground);
				pickups.add(temp);
				temp.setLoc(500,250);
			}
		});

		//create zombie swarm and test it
		Button spawnZombieBossBtn = new Button();
		devTools.getChildren().add(spawnZombieBossBtn);
		spawnZombieBossBtn.setText("Spawn ZombieBoss");
		spawnZombieBossBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnZombieBoss(playground,ui);
			}
		});

		//create zombie swarm and test it
		Button spawnBouncerBossBtn = new Button();
		devTools.getChildren().add(spawnBouncerBossBtn);
		spawnBouncerBossBtn.setText("Spawn BouncerBoss");
		spawnBouncerBossBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnBouncerBoss(playground,ui);
			}
		});

		Button spawnLaserBossBtn = new Button();
		devTools.getChildren().add(spawnLaserBossBtn);
		spawnLaserBossBtn.setText("Spawn LaserBoss");
		spawnLaserBossBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnLaserBoss(playground,ui);
			}
		});

		Button spawnBulletBossBtn = new Button();
		devTools.getChildren().add(spawnBulletBossBtn);
		spawnBulletBossBtn.setText("Spawn BulletBoss");
		spawnBulletBossBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnBulletBoss(playground,ui);
			}
		});

		//give guns more ammo
		Button addAmmo = new Button();
		devTools.getChildren().add(addAmmo);
		addAmmo.setText("Add ammo");
		addAmmo.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				for(int i=0; i<player.getGuns().size();i++){
					player.getGuns().get(i).setAmmo(10000);
					//ui.getStatus().setAmmoTxt(player.getGun().getAmmo());
				}
			}
		});

		//initiates roundList and starts playing the rounds
		roundList = new RoundList(playground, mobs, ui,overlay);
		Button startRounds = new Button();
		devTools.getChildren().add(startRounds);
		startRounds.setText("Start Rounds");
		startRounds.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				roundList.nextRound();
			}
		});

		//kills all the mobs (used for testing rounds)
		Button killMobs = new Button();
		devTools.getChildren().add(killMobs);
		killMobs.setText("Kill Mobs");
		killMobs.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				int temp = mobs.getSwarm().size();
				for(int i = 0; i <temp; i++){
					if(mobs.getSwarm(0) instanceof LaserMachine){
						pe.CircleExplosion(mobs.getSwarm(0).getAbsoluteMiddleX(),mobs.getSwarm(0).getAbsoluteMiddleY());
					}
					if(mobs.getSwarm(0) instanceof ZombieMob){
						pe.RectExplosion(mobs.getSwarm(0).getAbsoluteMiddleX(),mobs.getSwarm(0).getAbsoluteMiddleY());
					}
					if(mobs.getSwarm(0) instanceof ZombieBoss){
						ui.removeBossHP();
					}
					player.addToScore(mobs.getSwarm(0).getPoints());
					ui.setScore(player.getScore());
					playground.getChildren().remove(mobs.getSwarm(0));
					mobs.getSwarm().remove(0);
				}
			}
		});

		//sets Player hp to 1
		Button setHP = new Button();
		devTools.getChildren().add(setHP);
		setHP.setText("Set player HP to 1");
		setHP.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				player.setHealth(1);
				ui.getHealthBar().setHP(player.getHealth());
			}
		});
		
		Button setHP100 = new Button();
		devTools.getChildren().add(setHP100);
		setHP100.setText("Set player HP to 10");
		setHP100.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				player.setHealth(10);
				ui.getHealthBar().setHP(player.getHealth());
			}
		});

		Button spawnBouncerBtn = new Button();
		devTools.getChildren().add(spawnBouncerBtn);
		spawnBouncerBtn.setText("Spawn Bouncer");
		spawnBouncerBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnBouncerSwarm(playground,100);
				//mobMovement.play();
			}
		});
	}

	private void knockBackMobs(){
		mobMovement.stop();
		mobs.knockbackMobsAnimated(player, 300);
		mobMovement.start();
	}

	public void play(){
		stage.setScene( mainGame );
		//high score error checking;
		/*for(int i=0;i<highScores.getHighScoresSize();i++){
			System.out.println(highScores.getHighScoreNums()[i]);
		}*/
		player.setLayoutX(playground.getWidth()/2);
		player.setLayoutY(playground.getHeight()/2);
	}

	//BUG:THIS METHOD WILL SOMETIMES TRIGGER TWICE, MOST LIKELY DUE TO THE ANIMATION TIMER CALLING
	//THE METHOD BEFORE IT IS DONE CALCULATING
	public void playerCollisionChecker(){
		//if there are mobs, check for each mob if they collided with bullet
		if(mobs.getSwarm().size() > 0){
			for(int i = 0; i < mobs.getSwarm().size(); i++){
				if(player.collideWithMob(mobs.getSwarm(i))){
					if(player.getHealth()>=1){
						player.grantInvincibility(1);
						knockBackMobs();
						//ui.getStatus().setHealthTxt(player.getHealth());
						ui.getHealthBar().setHP(player.getHealth());
					}else if(player.getHealth()==0){
						ui.getHealthBar().setHP(player.getHealth());
						ui.gameOver();
						pause();
						if(player.getScore()>highScores.getHighScoreNums()[highScores.getHighScoresSize()-1]){
							Timeline HS = new Timeline(new KeyFrame(Duration.seconds(ui.getFadeTime()),ae -> newHighScore()));
							HS.play();
						}else{
							Timeline quit = new Timeline(new KeyFrame(Duration.seconds(ui.getFadeTime()),ae -> quit()));
							quit.play();
						}
					}
				}
			}
		}
	}

	public void projectileCollisionChecker(){
		if(mobs.getSwarm().size() > 0){
			for(int i = 0; i < mobs.getSwarm().size(); i++){
				//checks if player is getting hit by any MobProjectile
				if(mobs.getSwarm().get(i).getAttacks() && mobs.getSwarm().get(i).getShooting()){
					for(MobProjectile p : mobs.getSwarm().get(i).getProjectiles()){
						if(player.collideWithProjectile(p)){
							if(player.getHealth()>=1){
								player.grantInvincibility(1);
								//ui.getStatus().setHealthTxt(player.getHealth());
								ui.getHealthBar().setHP(player.getHealth());
							}
							else if(player.getHealth()==0){
								ui.getHealthBar().setHP(player.getHealth());
								ui.gameOver();
								pause();
								if(player.getScore()>highScores.getHighScoreNums()[highScores.getHighScoresSize()-1]){
									Timeline HS = new Timeline(new KeyFrame(Duration.seconds(ui.getFadeTime()),ae -> newHighScore()));
									HS.play();
								}else{
									Timeline quit = new Timeline(new KeyFrame(Duration.seconds(ui.getFadeTime()),ae -> quit()));
									quit.play();
								}
							}
						}
					}
				}
				//colldeWithBullet is in Mob class
				mobs.getSwarm(i).collideWithBullet(player);
				if(mobs.getSwarm(i).getHealth() < 0){
					if(mobs.getSwarm(i) instanceof LaserMachine){
						pe.CircleExplosion(mobs.getSwarm(i).getAbsoluteMiddleX(),mobs.getSwarm(i).getAbsoluteMiddleY());
					}
					if(mobs.getSwarm(i) instanceof ZombieMob){
						pe.RectExplosion(mobs.getSwarm(i).getAbsoluteMiddleX(),mobs.getSwarm(i).getAbsoluteMiddleY());
					}
					if(mobs.getSwarm(i) instanceof Splitter){
						pe.RectExplosion(mobs.getSwarm(i).getAbsoluteMiddleX(),mobs.getSwarm(i).getAbsoluteMiddleY());
						if(((Splitter)mobs.getSwarm(i)).getSize()>1){
							mobs.spawnSplitterSwarm(playground,((Splitter)mobs.getSwarm(i)).getSize()/2,mobs.getSwarm(i).getAbsoluteMiddleX(),mobs.getSwarm(i).getAbsoluteMiddleY());
						}
					}
					if(mobs.getSwarm(i).isBoss()){
						
						ui.removeBossHP();
					}
					if(mobs.getSwarm(i) instanceof PistolMob){
						int temp = mobs.getSwarm(i).getProjectiles().size();
						for(int b = 0; b < temp; b++){
							playground.getChildren().remove(mobs.getSwarm(i).getProjectiles().get(0));
							mobs.getSwarm(i).getProjectiles().remove(0);
						}
					}
					int temp = mobs.getSwarm(i).getProjectiles().size();
					for(int b = 0; b < temp; b++){
						playground.getChildren().remove(mobs.getSwarm(i).getProjectiles().get(0));
						mobs.getSwarm(i).getProjectiles().remove(0);
					}
					player.addToScore(mobs.getSwarm(i).getPoints());
					ui.setScore(player.getScore());
					spawnItem(Math.random(),mobs.getSwarm(i));

					playground.getChildren().remove(mobs.getSwarm(i));
					mobs.getSwarm().remove(mobs.getSwarm(i));

				}
				//checks if player is out of bounds
				player.checkRoomBounds();
			}
		}

		//checks if bullet should be removed after traveling this much distance(for flamethrower)
		if(bullets.size() > 0){
			for(int b = 0; b < bullets.size(); b++){
				if(bullets.get(b).getDistanceLimit() < 0){
					player.getGun().removeBullet(bullets.get(b));
				}
			}
		}


		//checks pickup collisions with player
		if(pickups.size() > 0){
			for(int p = 0; p < pickups.size(); p++){
				if(pickups.get(p).collideWithPlayer(player)){
					playground.getChildren().remove(pickups.get(p));
					pickups.remove(p);
					ui.getAmmoCounter().setAmmoNum(player.getGun().getAmmo());
					ui.getHealthBar().setHP(player.getHealth());
				}
			}
		}

	}

	public void resetPickups(){
		for(int i = pickups.size() -1; i >= 0;i--){
			playground.getChildren().remove(pickups.get(i));
			pickups.remove(i);
		}
	}

	public void reset(){
		player.reset();
		mobs.resetSwarm();
		ui.reset();
		roundList.reset();
		resetPickups();
		//defaults it back to pistol
		player.changeGun(0,ui);
	}

	public void pause(){
		mobMovement.stop();
		collision.stop();
		player.pause();
		control.pause();
		mobs.pause();
	}

	public void resume(){
		mobMovement.start();
		collision.start();
		player.play();
		control.play();
		mobs.play();
	}

	public double getWidth(){
		return width;
	}

	public double getHeight(){
		return height;
	}

	public void quit(){
		reset();
		mainMenu.fadeIn();
	}

	private void newHighScore(){
		setHighScores.displaySetHighScores(player.getScore());
		reset();
	}


	public void spawnItem(double i, Mob m){
		double rarity = 0.03;
		if(i < rarity){
			Bounds boundsInScene = m.getBody().localToScene(m.getBody().getBoundsInLocal());
			//testing: spawn pickup
			AmmoPickup p = new AmmoPickup(playground);
			p.setLoc(m.getAbsoluteMiddleX(), m.getAbsoluteMiddleY());
		//	p.setLoc(boundsInScene.getMinX() - boundsInScene.getWidth()/2,boundsInScene.getMinY() - boundsInScene.getHeight()/2);
		//	playground.getChildren().add(p);
			pickups.add(p);
		}else if(i >= rarity && i < rarity + 0.01){
			Bounds boundsInScene = m.getBody().localToScene(m.getBody().getBoundsInLocal());
			//testing: spawn pickup
			HealthPickup p = new HealthPickup(playground);
			p.setLoc(m.getAbsoluteMiddleX(), m.getAbsoluteMiddleY());
		//	p.setLoc(boundsInScene.getMinX() - boundsInScene.getWidth()/2,boundsInScene.getMinY() - boundsInScene.getHeight()/2);
		//	playground.getChildren().add(p);
			pickups.add(p);
		}else if(i >= rarity + 0.01 && i < rarity + 0.03){
			Bounds boundsInScene = m.getBody().localToScene(m.getBody().getBoundsInLocal());
			//testing: spawn pickup
			ImmunityBuff p = new ImmunityBuff(playground);
			p.setLoc(m.getAbsoluteMiddleX(), m.getAbsoluteMiddleY());
		//	p.setLoc(boundsInScene.getMinX() - boundsInScene.getWidth()/2,boundsInScene.getMinY() - boundsInScene.getHeight()/2);
	//		playground.getChildren().add(p);
			pickups.add(p);
		}
	}
}
