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
		Round round1 = new Round(r1,1000,main,s,1,ui);
		//the time btwn waves is really low right now, but just for testing, it should be like at least 5-10 seconds

		//round 2
		ArrayList<int[]> r2 = new ArrayList<int[]>();
		r2.add(new int[]{0,8,1});
		r2.add(new int[]{0,16,2});
		Round round2 = new Round(r2,1000,main,s,2,ui);

		//round 3
		Round round3 = new BossRound(0, main, s, 3, ui);

		//adds all rounds
		rounds.add(round1);
		rounds.add(round2);
		rounds.add(round3);
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
