/* Each round consits of multiple waves and each of those waves will spawn some mobs.
 * fields: numWaves, listOfMobs, mobs
*/ 

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
	ArrayList<int[3]> listOfMobs = new ArrayList<int[3]>();
	
	Swarm mobs; //mobs is the swarm that is created from listOfMobs
	
	public Round(ArrayList<int[3]> list, double time, Pane main){
		listOfMobs = list;
		timeBetweenWaves = time;
		currentWave = 0;
		
		//finds the number of waves
		numWaves = 1;
		for(int[] i : list){
			if(i[2] > numWaves){
				numWaves = int[2];
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