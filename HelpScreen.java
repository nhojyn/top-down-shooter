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
import javafx.geometry.Point2D;

public class HelpScreen extends VBox{
	Text help;
	Stage stage;
	Scene main;
	Scene helpScreen;
	Button mainBtn;
	ArrayList<Text> helpList= new ArrayList<Text>();
	
	public HelpScreen(Stage s, Scene m){
		stage=s;
		main=m;
		helpScreen = new Scene(this, 1000, 1000);
		setSpacing(15);
		setStyle("-fx-background-color: black");
		Font f = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),20);
		Font f1 = Font.loadFont(getClass().getResourceAsStream("ARCADECLASSIC.ttf"),40);
		help = new Text("Controls");
		help.setFont(f1);
		helpList.add(help);
		help = new Text("Movement	-	WASD");
		help.setFont(f);
		helpList.add(help);
		help = new Text("Shoot	-	Left Click");
		help.setFont(f);
		helpList.add(help);
		help = new Text("Blink	-	Right Click");
		help.setFont(f);
		helpList.add(help);
		help = new Text("Number Keys	-	Switch Weapons");
		help.setFont(f);
		helpList.add(help);
		help = new Text("Goal");
		help.setFont(f1);
		helpList.add(help);
		help = new Text("Kill	as		many	enemies		as		possible	and		get		a		high	score");
		help.setFont(f);
		helpList.add(help);
		StackPane centered;
		for(int i=0;i<helpList.size();i++){
			centered=new StackPane();
			helpList.get(i).setFill(Color.WHITE);
			centered.getChildren().add(helpList.get(i));
			getChildren().add(centered);
		}
		mainBtn = new Button();
		mainBtn.setFont(f);
		mainBtn.setText("Main Menu");
		mainBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				stage.setScene(main);
			}
		});
		centered = new StackPane();
		centered.getChildren().add(mainBtn);
		getChildren().addAll(centered);
	}
	
	public void setToScene(){
		stage.setScene(helpScreen);
	}
}