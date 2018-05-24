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

public class ImmunityBuff extends PickUp{
	public ImmunityBuff(Pane pg){
		super(pg);
		try{
			img = new Image("immunity.png");
			imgview = new ImageView(img);
			imgview.setFitWidth(50);
			imgview.setFitHeight(50);
			
			setPrefWidth(imgview.getFitWidth());
			setPrefHeight(imgview.getFitHeight());
			
			getChildren().add(imgview);
			playground.getChildren().add(this);
	
		}catch(Exception e){
			System.out.println("error while creating image");
			e.printStackTrace();
		}
	}
	
	public void setLoc(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	
	public boolean collideWithPlayer(Player p){
		Bounds b1 = p.getBody().localToScene(p.getBody().getBoundsInLocal());
		Bounds b2 = imgview.localToScene(imgview.getBoundsInLocal());
		if(b1.intersects(b2)){
			p.grantInvincibility(10);
			p.getBody().setFill(Color.YELLOW);
			return true;	
		}
		return false;		
		
	}
	
	
	
}
