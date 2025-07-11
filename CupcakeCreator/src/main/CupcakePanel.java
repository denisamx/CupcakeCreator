package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import buttons.RestartButton;
import buttons.StartButton;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import decorator.CupcakeInterface;
import decorator.FrostingDecorator;
import decorator.SprinklesDecorator;
import factory.ObjectConcreteFactory;
import kitchen.BakingPowder;
import kitchen.Bowl;
import kitchen.Butter;
import kitchen.Cupcake;
import kitchen.Egg;
import kitchen.Flour;
import kitchen.Kitchen;
import kitchen.Milk;
import kitchen.Oven;
import kitchen.Sugar;
import kitchen.Tray;
import kitchen.Vanilla;
import screens.End;
import screens.Intro;
import util.MinimHelper;

/* main panel that handles input from the user, instantiates objects, controls states, and draws everything
ECO: - all images are created by me using Figma: https://www.figma.com/file/Wz2jtJqmMK32xnYmsrkRuU/IAT265-A4?node-id=0%3A1
- complex shapes such as perlin noise and fractals were used in the kitchen background 
- multiple states used
- day/night shifting
- used decorator pattern */
public class CupcakePanel extends JPanel implements ActionListener {
	public static int PAN_WIDTH = 950;
	public static int PAN_HEIGHT = 750;
	
	private double mouseX; 
	private double mouseY;
	
	//states
	private int state = 0;
	private static final int GET_BOWL = 1;
	private static final int ADD_FLOUR = 2;
	private static final int ADD_BP = 3;
	private static final int ADD_SUGAR = 4;
	private static final int ADD_EGGS = 5;
	private static final int ADD_BUTTER = 6;
	private static final int ADD_VANILLA = 7;
	private static final int ADD_MILK = 8;
	private static final int MIX = 9;
	private static final int MIXED = 10;
	private static final int BAKE = 11;
	private static final int KITCHEN = 12;
	private static final int OPEN_OVEN = 13;
	private static final int BAKING = 14;
	private static final int BAKING_DONE = 15;
	private static final int LETS_DECORATE = 16;
	private static final int DECORATE = 17;
	private static final int DECORATE_COMPLETE = 18;
	private static final int DONE = 19;
	
	//objects and arraylists
	private ObjectConcreteFactory factory;
	private Intro intro;
	private End end;
	private StartButton startButton;
	private RestartButton restartButton;
	private Kitchen kitchen;
	private Cupcake baseCupcake;
	private Object bowl, oven, flour, bakingPowder, sugar, egg, milk,
	vanilla, butter, tray;
	private ArrayList<Object> objects; //arraylist for kitchen items
	private CupcakeInterface cupcake;
	
	//timers
	private Timer timer;
	private int ovenTimer = 0;
	private int addDecorTextTimer = 0;
	private int finalCupcakeTimer = 0;
	
	private Font font;
	
	//audio
	private Minim minim;
	private AudioPlayer bgMusic, eggCrack, pourMilk, pourVanilla, powder,
	addButter, openOven, ovenDone, baking, glitter, stir;
	
	public CupcakePanel(JFrame frame) {
		setPreferredSize(new Dimension(PAN_WIDTH, PAN_HEIGHT));
		this.setBackground(new Color(255, 199, 158));
		font = new Font("Pompiere", Font.PLAIN, 28);
		
		//audio
		minim = new Minim(new MinimHelper());
		bgMusic = minim.loadFile("themeSong.mp3");
		eggCrack = minim.loadFile("eggCrack.mp3");
		pourMilk = minim.loadFile("milk.mp3");
		pourVanilla = minim.loadFile("liquid.mp3");
		powder = minim.loadFile("powder.mp3");
		addButter = minim.loadFile("butter.mp3");
		openOven = minim.loadFile("openOven.mp3");
		ovenDone = minim.loadFile("timerDone.mp3");
		baking = minim.loadFile("baking.mp3");
		glitter = minim.loadFile("glitter.mp3");
		stir = minim.loadFile("stir.mp3");
		
		//instantiate screens, buttons and bg image
		intro = new Intro(PAN_WIDTH/2, 375);
		end = new End(PAN_WIDTH/2, 375);
		startButton = new StartButton(PAN_WIDTH/2, PAN_HEIGHT/2+200, 1);
		restartButton = new RestartButton(PAN_WIDTH/2, PAN_HEIGHT/2+120, 1);
		kitchen = new Kitchen(PAN_WIDTH/2, 375);
		baseCupcake = new Cupcake(PAN_WIDTH/2-50, PAN_HEIGHT/2, 1);
		cupcake = new Cupcake(PAN_WIDTH/2, PAN_HEIGHT/2+10, 1);
		cupcake = new FrostingDecorator((CupcakeInterface) cupcake, PAN_WIDTH/2, PAN_HEIGHT/2-120, 1);
		cupcake = new SprinklesDecorator((CupcakeInterface) cupcake, PAN_WIDTH/2-10, PAN_HEIGHT/2-50, 1);
		
		//create objects from the factory and add them to the arraylist objects
		factory = new ObjectConcreteFactory();
		objects = new ArrayList<Object>();
		
		bowl = factory.createObject("bowl");
		objects.add(bowl);
		oven = factory.createObject("oven");
		objects.add(oven);
		flour = factory.createObject("flour");
		objects.add(factory.createObject("flour"));
		bakingPowder = factory.createObject("bakingPowder");
		objects.add(factory.createObject("bakingPowder"));
		sugar = factory.createObject("sugar");
		objects.add(factory.createObject("sugar"));
		egg = factory.createObject("egg");
		objects.add(factory.createObject("egg"));
		milk = factory.createObject("milk");
		objects.add(factory.createObject("milk"));
		vanilla = factory.createObject("vanilla");
		objects.add(factory.createObject("vanilla"));
		butter = factory.createObject("butter");
		objects.add(factory.createObject("butter"));
		tray = factory.createObject("tray");
		objects.add(factory.createObject("tray"));
		
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		MyMouseMotionListener mml = new MyMouseMotionListener();
		addMouseMotionListener(mml);
		
		timer = new Timer(30, this);
		timer.start();
	}
	
	//draws all objects
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		states(g2);
	}
	
	//handles all actions
	@Override
	public void actionPerformed(ActionEvent e) {
		
		repaint();
	}
	
	public class MyMouseListener extends MouseAdapter {
		
		//handles mouse clicking events
		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();

			if (state == 0 && startButton.clicked(mouseX, mouseY)) {
				state = GET_BOWL;
				bgMusic.loop(); //play the bg music on repeat
			}
			
			if (state == 1 && ((Bowl) bowl).clicked(mouseX, mouseY)) {
				state = ADD_FLOUR;
			}
			
			if (state == MIX && ((Bowl) bowl).clicked(mouseX, mouseY)) {
				state = MIXED;
			}
			
			if (state == BAKE && ((Tray) tray).clicked(mouseX, mouseY)) {
				state = KITCHEN;
			}
			
			if (state == KITCHEN && ((Oven) oven).clicked(mouseX, mouseY)) {
				openOven.play(0);
				state = OPEN_OVEN;
			}
			
			if (state == OPEN_OVEN && ((Tray) tray).clicked(mouseX, mouseY)) {
				baking.play(0);
				state = BAKING;
			}
			
			if (state == BAKING_DONE && ((Tray) tray).clicked(mouseX, mouseY)) {
				state = LETS_DECORATE;
			}
			
			if (state == DONE && restartButton.clicked(mouseX, mouseY)) {
				new CupcakeApp("New CupcakeApp"); //launch the game again
			}
		}
	}
	
	public class MyMouseMotionListener extends MouseMotionAdapter {
		
		//handles mouse dragged events
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();

			if (state == ADD_FLOUR) {
				((Flour) flour).setXPos(mouseX);
				((Flour) flour).setYPos(mouseY);
				if (((Flour) flour).hit((kitchen.Object) bowl)) {
					powder.play(0);
					((Bowl) bowl).setBowlImg(1);
					state = ADD_BP;
				} 
			} 
			
			if (state == ADD_BP) {
				if (((BakingPowder) bakingPowder).clicked(mouseX, mouseY))
					((BakingPowder) bakingPowder).setXPos(mouseX);
					((BakingPowder) bakingPowder).setYPos(mouseY);
					if (((BakingPowder) bakingPowder).hit((kitchen.Object) bowl)) {
						powder.play(0);
						((Bowl) bowl).setBowlImg(3);
						state = ADD_SUGAR;
					}
			}
			
			if (state == ADD_SUGAR) {
				if (((Sugar) sugar).clicked(mouseX, mouseY))
					((Sugar) sugar).setXPos(mouseX);
					((Sugar) sugar).setYPos(mouseY);
					if (((Sugar) sugar).hit((kitchen.Object) bowl)) {
						powder.play(0);
						((Bowl) bowl).setBowlImg(3);
						state = ADD_EGGS;
					}
			}
			
			if (state == ADD_EGGS) {
				if (((Egg) egg).clicked(mouseX, mouseY))
					((Egg) egg).setXPos(mouseX);
					((Egg) egg).setYPos(mouseY);
					if (((Egg) egg).hit((kitchen.Object) bowl)) {
						eggCrack.play(0);
						((Bowl) bowl).setBowlImg(4);
						state = ADD_BUTTER;
					}
			}
			
			if (state == ADD_BUTTER) {
				if (((Butter) butter).clicked(mouseX, mouseY))
					((Butter) butter).setXPos(mouseX);
					((Butter) butter).setYPos(mouseY);
					if (((Butter) butter).hit((kitchen.Object) bowl)) {
						addButter.play(0);
						((Bowl) bowl).setBowlImg(5);
						state = ADD_VANILLA;
					}
			}
			
			if (state == ADD_VANILLA) {
				if (((Vanilla) vanilla).clicked(mouseX, mouseY))
					((Vanilla) vanilla).setXPos(mouseX);
					((Vanilla) vanilla).setYPos(mouseY);
					if (((Vanilla) vanilla).hit((kitchen.Object) bowl)) {
						pourVanilla.play(0);
						((Bowl) bowl).setBowlImg(6);
						state = ADD_MILK;
					}
			}
			
			if (state == ADD_MILK) {
				if (((Milk) milk).clicked(mouseX, mouseY))
					((Milk) milk).setXPos(mouseX);
					((Milk) milk).setYPos(mouseY);
					if (((Milk) milk).hit((kitchen.Object) bowl)) {
						pourMilk.play(0);
						((Bowl) bowl).setBowlImg(7);
						state = MIX;
						stir.play(0);
					}
			}
			
			if (state == MIXED) {
				if (((Bowl) bowl).clicked(mouseX, mouseY)) {
					((Bowl) bowl).setXPos(mouseX);
					((Bowl) bowl).setYPos(mouseY);
					if (((Bowl) bowl).hit((Tray) tray)) {
						((Tray) tray).setTrayImg(1);
						state = BAKE;
					}
				}
			}

			repaint();
		}
	}
	
	//states for simulation
	public void states(Graphics2D g2) {
		if (state == 0) {
			intro.drawMe(g2);
			startButton.drawMe(g2);
		} else if (state == GET_BOWL) {
			kitchen.drawMeDaytime(g2);
			kitchen.drawArt(g2, PAN_WIDTH/2-288, PAN_HEIGHT/3-155, 60);  
			kitchen.drawCloud(g2, 150, 250);
			kitchen.drawCloud(g2, 180, 270);
			kitchen.drawCloud(g2, 130, 270);
			((Bowl) bowl).setBowlImg(0);
			((Bowl) bowl).drawMe(g2);
			((Oven) oven).setOvenImg(0);
			((Oven) oven).drawMe(g2);
			text("Click on the bowl", 35, PAN_HEIGHT-90, g2);
		} else if (state == ADD_FLOUR) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setXPos(PAN_WIDTH/2+180);
			((Bowl) bowl).setYPos(PAN_HEIGHT/2);
			((Bowl) bowl).setBowlImg(1);
			((Bowl) bowl).drawMe(g2);
			((Flour) flour).drawMe(g2);
			text("Add flour to the mixing bowl", 35, 60, g2);
		} else if (state == ADD_BP) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(2);
			((Bowl) bowl).drawMe(g2);
			((BakingPowder) bakingPowder).drawMe(g2);
			text("Add some baking powder", 35, 60, g2);
		} else if (state == ADD_SUGAR) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(3);
			((Bowl) bowl).drawMe(g2);
			((Sugar) sugar).drawMe(g2);
			text("Let's add sugar", 35, 60, g2);
		} else if (state == ADD_EGGS) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(3);
			((Bowl) bowl).drawMe(g2);
			((Egg) egg).drawMe(g2);
			text("Add 2 eggs", 35, 60, g2);
		} else if (state == ADD_BUTTER) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(4);
			((Bowl) bowl).drawMe(g2);
			((Butter) butter).drawMe(g2);
			text("Add some butter", 35, 60, g2);
		} else if (state == ADD_VANILLA) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(5);
			((Bowl) bowl).drawMe(g2);
			((Vanilla) vanilla).drawMe(g2);
			text("Let's add a bit of vanilla", 35, 60, g2);
		} else if (state == ADD_MILK) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(6);
			((Bowl) bowl).drawMe(g2);
			((Milk) milk).drawMe(g2);
			text("Add milk", 35, 60, g2);
		} else if (state == MIX) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(7);
			((Bowl) bowl).drawMe(g2);
			text("Finally, let's mix it all together!", 35, 60, g2);
		} else if (state == MIXED) {
			kitchen.drawCounter(g2);
			((Bowl) bowl).setBowlImg(8);
			((Bowl) bowl).drawMe(g2);
			((Tray) tray).setTrayImg(0);
			((Tray) tray).drawMe(g2);
			text("Pour the batter in the tray", 35, 60, g2);
		} else if (state == BAKE) {
			kitchen.drawCounter(g2);
			((Tray) tray).setTrayImg(1);
			((Tray) tray).drawMe(g2);
			text("Let's put it in the oven!", 35, 60, g2);
		} else if (state == KITCHEN) {
			kitchen.drawMeNightTime(g2);
			kitchen.drawArt(g2, PAN_WIDTH/2-288, PAN_HEIGHT/3-155, 60); 
			kitchen.drawCloud(g2, 150, 250);
			kitchen.drawCloud(g2, 180, 270);
			kitchen.drawCloud(g2, 130, 270);
			((Oven) oven).setOvenImg(0);
			((Oven) oven).drawMe(g2);
			((Tray) tray).setTrayImg(2);
			((Tray) tray).setYPos(400);
			((Tray) tray).drawMe(g2);
			text("Open the oven", 35, PAN_HEIGHT-90, g2);
		} else if (state == OPEN_OVEN) {
			kitchen.drawMeNightTime(g2);
			kitchen.drawArt(g2, PAN_WIDTH/2-288, PAN_HEIGHT/3-155, 60); 
			kitchen.drawCloud(g2, 150, 250);
			kitchen.drawCloud(g2, 180, 270);
			kitchen.drawCloud(g2, 130, 270);
			((Oven) oven).setOvenImg(1);
			((Oven) oven).drawMe(g2);
			((Tray) tray).setTrayImg(2);
			((Tray) tray).setYPos(400);
			((Tray) tray).drawMe(g2);
			text("Click the tray to place it in the oven", 35, PAN_HEIGHT-90, g2);
		} else if (state == BAKING) {
			kitchen.drawMeNightTime(g2);
			kitchen.drawArt(g2, PAN_WIDTH/2-288, PAN_HEIGHT/3-155, 60); 
			kitchen.drawCloud(g2, 150, 250);
			kitchen.drawCloud(g2, 180, 270);
			kitchen.drawCloud(g2, 130, 270);
			((Oven) oven).setOvenImg(0);
			((Oven) oven).drawMe(g2);
			text("Waiting for it to cook...", 35, PAN_HEIGHT-90, g2);
			ovenTimer++;
			System.out.println("Seconds passed: " + ovenTimer);
			if(ovenTimer == 5) {
				baking.pause();
				ovenDone.play(0);
				state = BAKING_DONE; //after 5s passed, baking is done
				ovenTimer = 5;
			}
		} else if (state == BAKING_DONE) {
			kitchen.drawMeNightTime(g2);
			kitchen.drawArt(g2, PAN_WIDTH/2-288, PAN_HEIGHT/3-155, 60); 
			kitchen.drawCloud(g2, 150, 250);
			kitchen.drawCloud(g2, 180, 270);
			kitchen.drawCloud(g2, 130, 270);
			((Oven) oven).setOvenImg(1);
			((Oven) oven).drawMe(g2);
			((Tray) tray).setTrayImg(2);
			((Tray) tray).setXPos(625);
			((Tray) tray).setYPos(320);
			((Tray) tray).drawMe(g2);
			text("All done! Click the tray to continue", 35, PAN_HEIGHT-90, g2);
		} else if (state == LETS_DECORATE) {
			kitchen.drawCounter(g2);
			text("Let's add some frosting and sprinkles", 35, 60, g2);
			((Cupcake) baseCupcake).decorate(g2);
			addDecorTextTimer++;
			if (addDecorTextTimer == 80) {
				glitter.play(0);
				state = DECORATE;
				addDecorTextTimer = 80;
			}
		} else if (state == DECORATE) {
			kitchen.drawFinalResult(g2);
			cupcake.decorate(g2);
			text("All finished!", 35, 60, g2);
			finalCupcakeTimer++;
			if(finalCupcakeTimer == 150) {
				state = DONE;
				finalCupcakeTimer = 150;
			}
		} else {
			state = DONE;
			end.drawMe(g2);
			restartButton.drawMe(g2);
			bgMusic.pause();
		}	
	}
	
	//displays a string of text at a specified location
	public void text(String string, int x, int y, Graphics2D g2) {
		g2.translate(x, y);
		g2.setFont(font);
		g2.setColor(new Color(50, 50, 50));
		g2.drawString(string, 0, 0);
	}
}
