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

public class GunDisplay extends Pane{
	String gunName;
	Text gunText;
	
	public GunDisplay(String s){
		gunName=s;
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		gunText=new Text(gunName);
		gunText.setFill(Color.BLACK);
		gunText.setFont(f1);
		getChildren().add(gunText);
	}
	
	public void setGunText(String s){
		gunName = s;
		gunText.setText(s);
	}
}