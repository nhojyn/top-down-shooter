import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

public class Laser extends MobProjectile{
	
	public Laser(double x, double y, double width, double height, Color color){
		xSpeed = 0;
		ySpeed = 0;
		shell = new Rectangle(x,y,width,height);
		shell.setFill(color);
		getChildren().add(shell);
	}
	
}