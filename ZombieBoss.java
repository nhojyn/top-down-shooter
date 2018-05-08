import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;

public class ZombieBoss extends Boss implements TrueBounds{
	
	private final int MAX_COOLDOWN = 250;
	int cooldown = 20; //it will attack every 20*MAX_COOLDOWN ms (so for 125, it shoots every 5 seconds)
	Rectangle west,north,east,south;
	Swarm mobs;
	Pane pg;
	boolean grown = false;
	public ZombieBoss(Pane main, Swarm s){
		super();
		mobs = s;
		pg = main;
		health = 300;
		points = 200;
		body = new Rectangle(0,0,200,200);
		body.setFill(Color.BLUE);
		getChildren().add(body);
		front = new Rectangle(0,0,200,200);
		front.setFill(Color.BLUE);
		west = new Rectangle(0,((Rectangle)body).getHeight()/2 -10, 20,20);
		north = new Rectangle(((Rectangle)body).getWidth()/2-10, 0, 20,20);
		east = new Rectangle(((Rectangle)body).getWidth()-20,((Rectangle)body).getHeight()/2 -10, 20,20);
		south = new Rectangle(((Rectangle)body).getWidth()/2 - 10,((Rectangle)body).getHeight()-20, 20,20);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		getChildren().addAll(front,middle,west,north,east,south);
	}
	
	//grow and shirnk
	public void chase(Player p){
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
	
	//grows and shrinks
	private boolean attack1(){
		if(mobs.getSwarm().size() <=4){
			return false;
		}
		Timeline grow = new Timeline(new KeyFrame(Duration.millis(10),ae -> growAndShrink()));
		grow.setCycleCount(500);
		grow.play();
		return true;
	}
	
	//spawns 1-8 ZombieMobs depending on how many there currently is (will not spawn more than 15 total)
	private boolean attack2(){
		if(mobs.getSwarm().size() >= 15){
			return false;
		}	
		if(mobs.getSwarm().size() <= 15)
			mobs.spawnZombieSwarm(pg,(int)(Math.random()*2) + 1);
		if(mobs.getSwarm().size() <= 10)
			mobs.spawnZombieSwarm(pg,(int)(Math.random()*4) + 1);
		if(mobs.getSwarm().size() <= 5)
			mobs.spawnZombieSwarm(pg,(int)(Math.random()*8) + 1);
		return true;
	}
	
	//doubles the speed of all ZombieMobs currently alive
	private boolean attack3(){
		if(mobs.getSwarm().size() <=1){
			return false;
		}	
		for(Mob m : mobs.getSwarm()){
			m.setSpeedModifier(m.getSpeedModifier()*2);
		}
		return true;
	}
	
	//charges at player
	private boolean attack4(){
		
		return false;
	}
	
	public void knockbackPlayer(Player p){
		p.animatedKnockback(getLayoutX() + getBodyWidth()/2, getLayoutY() + getBodyHeight()/2, 200);
	}
	
	private void growAndShrink(){
		if(getBodyHeight()<=640 && !grown){
			grow();
		}
		if(getBodyHeight()>=640){
			grown = true;
		}
		if(grown){
			shrink();
		}
		if(getBodyHeight()<=200 && grown){
			grown = false;
		}
	}
	private void grow(){
		((Rectangle)body).setWidth(((Rectangle)body).getWidth() + 1.76);
		((Rectangle)body).setHeight(((Rectangle)body).getWidth() + 1.76);
		front.setWidth(front.getWidth() + 1.76);
		front.setHeight(front.getHeight() + 1.76);
		setLayoutX(getLayoutX()- 0.88);
		setLayoutY(getLayoutY()- 0.88);
		getChildren().removeAll(front,middle,west,north,east,south);
		west = new Rectangle(0,((Rectangle)body).getHeight()/2 -10, 20,20);
		north = new Rectangle(((Rectangle)body).getWidth()/2-10, 0, 20,20);
		east = new Rectangle(((Rectangle)body).getWidth()-20,((Rectangle)body).getHeight()/2 -10, 20,20);
		south = new Rectangle(((Rectangle)body).getWidth()/2 - 10,((Rectangle)body).getHeight()-20, 20,20);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		getChildren().addAll(front,middle,west,north,east,south);
	}
	private void shrink(){
		((Rectangle)body).setWidth(((Rectangle)body).getWidth() - 1.76);
		((Rectangle)body).setHeight(((Rectangle)body).getWidth() - 1.76);
		front.setWidth(front.getWidth() - 1.76);
		front.setHeight(front.getHeight() - 1.76);
		setLayoutX(getLayoutX()+ 0.88);
		setLayoutY(getLayoutY()+ 0.88);
		getChildren().removeAll(front,middle,west,north,east,south);
		west = new Rectangle(0,((Rectangle)body).getHeight()/2 -10, 20,20);
		north = new Rectangle(((Rectangle)body).getWidth()/2-10, 0, 20,20);
		east = new Rectangle(((Rectangle)body).getWidth()-20,((Rectangle)body).getHeight()/2 -10, 20,20);
		south = new Rectangle(((Rectangle)body).getWidth()/2 - 10,((Rectangle)body).getHeight()-20, 20,20);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		getChildren().addAll(front,middle,west,north,east,south);
	}
	
	public double getBodyHeight(){
		return ((Rectangle)body).getHeight();
	}
	
	public double getBodyWidth(){
		return ((Rectangle)body).getWidth();
	}
}