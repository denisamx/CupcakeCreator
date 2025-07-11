package decorator;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import util.ImageLoader;

// subclass that decorates the cupcake with sprinkles 
public class SprinklesDecorator extends CupcakeDecorator {
	
	public SprinklesDecorator(CupcakeInterface cupcake, int x, int y, double s) {
		super(cupcake, x, y, s);
		img = ImageLoader.loadImage("assets/images/sprinkles.png");
	}
	
	//call super class's decorate method and add sprinkle method on top
	public void decorate(Graphics2D g2) {
		super.decorate(g2);
		decorateWithSprinkles(g2);
	}
	
	//display sprinkles image
	private void decorateWithSprinkles(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		
		g2.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, null);
		
		g2.setTransform(at);
	}
}
