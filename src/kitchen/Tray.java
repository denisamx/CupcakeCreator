package kitchen;
import java.awt.Graphics2D;

import util.ImageLoader;

// subclass that displays a tray to place the batter in
public class Tray extends Object {
	
	public Tray(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/emptyTray.png");
	}
	
	public void setTrayImg(int trayState) {
		if (trayState == 0)
			img = ImageLoader.loadImage("assets/images/emptyTray.png");  
		else if (trayState == 1)
			img = ImageLoader.loadImage("assets/images/filledTray.png");   
		else if (trayState == 2)
			img = ImageLoader.loadImage("assets/images/frontTray.png");   
	}
}
