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

public class MainMenu{
	Stage stage;
	TopDownShooter game;
	Pane title;
	Scene main;
	Button mainBtn;
	Button audioBtn;
	AudioInterface AI;
	Rectangle fade;
	
	Button playGame;
	
	MainMenu(Stage s){
		title = new Pane();
		main = new Scene(title, 1000, 1000);
		
		fade = new Rectangle(main.getWidth(),main.getHeight());
		fade.setFill(Color.BLACK);
		fade.setOpacity(0);
		
		AI = new AudioInterface();
		stage=s;
		stage.setTitle("TopDownShooter");
		stage.getIcons().add(new Image("pixel-heart.png"));
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		mainBtn = new Button();
		audioBtn = new Button();
		
		game= new TopDownShooter(stage,mainBtn,this);
		playGame = new Button();
		playGame.setFont(f1);
		playGame.setText("Play Game");
		playGame.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				game.play();
			}
		});
		
		audioBtn.setFont(f1);
		audioBtn.setText("Play Music");
		audioBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				AI.playWIL();
			}
		});
		
		mainBtn.setFont(f1);
		mainBtn.setText("Main Menu");
		mainBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				game.reset();
				stage.setScene(main);
			}
		});
		
		Font f2 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),200);
		Text titleTxt = new Text("Game");
		titleTxt.setFont(f2);
		titleTxt.setFill(Color.WHITE);
		
		title.setStyle("-fx-background-color: black");
		title.getChildren().addAll(playGame,titleTxt,audioBtn);
		stage.setScene( main );
		stage.show();
		
		int spacing=10;
		
		audioBtn.setLayoutX(title.getWidth()/2-audioBtn.getLayoutBounds().getWidth()/2);
		audioBtn.setLayoutY(title.getHeight()/2+audioBtn.getLayoutBounds().getHeight()+spacing);
		
		playGame.setLayoutX(title.getWidth()/2-playGame.getLayoutBounds().getWidth()/2);
		playGame.setLayoutY(title.getHeight()/2);
		
		//titleTxt.applyCss();
		titleTxt.setLayoutX(title.getWidth()/2-titleTxt.getLayoutBounds().getWidth()/2);
		titleTxt.setLayoutY(title.getHeight()/2-titleTxt.getLayoutBounds().getHeight());
	}
	
	public void fadeIn(){
		title.getChildren().add(fade);
		stage.setScene( main );
		fade.setOpacity(1);
		Timeline fadeAni = new Timeline(new KeyFrame(Duration.millis(50),ae -> fade.setOpacity(fade.getOpacity()-0.02)));
		fadeAni.setCycleCount(50);
		fadeAni.play();
		fadeAni.setOnFinished(e -> title.getChildren().remove(fade));
	}
}