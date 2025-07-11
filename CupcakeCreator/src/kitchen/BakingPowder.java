package kitchen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.CupcakePanel;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients baking powder
public class BakingPowder extends Object {
	
	public BakingPowder(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/bakingPowder.png");
	}
	
}
