import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Tester extends Application{
	public static void main( String[] args ){
	  launch( args ) ;
	}
	@Override
	public void start(Stage stage){
		Pane root = new Pane();
		Bullet b = new Bullet();
		
		root.getChildren().add(b);
		b.setLayoutX(500);
		b.setLayoutY(500);
		Scene scene1 = new Scene(root,1000,600);

		stage.setScene(scene1);
		stage.show();
	}
}