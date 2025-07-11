package kitchen;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients milk
public class Milk extends Object {
	
	public Milk(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/milk.png");
	}
	
}
