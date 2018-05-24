import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.event.*;

/* RoundList has all the rounds that are in the game, and goes through them.
 *
*/

public class RoundList{

	//fields
	ArrayList<Round> rounds = new ArrayList<Round>();
	int currentRound;
	Timeline endCheck; //checks for the end of round

	//setters and getters
	public int getCurrentRound(){
		return currentRound;
	}

	//this constructor contains the list of rounds and their spawns that are listed on the wiki page
	public RoundList(Pane main, Swarm s, UserInterface ui){
		currentRound = 0;

		EventHandler<ActionEvent> checkEnd = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				if(currentRound > 0 && currentRound <= rounds.size() && rounds.get(currentRound-1).endRound()){
					nextRound();
				}
			}
		};
		endCheck = new Timeline(new KeyFrame(Duration.millis(1000), checkEnd));
		endCheck.setCycleCount(Animation.INDEFINITE);
		endCheck.play();

		/* Reminder the the Round constructor is
		 * Round(ArrayList<int[]> mobsList, (time between waves in ms), main, s, (round number), ui)
		 * There's an explanation for how the ArrayList<int[]> works in the Round class.
		*/
		//round 1
		ArrayList<int[]> r1 = new ArrayList<int[]>();
		r1.add(new int[]{0,8,1});
		r1.add(new int[]{0,16,2});
		Round round1 = new Round(r1,5000,main,s,1,ui);
		//the time btwn waves is really low right now, but just for testing, it should be like at least 5-10 seconds

		//round 2
		Round round2 = new BossRound(0, main, s, 2, ui);
		
		//round 3
		ArrayList<int[]> r3 = new ArrayList<int[]>();
		r3.add(new int[]{0,8,1});
		r3.add(new int[]{1,2,1});
		r3.add(new int[]{3,2,2});
		r3.add(new int[]{2,1,2});
		Round round3 = new Round(r3,5000,main,s,3,ui);
		
		//round 4
		ArrayList<int[]> r4 = new ArrayList<int[]>();
		r4.add(new int[]{3,4,1});
		r4.add(new int[]{0,4,1});
		r4.add(new int[]{3,4,2});
		r4.add(new int[]{1,4,2});
		r4.add(new int[]{2,1,2});
		Round round4 = new Round(r4,7000,main,s,4,ui);
		 
		//round 5
		Round round5 = new BossRound(1, main, s, 5, ui);
		
		//round 6
		ArrayList<int[]> r6 = new ArrayList<int[]>();
		r6.add(new int[]{3,8,1});
		r6.add(new int[]{1,2,1});
		Round round6 = new Round(r6,5000,main,s,6,ui);
		
		//round 7
		ArrayList<int[]> r7 = new ArrayList<int[]>();
		r7.add(new int[]{1,8,1});
		Round round7 = new Round(r7,5000,main,s,7,ui);
		
		//round 8
		ArrayList<int[]> r8 = new ArrayList<int[]>();
		r8.add(new int[]{0,16,1});
		r8.add(new int[]{2,1,1});
		r8.add(new int[]{4,6,2});
		r8.add(new int[]{1,2,2});
		Round round8 = new Round(r8,5000,main,s,8,ui);
		
		//round 9
		Round round9 = new BossRound(2, main, s, 9, ui);
		
		//round 10
		ArrayList<int[]> r10 = new ArrayList<int[]>();
		r10.add(new int[]{3,8,1});
		r10.add(new int[]{4,2,1});
		r10.add(new int[]{1,2,2});
		r10.add(new int[]{4,2,2});
		Round round10 = new Round(r10,5000,main,s,10,ui);
		
		//round 11
		ArrayList<int[]> r11 = new ArrayList<int[]>();
		r11.add(new int[]{4,8,1});
		r11.add(new int[]{4,8,2});
		Round round11 = new Round(r11,5000,main,s,11,ui);
		
		//round 12
		Round round12 = new BossRound(3, main, s, 14, ui);
		
		/* Decided to cut two rounds because they're getting a little repetitive (most ppl won't make it past laser boss anyway)
		 * 
		//round 13
		ArrayList<int[]> r13 = new ArrayList<int[]>();
		
		Round round13 = 
		
		//round 14
		Round round14 = 
		*/
		
		//adds all rounds
		rounds.add(round1);
		rounds.add(round2);
		rounds.add(round3);
		rounds.add(round4);
		rounds.add(round5);
		rounds.add(round6);
		rounds.add(round7);
		rounds.add(round8);
		rounds.add(round9);
		rounds.add(round10);
		rounds.add(round11);
		rounds.add(round12);
		
		/*
		rounds.add(round13);
		rounds.add(round14);
		*/
	}

	//this constructor is probably not getting used
	public RoundList(ArrayList<Round> r){
		rounds = r;
		currentRound = 0;
	}

	//methods
	public void nextRound(){
		currentRound++;
		if(currentRound <= rounds.size()){
			rounds.get(currentRound-1).play();
		}
	}

	public void reset(){
		currentRound = 0;
		for(Round r:rounds){
			r.reset();
		}
	}
}
