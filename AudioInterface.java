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
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AudioInterface{
	ArrayList<MediaPlayer> jukebox;
	MediaPlayer mediaPlayer;
	
	AudioInterface(){
		jukebox = new ArrayList<MediaPlayer>();
		String musicFile = "What is Love.mp3";
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(.2);
		jukebox.add(mediaPlayer);
	}
	
	public void playWIL(){
		if(jukebox.get(0)!=null){
			jukebox.get(0).play();
		}
	}
	
	public void stopAll(){
		for(int i=0; i<jukebox.size();i++){
			jukebox.get(i).stop();
		}
	}
}