package view;

import javafx.scene.paint.Color;
import model.brick.Brick;

/**
 * BrickView ist für das Colorieren der Bricks zuständig.
 * @author Niklas
 *
 */
public class BrickView {

	/**
	 * setColor setzt die Farbe eines Bricks brick je nachdem welchen removed Counter
	 * der jeweilige Brick besitzt.
	 * @param brick ist der Brick der coloriert werden soll.
	 * @param removedCounter ist der Wert des Bricks, wie oft dieser noch getroffen werden muss
	 * damit er von der Spieloberfläche verschwindet. Außer es ist ein Sonderbrick wie Z oder G. 
	 */
	public void setColor(Brick brick, int removedCounter) {
		switch(removedCounter){
			case 1:{
				brick.setFill(Color.RED);
				break;
			}
			case 2:{
				brick.setFill(Color.ORANGE);
				break;
			}
			case 3:{
				brick.setFill(Color.YELLOW);
				break;
			}
			case 4:{
				brick.setFill(Color.GREEN);
				break;
			}
			case 5:{
				brick.setFill(Color.BLUE);
				break;
			}
			case 6:{
				brick.setFill(Color.PURPLE);
				break;
			}
			case 7:{
				brick.setFill(Color.SLATEGREY);
				break;
			}
			case 8:{
				brick.setFill(Color.IVORY);
				break;
			}
		}
	}
}
