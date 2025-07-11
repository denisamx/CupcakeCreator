package buttons;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import util.ImageLoader;

// RestartButton class that allows the user to replay the simulation once clicked
public class RestartButton {
	private double xPos;
	private double yPos;
	private double size;
	private BufferedImage img;
	
	public RestartButton(double x, double y, double size) {
		xPos = x;
		yPos = y;
		this.size = size;
		img = ImageLoader.loadImage("assets/images/restartButton.png");
	}
	
	//draws the restart button
	public void drawMe(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(size, size);

		g2.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, null);

		g2.setTransform(transform);
	}
	
	//collision detection method
	public boolean clicked(double x, double y) { 
		boolean clicked = false;
		
		if(x > (xPos - ((double) img.getWidth())/2) && x < (xPos + ((double) img.getWidth())/2) && y > (yPos - ((double) img.getHeight())/2) && y < (yPos + ((double) img.getHeight())/2)) 
			clicked = true;
		
		return clicked;
	}
}
