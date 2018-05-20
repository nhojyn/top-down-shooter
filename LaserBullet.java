import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

public class LaserBullet extends MobProjectile{

	public LaserBullet(double x, double y, double width, double height, Color color, double xv, double yv){
		xSpeed = xv;
		ySpeed = yv;
		shell = new Rectangle(x,y,width,height);
		shell.setFill(color);
		getChildren().add(shell);
	}

}
