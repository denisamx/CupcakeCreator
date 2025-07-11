package kitchen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients flour
public class Flour extends Object {
	
	public Flour(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/flour.png");
	}
	
}
