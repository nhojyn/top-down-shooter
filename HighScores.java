import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;
public class HighScores{
	int[] highScores;
	
	HighScores(){
		highScores = new int[10];
		
	}
	public int getHighScore(int i){
		return highScores[i];
	}
	public int getHighScoresSize(){
		return highScores.length;
	}
	/**	
		Adds list of top 3 high scores
		@param: 
		Precondition: 
		Postcondition: adds to list of highscores 
	*/
	public void addListHighScore(){
		try{
            FileReader fw = new FileReader("List_highscores.txt");
            BufferedReader bw = new BufferedReader(fw);
			String line;
			int temp = 0;
            while((line = bw.readLine())!= null){
				if(line.length() > 0){
					int num= Integer.parseInt(line);
					highScores[temp] = num;
					temp++;
				}
            }
            fw.close();
			bw.close();
		

		}catch(Exception e){
			e.printStackTrace();
		}
		
//		Text temp1 = new Text("Highscores");
//		temp1.setFill(Color.YELLOW);
//		temp1.setStyle("-fx-font: 17 arial;");
//		listHighScore.getChildren().add(temp1);
//		for(int i = 0; i < 3; i++){
//			listHighScore.getChildren().add(new Text("#"+ (i+1) + "       " + highscores[i]));
//		}
	}
	/**	
		Updates the highscores and then remakes the list of highscores. writes the new highscores List_highscores.txt
		@param: 
		Precondition: 
		Postcondition: highscore list will be updated and List_highscores.txt will be renewed
	*/
	public void updateHighScore(){
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("List_highscores.txt",false)); 
			FileWriter fw = new FileWriter("List_highscores.txt");
			for(int i = 0; i < 3; i++){
				fw.write("\n" + highScores[i]);
				
			}
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		addListHighScore();
	}	
	
}