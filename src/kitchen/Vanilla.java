package kitchen;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients vanilla
public class Vanilla extends Object {
	public Vanilla(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/vanilla.png");
	}
	
}
