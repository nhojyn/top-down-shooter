import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
public class MachineGunBullet extends Bullet{
	
	public MachineGunBullet(double xS, double yS){
		super(xS, yS);
		speedMultiplier = .35;
		damage = 2;
		shell = new Circle(6);
		shell.setFill(Color.PINK);
		getChildren().add(shell);
		setPrefSize(10,10);
		shell.setCenterX(10);
		shell.setCenterY(10);
		
		explosionTimer = null;
	}

	

}