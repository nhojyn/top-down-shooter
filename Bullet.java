import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class Bullet extends Pane{
	double xSpeed;
	double ySpeed;
	Circle shell;
	
	public Bullet(double xS, double yS){
		shell = new Circle(10);
		shell.setFill(Color.BLACK);
		getChildren().add(shell);
		setPrefSize(20,20);
		shell.setCenterX(10);
		shell.setCenterY(10);
		xSpeed=xS;
		ySpeed=yS;
	}

	public void setLocation(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	public void move(){
		setLayoutX(getLayoutX() + xSpeed);
		setLayoutY(getLayoutY() + ySpeed);
	}
	
	public double getRadius(){
		return shell.getRadius();
	}
	public double getCenterX(){return shell.getCenterX();}
	public double getCenterY(){return shell.getCenterY();}
	

}