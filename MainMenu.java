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
	Button mainBtn,audioBtn,highScore1,helpBtn;
	AudioInterface AI;
	Rectangle fade;
	HighScores highScores;
	HighScoreScreen highScoreScreen1;
	HelpScreen helpScreen;
	int counter;
	String s1;
	
	Button playGame;
	
	MainMenu(Stage s){
		title = new Pane();
		main = new Scene(title, 1000, 1000);
		
		highScores = new HighScores();
		highScores.readHighScores();
		
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
		highScore1 = new Button();
		helpBtn = new Button();
		
		highScoreScreen1= new HighScoreScreen(highScores, s,main);
		helpScreen = new HelpScreen(s, main);
		
		game= new TopDownShooter(stage,mainBtn,this,highScores);
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
		
		highScore1.setFont(f1);
		highScore1.setText("High Scores");
		highScore1.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				highScoreScreen1.updateHighScores();
				highScoreScreen1.setToScene();
			}
		});
		
		helpBtn.setFont(f1);
		helpBtn.setText("Help");
		helpBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				helpScreen.setToScene();
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
		title.getChildren().addAll(playGame,titleTxt,audioBtn,highScore1,helpBtn);
		stage.setScene( main );
		stage.show();
		
		int spacing=10;
		
		audioBtn.setLayoutX(title.getWidth()/2-audioBtn.getLayoutBounds().getWidth()/2);
		audioBtn.setLayoutY(title.getHeight()/2+audioBtn.getLayoutBounds().getHeight()+spacing);
		
		highScore1.setLayoutX(title.getWidth()/2-highScore1.getLayoutBounds().getWidth()/2);
		highScore1.setLayoutY(title.getHeight()/2+highScore1.getLayoutBounds().getHeight()+audioBtn.getLayoutBounds().getHeight()+spacing*2);
		
		helpBtn.setLayoutX(title.getWidth()/2-helpBtn.getLayoutBounds().getWidth()/2);
		helpBtn.setLayoutY(title.getHeight()/2+helpBtn.getLayoutBounds().getHeight()+audioBtn.getLayoutBounds().getHeight()+highScore1.getLayoutBounds().getHeight()+spacing*3);
		
		playGame.setLayoutX(title.getWidth()/2-playGame.getLayoutBounds().getWidth()/2);
		playGame.setLayoutY(title.getHeight()/2);
		
		//titleTxt.applyCss();
		titleTxt.setLayoutX(title.getWidth()/2-titleTxt.getLayoutBounds().getWidth()/2);
		titleTxt.setLayoutY(title.getHeight()/2-titleTxt.getLayoutBounds().getHeight());
		
		ArrayList<String> code = new ArrayList<String>();
		String correctCode= "nickng589";
		counter=0;
		s1="";
		main.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
				if(correctCode.substring(counter,counter+1).equals(event.getText())){
					code.add(event.getText());
					counter++;
				}else{
					code.clear();
					counter=0;
				}
				s1="";
				for(int i=0;i<code.size();i++){
					s1+=code.get(i);
				}
				if(s1.equals(correctCode)){
					System.out.println("DEV TOOLS UNLOCKED");
					code.clear();
					counter=0;
					game.unlockDevTools();
				}
            }
        });
	}
	
	public void fadeIn(){
		title.getChildren().add(fade);
		fade.setOpacity(1);
		stage.setScene( main );
		Timeline fadeAni = new Timeline(new KeyFrame(Duration.millis(50),ae -> fade.setOpacity(fade.getOpacity()-0.02)));
		fadeAni.setCycleCount(50);
		fadeAni.play();
		fadeAni.setOnFinished(e -> title.getChildren().remove(fade));
	}
}