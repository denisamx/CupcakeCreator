package kitchen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients sugar
public class Sugar extends Object {
	
	public Sugar(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/sugar.png");
	}
	
}
