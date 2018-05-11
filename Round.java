/* Each round consits of multiple waves and each of those waves will spawn some mobs.
 * fields: numWaves, listOfMobs, mobs
*/
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
public class Round{
	int id; //which round this is (e.g round 1, round 2, ...)
	Pane pg;
	int numWaves;
	int currentWave;
	double timeBetweenWaves;
	UserInterface ui;
	Timeline mobSpawning;

	/* list of mobs contains the mobs that will spawn in the round, including type, number of, and which wave they spawn
	 * The format is {mobId, numberOfMobs, waveNumber}
	 * For example, having the int array of {0,4,1} will spawn 4 of the mob with id 0 (ZombieMob) in wave 1 of this round
	 * WAVES START AT 1, not 0
	*/
	ArrayList<int[]> listOfMobs = new ArrayList<int[]>();

	Swarm mobs; //mobs is the swarm that is inside the main game

	public Round(ArrayList<int[]> list, double time, Pane main, Swarm s, int id, UserInterface ui){
		listOfMobs = list;
		timeBetweenWaves = time;
		currentWave = 0;
		mobs = s;
		this.id = id;
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
	}

	//setters and getters
	public int getID(){
		return id;
	}
	public int getNumWaves(){
		return numWaves;
	}
	public double getTimeBtwnWaves(){
		return timeBetweenWaves;
	}


	public void nextWave(){
		if(currentWave < numWaves){
			currentWave++;
			for(int[] i : listOfMobs){
				if(i[2] == currentWave){
					switch (i[0]){
						case 0: mobs.spawnZombieSwarm(pg, i[1]);
							      break;
						case 1: mobs.spawnZombieBoss(pg, ui);
									  break;
						case 2: mobs.spawnLaserSwarm(pg, i[1]);
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
	public void stop(){
		mobSpawning.stop();
	}

	public void pause(){
		mobSpawning.pause();
	}

	public void play(){
		mobSpawning.play();
	}

}
