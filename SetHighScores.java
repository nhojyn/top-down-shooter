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
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.*;

public class SetHighScores{
	Font f = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
	ArrayList<String> initials = new ArrayList<String>();
	TextFlow tf = new TextFlow();
	Stage stage;
	Scene scene;
	HighScores highScores;
	MainMenu mainMenu;
	Pane screen;
	Text t;
	Text t2;
	Rectangle fade;
	
	public SetHighScores(Stage s, HighScores hs,MainMenu mm){
		stage=s;
		highScores=hs;
		mainMenu=mm;
		screen = new Pane();
		screen.setPrefWidth(1000);
		screen.setPrefHeight(1000);
		screen.setStyle("-fx-background-color: black;");
		t = new Text("New    High    Score!");
		t.setFont(f);
		t.setFill(Color.WHITE);
		t2 = new Text("Type     in     3     character     initials");
		t2.setFont(f);
		t2.setFill(Color.WHITE);
		tf=new TextFlow();
		fade = new Rectangle(screen.getPrefWidth(), screen.getPrefHeight());
		fade.setFill(Color.BLACK);
		fade.setOpacity(0);
		scene = new Scene(screen, 1000, 1000);
	}
	
	public void displaySetHighScores(int score){
		reset();
		stage.setScene(scene);
		screen.getChildren().addAll(t,t2,tf);
		t2.setLayoutX(screen.getWidth()/2-t2.getLayoutBounds().getWidth()/2);
		t2.setLayoutY(screen.getHeight()/2-t2.getLayoutBounds().getHeight()/2+t2.getLayoutBounds().getHeight());
		t.setLayoutX(screen.getWidth()/2-t.getLayoutBounds().getWidth()/2);
		t.setLayoutY(screen.getHeight()/2-t.getLayoutBounds().getHeight()/2);
		tf.setLayoutX(screen.getWidth()/2-tf.getLayoutBounds().getWidth()/2);
		tf.setLayoutY(screen.getHeight()/2-tf.getLayoutBounds().getHeight()/2+t2.getLayoutBounds().getHeight()+t.getLayoutBounds().getHeight());
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
				if(!event.getCode().equals(KeyCode.BACK_SPACE)&&!event.getCode().equals(KeyCode.ENTER)&&initials.size()<3){
					initials.add(event.getText());
					Text t = new Text(event.getText());
					Font f = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),30);
					t.setFont(f);
					t.setFill(Color.WHITE);
					tf.getChildren().add(t);
					move();
				}else if(event.getCode().equals(KeyCode.BACK_SPACE)&&initials.size()>=1){
					tf.getChildren().remove(initials.size()-1);
					initials.remove(initials.size()-1);
					move();
				}else if(event.getCode().equals(KeyCode.ENTER)&&initials.size()==3){
					if(!screen.getChildren().contains(fade)){
						save(score);
						screen.getChildren().add(fade);
						Timeline fadeAni = new Timeline(new KeyFrame(Duration.millis(30),ae -> animateFade()));
						fadeAni.setCycleCount(50);
						fadeAni.play();
						fadeAni.setOnFinished(e -> goToMain());
					}
				}
            }
        });
	}
	
	public void move(){
		tf.setLayoutX(screen.getWidth()/2-tf.getWidth()/2);
	}
	
	private void animateFade(){
		fade.setOpacity(fade.getOpacity()+0.02);
	}
	
	public void goToMain(){
		screen.getChildren().remove(fade);
		mainMenu.fadeIn();
	}
	
	public void save(int score){
		String name ="";
		for(int i=0;i<initials.size();i++){
			name+=initials.get(i);
		}
		for(int i=0;i<highScores.getHighScoreNums().length;i++){
			if(highScores.getHighScoreNums()[i]< score){
				for(int a=highScores.getHighScoreNums().length-1;a>i;a--){
					highScores.getHighScoreNums()[a]=highScores.getHighScoreNums()[a-1];
					highScores.getHighScoreNames()[a]=highScores.getHighScoreNames()[a-1];
				}
				highScores.getHighScoreNums()[i]=score;
				highScores.getHighScoreNames()[i]=name;
				break;
			}
		}
		highScores.saveHighScores();
		
	}
	
	public void reset(){
		initials.clear();
		tf.getChildren().clear();
		screen.getChildren().clear();
	}
}