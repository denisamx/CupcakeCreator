package kitchen;
import util.ImageLoader;

// subclass that displays one of the cooking ingredients butter
public class Butter extends Object {
	
	public Butter(double x, double y, double size) {
		super(x, y, size);
		img = ImageLoader.loadImage("assets/images/butter.png");
	}
	
}
