package kitchen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import util.ImageLoader;

/* subclass that displays a mixing bowl which will visually update as more ingredients
are added into it */
public class Bowl extends Object {
	
	public Bowl(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/bowl.png");
	}
	
	//changes the image to be displayed
	public void setBowlImg(int bowlState) {
		if (bowlState == 0)
			img = ImageLoader.loadImage("assets/images/bowl.png");  
		else if (bowlState == 1)
			img = ImageLoader.loadImage("assets/images/emptyBowl.png");   
		else if (bowlState == 2)
			img = ImageLoader.loadImage("assets/images/flourBowl.png");   
		else if (bowlState == 3)
			img = ImageLoader.loadImage("assets/images/bakingPowderBowl.png");
		else if (bowlState == 4)
			img = ImageLoader.loadImage("assets/images/eggBowl.png");
		else if (bowlState == 5)
			img = ImageLoader.loadImage("assets/images/butterBowl.png");
		else if (bowlState == 6)
			img = ImageLoader.loadImage("assets/images/vanillaBowl.png");
		else if (bowlState == 7)
			img = ImageLoader.loadImage("assets/images/milkBowl.png");
		else if (bowlState == 8)
			img = ImageLoader.loadImage("assets/images/mixedBowl.png");
	}

}
