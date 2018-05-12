import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;

/* Each round consits of multiple waves and each of those waves will spawn some mobs.
 * Refer to the wiki page "Round Generation" for mob id's and current round/wave set up.
*/

public class Round{
	int round; //which round this is (e.g round 1, round 2, ...)
	Pane pg;
	int numWaves;
	int currentWave;
	double timeBetweenWaves;
	UserInterface ui;
	Timeline mobSpawning;
	boolean boss; //is this a boss round or not
	boolean upgrade; //is this an upgrade round or not
	Swarm mobs; //mobs is the swarm that is inside the main game

	/* list of mobs contains the mobs that will spawn in the round, including type, number of, and which wave they spawn
	 * The format is {mobId, numberOfMobs, waveNumber}
	 * For example, having the int array of {0,4,1} will spawn 4 of the mob with id 0 (ZombieMob) in wave 1 of this round
	 * WAVES START AT 1, not 0
	*/
	ArrayList<int[]> listOfMobs = new ArrayList<int[]>();

	
	public Round(){
		
	}
	public Round(ArrayList<int[]> list, double time, Pane main, Swarm s, int round, UserInterface ui){
		listOfMobs = list;
		timeBetweenWaves = time;
		currentWave = 0;
		mobs = s;
		this.round = round;
		this.ui = ui;
		pg = main;
		//finds the number of waves
		numWaves = 1;
		for(int[] i : list){
			if(i[2] > numWaves){
				numWaves = i[2];
			}
		}
		
		mobSpawning = new Timeline(new KeyFrame(Duration.millis(timeBetweenWaves), ae -> nextWave()));
		mobSpawning.setCycleCount(Animation.INDEFINITE);
		boss = false;
		upgrade = false;
	}

	//setters and getters
	public int getID(){
		return round;
	}
	public int getNumWaves(){
		return numWaves;
	}
	public double getTimeBtwnWaves(){
		return timeBetweenWaves;
	}

	//methods
	
	/* Iterates the currentWave int, then spawns the mobs in that waves, corresponding to int[] listOfMobs.
 	 * If the currentWave is the final waves, it stops the timeline.
	*/
	public void nextWave(){
		if(currentWave < numWaves){
			currentWave++;
			for(int[] i : listOfMobs){
				if(i[2] == currentWave){
					switch (i[0]){
						case 0: mobs.spawnZombieSwarm(pg, i[1]);
							    break;
						case 1: mobs.spawnLaserSwarm(pg, i[1]);
								break;
						case 2: 
								break;
						case 3:

					}
				}
			}
		}
		else{
			stop();
		}
	}
	
	/* 
	 * Checks to see if all mobs are gone, and if this is the final waves. If so, returns true, and displays
	 * round clear message. the endRound() method for bosses and upgrade rounds will be different
	 * @return: boolean
	*/
	public boolean endRound(){
		if(mobs.getSwarm().size() == 0 && currentWave >= numWaves){
			System.out.println("Round " + round + " Clear");
			return true;
		}
		else{
			return false;
		}
		
	}
	
	//stops the mobSpawning timeline
	public void stop(){
		mobSpawning.stop();
	}
	//pauses the mobSpawning timeline
	public void pause(){
		mobSpawning.pause();
	}
	
	//plays the timeline, starting the round. Also displays round start message.
	public void play(){
		System.out.println("Round " + round + " Start");
		mobSpawning.play();
	}

}
