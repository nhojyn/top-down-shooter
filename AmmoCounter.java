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

public class AmmoCounter extends Pane{
	int ammo;
	Text AmmoNum;
	Image image;
	
	AmmoCounter(int num){
		ammo=num;
		
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		AmmoNum= new Text(Integer.toString(ammo));
		AmmoNum.setFill(Color.BLACK);
		AmmoNum.setFont(f1);
		AmmoNum.setLayoutX(37);
		AmmoNum.setLayoutY(57);
		
		image = new Image("bullet.png");
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(100);
		iv.setFitWidth(100);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
		
		getChildren().addAll(iv,AmmoNum);
	}
	
	public void setAmmoNum(int i){
		AmmoNum.setText(Integer.toString(i));
	}
	
	
}