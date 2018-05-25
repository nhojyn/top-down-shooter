import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.scene.image.*;

public class ZombieBoss extends Boss implements TrueBounds{
	
	private final int MAX_COOLDOWN = 250;
	int cooldown = 20; //it will attack every 20*MAX_COOLDOWN ms (so for 125, it shoots every 5 seconds)
	Swarm mobs;
	Pane pg;
	boolean grown = false;
	ImageView imgview;
	ImageView enragedimg;
	boolean enraged;
	public ZombieBoss(Pane main, Swarm s,UserInterface ui){
		super(ui);
		mobs = s;
		pg = main;
		health = 200;
		ui.addBossHP(health);
		points = 200;
		
		//body = new Rectangle(0,0,200,200);
		body = new Rectangle(0,0,0,0);
		body.setFill(Color.TRANSPARENT);
		getChildren().add(body);
		front = new Rectangle(0,0,0,0);
		front.setFill(Color.TRANSPARENT);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		middle.setFill(Color.TRANSPARENT);
		getChildren().addAll(front,middle);
		try{
			Image img = new Image("zombie.png");
			imgview = new ImageView(img);
			imgview.setFitWidth(1);
			imgview.setFitHeight(1);
			getChildren().add(imgview);
			Image en = new Image("zombie_1.png");
			enragedimg = new ImageView(en);
			enragedimg.setFitWidth(200);
			enragedimg.setFitHeight(200);
		}
		catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}	
		enraged = false;
	}
	
	public void spawn(Player p){
		super.spawn(p);
		Timeline spawn = new Timeline(new KeyFrame(Duration.millis(10), ae -> spawnGrow()));
		spawn.setCycleCount(100);
		spawn.play();
		
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
		toggleEnraged();
		Timeline toggle = new Timeline(new KeyFrame(Duration.millis(1000),ae -> toggleEnraged()));
		toggle.setCycleCount(1);
		toggle.play();		
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
		getChildren().removeAll(front,middle);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		middle.setFill(Color.TRANSPARENT);
		getChildren().addAll(front,middle);
		imgview.setFitWidth(front.getWidth());
		imgview.setFitHeight(front.getHeight());
		enragedimg.setFitWidth(front.getWidth());
		enragedimg.setFitHeight(front.getHeight());
	}
	private void shrink(){
		((Rectangle)body).setWidth(((Rectangle)body).getWidth() - 1.76);
		((Rectangle)body).setHeight(((Rectangle)body).getWidth() - 1.76);
		front.setWidth(front.getWidth() - 1.76);
		front.setHeight(front.getHeight() - 1.76);
		setLayoutX(getLayoutX()+ 0.88);
		setLayoutY(getLayoutY()+ 0.88);
		getChildren().removeAll(front,middle);
		middle = new Rectangle(((Rectangle)body).getWidth()/2, ((Rectangle)body).getHeight()/2, 5,5);
		middle.setFill(Color.TRANSPARENT);
		getChildren().addAll(front,middle);
		imgview.setFitWidth(front.getWidth());
		imgview.setFitHeight(front.getHeight());
		enragedimg.setFitWidth(front.getWidth());
		enragedimg.setFitHeight(front.getHeight());
	}
	
	public double getBodyHeight(){
		return ((Rectangle)body).getHeight();
	}
	
	public double getBodyWidth(){
		return ((Rectangle)body).getWidth();
	}
	
	private void spawnGrow(){
		((Rectangle)body).setWidth(((Rectangle)body).getWidth() + 2);
		((Rectangle)body).setHeight(((Rectangle)body).getHeight() + 2);
		front.setWidth(front.getWidth() + 2);
		front.setHeight(front.getHeight() + 2);
		setLayoutX(getLayoutX()- 1);
		setLayoutY(getLayoutY()-1);
		imgview.setFitWidth(front.getWidth());
		imgview.setFitHeight(front.getHeight());
	}
	
	private void toggleEnraged(){
		enragedimg.setFitWidth(front.getWidth());
		enragedimg.setFitHeight(front.getHeight());
		if(enraged){
			getChildren().remove(enragedimg);
			enraged = false;
		}
		else{
			getChildren().add(enragedimg);
			enraged = true;
		}
	}
}