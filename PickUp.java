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

public class PickUp extends Pane{
	
	Image ammo, health;
	public PickUp(){
	
		try{
			//img = new Image("C:\\Users\\ ... //FullPath ... \\JoPoFX\\src\\jopofx\\myimage.png");
			ammo = new Image("ammobox.png");
			ImageView temp = new ImageView(ammo);
			temp.setFitWidth(50);
			temp.setFitHeight(50);
			//health = new Image("healthkit.png");
			setPrefWidth(temp.getFitWidth());
			setPrefHeight(temp.getFitHeight());
			
			getChildren().add(temp);
			//playground.getChildren().add(getChildren());
	
		}catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}
	}
	
	public void setLoc(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	
	
}