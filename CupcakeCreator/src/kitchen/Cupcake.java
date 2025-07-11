package kitchen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import decorator.CupcakeInterface;
import util.ImageLoader;

// class that displays a cupcake
public class Cupcake implements CupcakeInterface {
	private BufferedImage img;
	private int xPos, yPos;
	private double size;
	
	public Cupcake(int x, int y, double size) {
		img = ImageLoader.loadImage("assets/images/baseCupcake.png");
		xPos = x;
		yPos = y;
		this.size = size;
	}
	
	public void decorate(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(size, size);
		g2.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, null);
		g2.setTransform(at);
	}
}
