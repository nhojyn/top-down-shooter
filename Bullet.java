import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class Bullet extends Pane{
	double xSpeed;
	double ySpeed;
	Circle shell;
	int position;
	
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
	
	public void setPosition(int p){position = p;}
	public void setLocation(double x, double y){
		setLayoutX(x);
		setLayoutY(y);
	}
	public void setXSpeed(int x){xSpeed = x;}
	public void setYSpeed(int y){ySpeed = y;}
	
	public double getRadius(){
		return shell.getRadius();
	}
	public int getPosition(){return position;}
	public double getCenterX(){return shell.getCenterX();}
	public double getCenterY(){return shell.getCenterY();}
	
	public void move(){
		setLayoutX(getLayoutX() + xSpeed);
		setLayoutY(getLayoutY() + ySpeed);
	}

	
}