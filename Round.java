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
	Pane pg;
	int numWaves;
	int currentWave;
	double timeBetweenWaves;
	
	/* list of mobs contains the mobs that will spawn in the round, including type, number of, and which wave they spawn
	 * The format is {mobId, numberOfMobs, waveNumber}
	 * For example, having the int array of {0,4,1} will spawn 4 of the mob with id 0 (ZombieMob) in wave 1 of this round
	 * WAVES START AT 1, not 0
	*/
	ArrayList<int[]> listOfMobs = new ArrayList<int[]>();
	
	Swarm mobs; //mobs is the swarm that is inside the main game
	
	public Round(ArrayList<int[]> list, double time, Pane main, Swarm s){
		listOfMobs = list;
		timeBetweenWaves = time;
		currentWave = 0;
		
		//finds the number of waves
		numWaves = 1;
		for(int[] i : list){
			if(i[2] > numWaves){
				numWaves = i[2];
			}
		}
		
		
	}
	
	public void nextWave(){
		currentWave++;
		for(int[] i : listOfMobs){
			if(i[2] == currentWave){
				switch (i[0]){
					case 0: mobs.spawnZombieSwarm(pg, i[1]);
						    break;
					case 1: 
					
				}
			}
		}
	}
}
