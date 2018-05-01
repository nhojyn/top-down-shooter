import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;

//swarm has all the mobs and stuff
public class Swarm{
	
	//fields
	ArrayList<Mob> swarm = new ArrayList<Mob>();
	int numMobs;
	
	//constructors
	public Swarm(int n){
		numMobs = n;
	}
	
	//setters and getters
	public ArrayList<Mob> getSwarm(){return swarm;}
	
	public Mob getSwarm(int i){return swarm.get(i);}	

	//methods
	public void spawnSwarm(Pane main){
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
	
	
}