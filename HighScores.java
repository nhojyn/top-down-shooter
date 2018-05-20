import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;
public class HighScores{
	int[] highScoreNums;
	String[] highScoreNames;
	
	HighScores(){
		highScoreNums = new int[10];
		highScoreNames = new String[10];
		
	}
	public int[] getHighScoreNums(){
		return highScoreNums;
	}
	public String[] getHighScoreNames(){
		return highScoreNames;
	}
	public int getHighScoreNum(int i){
		return highScoreNums[i];
	}
	public String getHighScoreName(int i){
		return highScoreNames[i];
	}
	public int getHighScoresSize(){
		return highScoreNums.length;
	}
	public void readHighScores(){
		try{
            FileReader fw = new FileReader("List_highscores.txt");
            BufferedReader bw = new BufferedReader(fw);
			String line;
			int counter = 0;
            while((line = bw.readLine())!= null){
				if(line.length() > 0){
					int num= Integer.parseInt(line.substring(0,line.indexOf("-")));
					highScoreNums[counter] = num;
					highScoreNames[counter] = line.substring(line.indexOf("-")+1);
					counter++;
				}
            }
			for(int i=0;i<highScoreNums.length;i++){
				if(highScoreNames[i]==null){
					highScoreNames[i]="";
				}
			}
            fw.close();
			bw.close();
		

		}catch(Exception e){
			System.out.println("High Score not saved");
			e.printStackTrace();
		}
	}
	public void saveHighScores(){
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("List_highscores.txt",false)); 
			FileWriter fw = new FileWriter("List_highscores.txt");
			for(int i = 0; i < highScoreNums.length; i++){
				fw.write(highScoreNums[i]+"-"+highScoreNames[i]+"\n");
			}
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}	
	
}