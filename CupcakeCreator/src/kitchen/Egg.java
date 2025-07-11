package kitchen;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients egg
public class Egg extends Object {
	
	public Egg(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/eggs.png");
	}
	
}
