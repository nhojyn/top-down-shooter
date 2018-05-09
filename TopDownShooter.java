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

public class TopDownShooter{
	Player player;
	BorderPane screen;
	public static Pane playground;
	Scene mainGame;
	Stage stage;
	Swarm mobs;
	UserInterface ui;
	ParticleEffects pe;
	VBox devTools;
	Controls control;
	AnimationTimer mobMovement;
	AnimationTimer collision;
	ArrayList<Bullet> bullets;
	
	//width and height of main screen
	int width = 1000;
	int height = 1000;
	
	private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
	
	TopDownShooter(Stage s,Button main){
		stage=s;
		
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
		Pane overlay = new Pane();
		overlay.setPrefWidth(width);
		overlay.setPrefHeight(height);
		
		StackPane centered = new StackPane();
		centered.setPrefWidth(width);
		centered.setPrefHeight(height);
		
		playground = new Pane();
		playground.setPrefWidth(width);
		playground.setPrefHeight(height);
		
		centered.getChildren().addAll(playground,overlay);
		screen.setCenter(centered);

		pe=new ParticleEffects(playground);
		
		bullets = new ArrayList<Bullet>();
		player = new Player(playground, bullets);
		playground.getChildren().addAll(player);
		
		ui=new UserInterface(overlay,main,this,player);
		
		mainGame = new Scene(screen);
		
		//Moved all the controls in Controls class
		control = new Controls(mainGame, player, playground);
					
		//checks if mob collides with bullet
		collision= new AnimationTimer(){
			public void handle(long l){
				playerCollisionChecker();
				projectileCollisionChecker();
			}
		};
		collision.start();
		
		//create laser swarm and test it
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
				mobs.spawnLaserSwarm(playground);				
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
		
		//create zombie swarm and test it
		Button spawnZombieBossBtn = new Button();
		devTools.getChildren().add(spawnZombieBossBtn);
		spawnZombieBossBtn.setText("Spawn ZombieBoss");
		spawnZombieBossBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mobs.spawnZombieBoss(playground);
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
					player.grantInvincibility(1);

					knockBackMobs();
					ui.getStatus().setHealthTxt(player.getHealth());
					ui.getHealthBar().setHP(player.getHealth());
					System.out.println("hit"+i);
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
							player.grantInvincibility(1);
							ui.getStatus().setHealthTxt(player.getHealth());
							ui.getHealthBar().setHP(player.getHealth());
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
					player.addToScore(mobs.getSwarm(i).getPoints());
					ui.setScore(player.getScore());
					playground.getChildren().remove(mobs.getSwarm(i));
					mobs.getSwarm().remove(mobs.getSwarm(i));
				}
			}
		}
	}
	
	public void reset(){
		player.reset();
		mobs.resetSwarm();
		ui.reset();
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
}
