package kitchen;
import util.ImageLoader;

/* subclass that displays an oven which will visually update when it's opened,
closed, or being used */
public class Oven extends Object {
	
	public Oven(double x, double y, double size) {
		super(x, y, size);
	}
	
	//changes the image to be displayed
	public void setOvenImg(int ovenState) {
		if (ovenState == 0)
			img = ImageLoader.loadImage("assets/images/closedOven.png");  
		else if (ovenState == 1)
			img = ImageLoader.loadImage("assets/images/openedOven.png");  
	}
}
