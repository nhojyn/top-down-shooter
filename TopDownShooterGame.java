
import javafx.application.Application;
import javafx.stage.Stage;

public class TopDownShooterGame extends Application{
	public static void main( String[] args ){
	  launch( args ) ;
	}
	@Override
	public void start(Stage stage){
		TopDownShooter game = new TopDownShooter(stage);
		game.play();
		stage.show();
	}
}