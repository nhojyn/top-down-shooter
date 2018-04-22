import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class Bullet extends Pane{

	public Bullet(){
		Circle shell = new Circle(10);
		shell.setFill(Color.BLACK);
		getChildren().add(shell);
		setStyle("-fx-background-color: blue;");
		setPrefSize(20,20);
		shell.setCenterX(10);
		shell.setCenterY(10);
		
	}

	public void setLocation(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	public void move(double xSpeed, double ySpeed){
		setLayoutX(getLayoutX() + xSpeed);
		setLayoutY(getLayoutY() + ySpeed);
	}
}