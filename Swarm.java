import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;

//swarm has all the mobs and stuff
public class Swarm{
	
	//fields
	ArrayList<Mob> swarm = new ArrayList<Mob>();
	int numMobs;
	Timeline collisionCheck;
	
	//constructors
	public Swarm(int n){
		numMobs = n;
		collisionCheck = new Timeline(new KeyFrame(Duration.millis(10),ae -> checkCollisions()));
		collisionCheck.setCycleCount(Animation.INDEFINITE);
		collisionCheck.play();
	}
	
	//setters and getters
	public ArrayList<Mob> getSwarm(){return swarm;}
	Pane playground;
	
	//methods
	public void spawnSwarm(Pane main){
		playground = main;
		for(int i = 0; i < numMobs; i++){
			double spawnX = 0;
			double spawnY = 0;
			
			int spawnPos = (int)(Math.random()*4); //0=north, 1=east, 2=south, 3=west
			
			switch (spawnPos){
				case 0: spawnX = Math.random()*main.getPrefWidth();
						break;
				case 1: spawnY = Math.random()*main.getPrefHeight();
						break;
				case 2: spawnX = Math.random()*main.getPrefWidth();
						spawnY = main.getPrefHeight();
						break;
				case 3: spawnY = Math.random()*main.getPrefHeight();
						spawnX = main.getPrefWidth();
						break;
			}
			
			System.out.println(spawnX + "," + spawnY);
			Mob temp = new Mob();
			swarm.add(temp);
			main.getChildren().add(temp);
			temp.setLayoutX(spawnX);
			temp.setLayoutY(spawnY);
			
		}
		
	}
	
	public void swarmPlayer(Player p){
		for(int i = 0; i < swarm.size(); i++){
			swarm.get(i).chase(p);
		}
	}
	
	public void resetSwarm(){
		swarm = new ArrayList<Mob>();
	}
	
	private void checkCollisions(){
		checkRoomBounds();
		checkMobCollisions();
	}
	
	private void checkRoomBounds(){
		for(Mob m : swarm){
			if(m.getLayoutX() >= playground.getPrefWidth()){
				m.move(-1,0);
			}
			else if(m.getLayoutX() <= 0){
				m.move(1,0);
			}
			else if(m.getLayoutY() >= playground.getPrefHeight()){
				m.move(0,-1);
			}
			else if(m.getLayoutY() <= 0){
				m.move(0,1);
			}
		}
	}
	
	private void checkMobCollisions(){
		for(int a = 0; a < swarm.size(); a++){
			for (int b = a; b < swarm.size(); b++){
				Rectangle m1 = swarm.get(a).getBody(); //eventually should be Polygon
				Rectangle m2 = swarm.get(b).getBody();
				Bounds b1 = m1.localToScene(m1.getBoundsInLocal());
				Bounds b2 = m2.localToScene(m2.getBoundsInLocal());
				if(b1.intersects(b2)){
					System.out.println("Intersects");
					while(m1.getLayoutX() > m2.getLayoutX() && m1.getLayoutX()-m2.getLayoutX() <= m1.getWidth()){
						swarm.get(a).move(1,0);
						swarm.get(b).move(-1,0);
					}
					while(m1.getLayoutX() < m2.getLayoutX() && m2.getLayoutX()-m1.getLayoutX() <= m1.getWidth()){
						swarm.get(a).move(-1,0);
						swarm.get(b).move(1,0);
					}
					while(m1.getLayoutY() > m2.getLayoutY() && m1.getLayoutY()-m2.getLayoutY() <= m1.getHeight()){
						swarm.get(a).move(0,1);
						swarm.get(b).move(0,-1);
					}
					while(m1.getLayoutY() < m2.getLayoutY() && m2.getLayoutY()-m1.getLayoutY() <= m1.getHeight()){
						swarm.get(a).move(0,-1);
						swarm.get(b).move(0,1);
					}
				}
			}
		}
	}
	
}