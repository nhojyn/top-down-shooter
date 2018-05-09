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

public class HealthBar extends Pane{
	Rectangle Outline;
	Rectangle HPbar;
	int size;
	int total;
	Image image;
	Text HPnum;
	
	public HealthBar(int t){
		total=t;
		
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),30);
		HPnum= new Text(Integer.toString(total));
		HPnum.setFill(Color.BLACK);
		HPnum.setFont(f1);
		HPnum.setLayoutX(15);
		HPnum.setLayoutY(40);
		
		image = new Image("pixel-heart.png");
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(65);
		iv.setFitWidth(65);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
		
		Outline=new Rectangle(500,30);
		Outline.setFill(Color.BLACK);
		Outline.setArcWidth(20);
		Outline.setArcHeight(20);
		HPbar=new Rectangle(Outline.getWidth()-10,Outline.getHeight()-10);
		Rectangle innerWhite = new Rectangle(Outline.getWidth()-10,Outline.getHeight()-10);
		size=(int)HPbar.getWidth();
		HPbar.setArcWidth(10);
		HPbar.setArcHeight(10);
		HPbar.setFill(Color.RED);
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
		
		getChildren().addAll(Outline, innerWhite, HPbar,iv,HPnum);
	}
	
	public void setHP(int i){
		HPnum.setText(Integer.toString(i));
		if(i<10){
			HPnum.setLayoutX(25);
		}
		if(i>=10){
			HPnum.setLayoutX(15);
		}
		HPbar.setWidth((size/total)*i);
	}
	
	public int getBarWidth(){
		return size;
	}
}