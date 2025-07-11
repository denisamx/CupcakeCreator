package kitchen;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/* abstract superclass for all kitchen items which handles 
drawing them, detecting collisions, etc. */
public abstract class Object {
	protected double xPos;
	protected double yPos;
	protected double size;
	protected BufferedImage img;
	
	public Object(double x, double y, double size) {
		xPos = x;
		yPos = y;
		this.size = size;
	}
	
	//draws the kitchen item
	public void drawMe(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(size, size);

		g2.drawImage(img, -img.getWidth()/2, -img.getHeight()/2, null);

		g2.setTransform(transform);
	}
	
	//collision detection between objects
	public boolean hit(Object object) {
		boolean hit = false;

		if (Math.abs(xPos - object.getXPos()) < 50 && Math.abs(yPos- object.getYPos()) < 30)
			hit = true;
		
		return hit;
	}
	
	//collision detection for mouse
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if(x > (xPos - ((double) img.getWidth())/2*size) && x < (xPos + ((double) img.getWidth())/2*size) && y > (yPos - ((double) img.getHeight())/2*size) && y < (yPos + ((double) img.getHeight())/2*size)) 
			clicked = true;
		
		return clicked;
	}
	
	//returns object's x-axis position
	public double getXPos(){
		return xPos;
	}
	
	//returns object's y-axis position
	public double getYPos(){
		return yPos;
	}
	
	//sets the object's x-axis position
	public void setXPos(double x){
		xPos = x;
	}
	
	//sets the object's y-axis position
	public void setYPos(double y){
		yPos = y;
	}
}
