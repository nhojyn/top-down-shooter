import java.util.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.* ; 
import javafx.scene.input.* ;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HealthPickup extends PickUp{
	int healAmt;
	public HealthPickup(){
		try{
			//img = new Image("C:\\Users\\ ... //FullPath ... \\JoPoFX\\src\\jopofx\\myimage.png");
			health = new Image("healthkit.png");
			imgview = new ImageView(health);
			imgview.setFitWidth(50);
			imgview.setFitHeight(50);
			//health = new Image("healthkit.png");
			setPrefWidth(imgview.getFitWidth());
			setPrefHeight(imgview.getFitHeight());
			
			getChildren().add(imgview);
			//playground.getChildren().add(getChildren());
	
		}catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}
		healAmt=2;
	}
	
	public void setLoc(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	
	public boolean collideWithPlayer(Player p){
		Bounds b1 = p.getBody().localToScene(p.getBody().getBoundsInLocal());
		Bounds b2 = imgview.localToScene(imgview.getBoundsInLocal());
			
		if(b1.intersects(b2)){
			if(p.getHealth()==p.getHealthCap()){
				return false;
			}
			else if(p.getHealth()+healAmt<=p.getHealthCap()){
				p.heal(healAmt);
				return true;
			}else{
				p.heal(p.getHealth()+healAmt-p.getHealthCap());
				return true;
			}			
		}
		return false;
	}
}