import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.util.Duration;
import javafx.animation.*;

public class RoundList{

	ArrayList<Round> rounds = new ArrayList<Round>();
	int currentRound;
	int maxRounds;

	public RoundList(ArrayList<Round> r){
		rounds = r;
		currentRound = 0;
		maxRounds = r.size();
	}

	public RoundList(Pane main, Swarm s, UserInterface ui){
		currentRound = 0;

		//round 1
		ArrayList<int[]> r1 = new ArrayList<int[]>();
		r1.add(new int[]{0,8,1});
		r1.add(new int[]{0,16,2});
		Round round1 = new Round(r1,7000,main,s,1,ui);

		//round 2

		//adds all rounds
		rounds.add(round1);
	}

	public void nextRound(){
		currentRound++;
		rounds.get(currentRound-1).play();
	}
}
