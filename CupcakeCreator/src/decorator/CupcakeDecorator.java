package decorator;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// abstract class that implements the CupcakeInterface 
public abstract class CupcakeDecorator implements CupcakeInterface {
	
	private CupcakeInterface baseCupcake;
	protected BufferedImage img;
	protected int xPos, yPos;
	protected double scale;
	
	public CupcakeDecorator(CupcakeInterface baseCupcake, int x, int y, double s) {
		this.baseCupcake = baseCupcake;
		xPos = x;
		yPos = y;
		scale = s;
	}
	
	//use base cupcake image
	public void decorate(Graphics2D g2) {
		baseCupcake.decorate(g2);
	}
	
	//collision detection method for mouse
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if(x > (xPos - ((double) img.getWidth())/2*scale) && x < (xPos + ((double) img.getWidth())/2*scale) && y > (yPos - ((double) img.getHeight())/2*scale) && y < (yPos + ((double) img.getHeight())/2*scale)) 
			clicked = true;
		
		return clicked;
	}
}
