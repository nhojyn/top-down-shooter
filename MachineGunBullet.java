import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class MachineGunBullet extends Bullet{
	
	public MachineGunBullet(double xS, double yS){
		super(xS, yS);
		speedMultiplier = .7;
		damage = 1;
		shell = new Circle(8);
		shell.setFill(Color.PINK);
		getChildren().add(shell);
		setPrefSize(10,10);
		shell.setCenterX(10);
		shell.setCenterY(10);
		
		explosionTimer = null;
	}

	

}