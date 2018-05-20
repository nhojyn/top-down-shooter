import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

public class MobBullet extends MobProjectile{

	public MobBullet(double x, double y, double radius, double xv, double yv, Color color){
		xSpeed = xv;
		ySpeed = yv;
		shell = new Circle(x,y,radius);
		shell.setFill(color);
		getChildren().add(shell);
	}

	public double getRadius(){
		return ((Circle)shell).getRadius();
	}
}
