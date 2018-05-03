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
import javafx.scene.shape.Rectangle;
//creates particles and moves them, parameters are the x and y coordinates

import javafx.util.Duration;

public class ParticleEffects{
	Pane Playground;
	Timeline timeline;
	
	//ArrayList<
	
	ParticleEffects(Pane p){
		Playground=p;
	}
	
	public void RectExplosion(double x, double y){
		ArrayList<RectParticle> rectParticles = new ArrayList<RectParticle>();
		for(int i=0;i<(int)Math.random()*50+175;i++){
			RectParticle r = new RectParticle();
			r.setLayoutX(x);
			r.setLayoutY(y);
			rectParticles.add(r);
			Playground.getChildren().add(r);
		}
		AnimationTimer timer;
		timer= new AnimationTimer(){
			public void handle(long l){
				for(int a=0;a<rectParticles.size();a++){
					rectParticles.get(a).animate();
				}
				for(int a=0;a<rectParticles.size();a++){
					if(rectParticles.get(a).getOpacity()<=0){
						Playground.getChildren().remove(rectParticles.get(a));
						rectParticles.remove(a);
					}
				}
				if(rectParticles.size()<=0){
					this.stop();
				}
			}
		};
		timer.start();
	}
	
	public void CircleExplosion(double x, double y){
		ArrayList<CircleParticle> circleParticles = new ArrayList<CircleParticle>();
		for(int i=0;i<(int)Math.random()*50+175;i++){
			CircleParticle r = new CircleParticle();
			r.setLayoutX(x);
			r.setLayoutY(y);
			circleParticles.add(r);
			Playground.getChildren().add(r);
		}
		AnimationTimer timer;
		timer= new AnimationTimer(){
			public void handle(long l){
				for(int a=0;a<circleParticles.size();a++){
					circleParticles.get(a).animate();
				}
				for(int a=0;a<circleParticles.size();a++){
					if(circleParticles.get(a).getOpacity()<=0){
						Playground.getChildren().remove(circleParticles.get(a));
						circleParticles.remove(a);
					}
				}
				if(circleParticles.size()<=0){
					this.stop();
				}
			}
		};
		timer.start();
	}
	
}