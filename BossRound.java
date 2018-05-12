import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;

public class BossRound extends Round{
	
	int bossId;
	
	public BossRound(int bossId, Pane main, Swarm s, int round, UserInterface ui){
		mobs = s;
		this. bossId = bossId;
		this.ui = ui;
		this.round = round;
		pg = main;
		currentWave = 0;
		numWaves = 1;
		boss = true;
		upgrade = false;
	}
	
	public void play(){
		System.out.println("Boss Round: Round " + round);
		switch (bossId){
			case 0: mobs.spawnZombieBoss(pg, ui);
					break;
			case 1: 
					break;
		}
		currentWave = 1;
	}
}