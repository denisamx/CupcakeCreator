package kitchen;
import static util.Util.random;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import processing.core.PApplet;
import util.ImageLoader;

// Kitchen class that displays a kitchen as the background for the simulation
public class Kitchen {
	private double xPos;
	private double yPos;
	private BufferedImage imgDaytime, imgNightTime;
	private BufferedImage counterImg;
	private BufferedImage finalImg;
	
	private float xStart;
	private float xSeed;
	private float ySeed;
	private PApplet pa;
	
	public Kitchen(double x, double y) {
		xPos = x;
		yPos = y;
		imgDaytime = ImageLoader.loadImage("assets/images/kitchenDaytime.png");
		imgNightTime = ImageLoader.loadImage("assets/images/kitchenNightTime.png");
		counterImg = ImageLoader.loadImage("assets/images/counter.png");
		finalImg = ImageLoader.loadImage("assets/images/finalResult.png");
		
		xStart = random(10);
		xSeed = xStart;
		xSeed = random(10);
		pa = new PApplet();
	}
	
	//draws the kitchen during day time
	public void drawMeDaytime(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		
		g2.drawImage(imgDaytime, -imgDaytime.getWidth()/2, -imgDaytime.getHeight()/2, null);
		
		g2.setTransform(transform);
		
	}
	
	//draws the kitchen during night time
	public void drawMeNightTime(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos+12);
		
		g2.drawImage(imgNightTime, -imgNightTime.getWidth()/2, -imgNightTime.getHeight()/2, null);
		
		g2.setTransform(transform);
		
	}
	
	//draws the counter
	public void drawCounter(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		
		g2.drawImage(counterImg, -counterImg.getWidth()/2, -counterImg.getHeight()/2, null);
		
		g2.setTransform(transform);
	}
	
	//draws the final cupcake
	public void drawFinalResult(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		
		g2.drawImage(finalImg, -finalImg.getWidth()/2, -finalImg.getHeight()/2, null);
		
		g2.setTransform(transform);
	}
	
	//draws the picture on the wall using fractals
	public void drawArt(Graphics2D g2, float x, float y, float d) {
		AffineTransform at = g2.getTransform();
		g2.translate(x, y);
		g2.setColor(new Color(166, 213, 239));
		g2.setStroke(new BasicStroke(6));
		g2.draw(new Ellipse2D.Float(-d/2, -d/2, d, d ));
		g2.setTransform(at);
		if (d  > 1) {
			 d *= 0.6; //shrink d by 60% each recursion
			 drawArt (g2, x+d, y, d );
			 drawArt (g2, x-d, y, d );
		}
	}
	
	//draw a cloud using perlin noise
	public void drawCloud(Graphics2D g2, float xPos, float yPos) {
		float noiseFactor;
		for (int y = 0; y <= 750; y += 5) {
			ySeed += 0.1;
			xSeed = xStart;
			for (int x = 0; x <= 950; x += 5) {
				xSeed += 0.2;
				noiseFactor = pa.noise(xSeed, ySeed);

				AffineTransform at = g2.getTransform();
				g2.translate(xPos, yPos);
				g2.rotate(noiseFactor * (360));
				float diameter = noiseFactor * 50;
				int grey = (int) (150 + (noiseFactor * 105));
				int alph = (int) (150 + (noiseFactor * 105));
				g2.setColor(new Color(grey, grey, grey, alph));
				g2.fill(new Ellipse2D.Float(-diameter/2, -diameter/4, diameter, diameter));
				g2.setTransform(at);
			}
		}
	}
}
