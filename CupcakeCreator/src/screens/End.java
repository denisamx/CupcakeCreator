package screens;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import util.ImageLoader;

// End class that displays the end screen image
public class End {
	private double xPos;
	private double yPos;
	private BufferedImage img;
	
	public End(double x, double y) {
		xPos = x;
		yPos = y;
		img = ImageLoader.loadImage("assets/images/end.png");
	}
	
	public void drawMe(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		
		g2.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, null);
		
		g2.setTransform(transform);
	}
}
