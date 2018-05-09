import java.util.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.*; 
import javafx.scene.input.* ;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BossHealthBar extends Pane{
	Rectangle Outline;
	Rectangle HPbar;
	int size;
	int total;
	
	public BossHealthBar(int t){
		total=t+1;
		
		Outline=new Rectangle(800,15);
		Outline.setFill(Color.BLACK);
		Outline.setArcWidth(20);
		Outline.setArcHeight(20);
		HPbar=new Rectangle(Outline.getWidth()-7,Outline.getHeight()-7);
		Rectangle innerWhite = new Rectangle(Outline.getWidth()-7,Outline.getHeight()-7);
		size=(int)HPbar.getWidth();
		HPbar.setArcWidth(10);
		HPbar.setArcHeight(10);
		HPbar.setFill(Color.GREEN);
		innerWhite.setArcWidth(10);
		innerWhite.setArcHeight(10);
		innerWhite.setFill(Color.WHITE);
		int xOffset = 18;
		int yOffset = 18;
		Outline.setLayoutX(xOffset);
		Outline.setLayoutY(yOffset);
		HPbar.setLayoutX(Outline.getWidth()/2-HPbar.getWidth()/2+xOffset);
		HPbar.setLayoutY(Outline.getHeight()/2-HPbar.getHeight()/2+yOffset);
		innerWhite.setLayoutX(Outline.getWidth()/2-HPbar.getWidth()/2+xOffset);
		innerWhite.setLayoutY(Outline.getHeight()/2-HPbar.getHeight()/2+yOffset);
		
		getChildren().addAll(Outline, innerWhite, HPbar);
	}
	
	public void setHP(int i){
		HPbar.setWidth(((double)size/(double)total)*(double)i);
	}
	
	public int getBarWidth(){
		return size;
	}
}