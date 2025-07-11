package main;
import javax.swing.JFrame;

//CupcakeApp subclass that creates the app 
public class CupcakeApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public CupcakeApp(String title) {
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CupcakePanel panel = new CupcakePanel(this);
		this.add(panel); 
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	//runs the app 
	public static void main (String[] args){
		new CupcakeApp("Cupcake App");
		
	}
}
