//the circle version of particles

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
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleParticle extends Circle implements Animated{
	
	double xVel;
	double yVel;
	double fade;
	
	CircleParticle(){
		xVel=Math.random()*2;
		if((int)(Math.random()*2)==0){
			xVel=-xVel;
		}
		yVel=Math.random()*2;
		if((int)(Math.random()*2)==0){
			yVel=-yVel;
		}
		fade=Math.random()/20+.015;
		setFill(Color.GREEN);
		setRadius(5);
		
	}
	
	public void animate(){
		setLayoutY(getLayoutY()-yVel);
		setLayoutX(getLayoutX()-xVel);
		setOpacity(getOpacity()-fade);
	}
}