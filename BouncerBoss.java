import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
public class BouncerBoss extends Boss implements TrueBounds{
	
	private final int MAX_COOLDOWN = 500;
	int cooldown = 20; //it will attack every 20*MAX_COOLDOWN ms (so for 125, it shoots every 5 seconds)
	Rectangle west,north,east,south;
	Swarm mobs;
	Pane pg;
	Rectangle blade,blade2;
	int xSpeed;
	int ySpeed;
	int minSpeed;
	int speedUp;
	Timeline spin;
	public BouncerBoss(Pane main, Swarm s,UserInterface ui){
		super(ui);
		mobs = s;
		pg = main;
		health = 300;
		ui.addBossHP(health);
		points = 200;
		xSpeed = 3;
		ySpeed = 3;
		minSpeed = 3;
		speedUp = 0;
	//	setStyle("-fx-background-color: black;");
		setPrefSize(200,200);
		body = new Circle(getPrefWidth()/2, getPrefHeight()/2, 100);
		body.setFill(Color.PURPLE);
		blade = new Rectangle(10, 10,((Circle)body).getRadius()*2-20,((Circle)body).getRadius()*2-20);
		blade.setFill(Color.PINK);
		blade2 = new Rectangle(10, 10,((Circle)body).getRadius()*2-20,((Circle)body).getRadius()*2-20);
		blade2.setFill(Color.YELLOW);	
		blade2.setRotate(45);
		//hitbox for boss(it is slightly bigger than body)
		front = new Rectangle(((Circle)body).getCenterX()-110,((Circle)body).getCenterY()-110,220,220);
		front.setFill(Color.TRANSPARENT);
	 	spin = new Timeline(new KeyFrame(Duration.millis(15),ae -> rotateBlade()));
	 	spin.setCycleCount(Animation.INDEFINITE);
		spin.play();
		getChildren().addAll(blade,blade2,body,front);

	//	front = new Rectangle(0,0,200,200);
	//	front.setFill(Color.BLUE);
	//	west = new Rectangle(0,((Rectangle)body).getHeight()/2 -10, 20,20);
	//	north = new Rectangle(((Rectangle)body).getWidth()/2-10, 0, 20,20);
	//	east = new Rectangle(((Rectangle)body).getWidth()-20,((Rectangle)body).getHeight()/2 -10, 20,20);
	//	south = new Rectangle(((Rectangle)body).getWidth()/2 - 10,((Rectangle)body).getHeight()-20, 20,20);
	//	middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
	//	getChildren().addAll(front,middle,west,north,east,south);
	}
	
	public void rotateBlade(){
		blade.setRotate(blade.getRotate()+1);
		blade2.setRotate(blade2.getRotate()+1);
	}
	//grow and shirnk
	public void chase(Player p){
		setLayoutX(getLayoutX()+xSpeed);
		setLayoutY(getLayoutY()+ySpeed);
		if(getLayoutY() > pg.getHeight() - ((Circle)body).getRadius()*2){
			ySpeed = -(int)(Math.random()*3)-minSpeed-speedUp;
		}else if(getLayoutX() > pg.getWidth() - ((Circle)body).getRadius()*2){
			xSpeed = -(int)(Math.random()*3)-minSpeed-speedUp;
		}else if(getLayoutX() < 0){
			xSpeed = (int)(Math.random()*3)+minSpeed+speedUp;
		}else if(getLayoutY() < 0){
			ySpeed = (int)(Math.random()*3)+minSpeed+speedUp;
		}
		if(!spawned){
			spawn(p);
		}
		cooldown--;
		if(cooldown <= 0){
			attack();
		}
		
	}
	
	public void attack(){
		boolean attacked = false;
		while(!attacked){
			int attackNum = (int)(Math.random()*4) + 1; 
			switch (attackNum){
				case 1: attacked = attack1();
						break;
				case 2: attacked = attack2();
						break;
				case 3: attacked = attack3();
						break;
				case 4: attacked = attack4();
						break;
			}
		}
		cooldown = MAX_COOLDOWN;
	}
	
		
	//speed up boss
	private boolean attack1(){
		speedUp = 7;
		blade.setFill(Color.GREEN);		
        blade2.setFill(Color.GREEN);	
		AnimationTimer speedUpBuff = new AnimationTimer() {
			int counter=0;
            @Override
            public void handle(long now) {
                counter++;
                if(counter == 275){
                	speedUp = 0;
                	blade.setFill(Color.PINK);		
                	blade2.setFill(Color.YELLOW);	
                }
            }
        };
        speedUpBuff.start();
        return true;
	}
	
	//spawns 1-5 BouncerMobs depending on how many there currently is (will not spawn more than 15 total)
	private boolean attack2(){
		double temp = spin.getRate();
		int temp1 = xSpeed;
		int temp2 = ySpeed;
		
		//make blades spin faster and stop the boss
		spin.setRate(7);
		blade.setFill(Color.RED);		
        blade2.setFill(Color.RED);	
        xSpeed = 0;
        ySpeed = 0;
        
	 	Timeline shootAttack = new Timeline(new KeyFrame(Duration.millis(500),ae -> shoot()));
	 	shootAttack.setCycleCount(10);
		shootAttack.play();
        
        shootAttack.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {	
				shootAttack.stop();
				xSpeed = temp1;
				ySpeed = temp2;
				spin.setRate(temp);
				blade.setFill(Color.PINK);		
            	blade2.setFill(Color.YELLOW);	
			}
		});
	
		if(mobs.getSwarm().size() >= 15){
			return false;
		}	
		if(mobs.getSwarm().size() <= 15)
			mobs.spawnBouncerSwarm(pg,(int)(Math.random()*2) + 1);
		if(mobs.getSwarm().size() <= 10)
			mobs.spawnBouncerSwarm(pg,(int)(Math.random()*3) + 1);
		if(mobs.getSwarm().size() <= 5)
			mobs.spawnBouncerSwarm(pg,(int)(Math.random()*5) + 1);
		return true;
	}
	
	//doesnt do anything right now
	private boolean attack3(){
		if(mobs.getSwarm().size() <=1){
			return false;
		}	
		for(Mob m : mobs.getSwarm()){
			m.setSpeedModifier(m.getSpeedModifier()*2);
		}
		return true;
	}
	
	//charges at playern
	private boolean attack4(){
		
		return false;
	}
	public void shoot(){
	// 	int rand = (int)(Math.random()*4);
// 		if(rand == 0){
// 			ySpeed = 20;
// 			xSpeed = 0;
// 		}else if(rand == 1){
// 			ySpeed = -20;
// 			xSpeed = 0;
// 		}else if(rand == 2){
// 			xSpeed = 20;
// 			ySpeed = 0;
// 		}else if(rand == 3){
// 			xSpeed = -20;
// 			ySpeed = 0;
// 		}
	}
	
	public void knockbackPlayer(Player p){
		p.animatedKnockback(getLayoutX() + getBodyWidth()/2, getLayoutY() + getBodyHeight()/2, 100);
	}
	
	public double getBodyHeight(){
		return ((Circle)body).getRadius();
	}
	
	public double getBodyWidth(){
		return ((Circle)body).getRadius();
	}
}