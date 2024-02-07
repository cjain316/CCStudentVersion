package CandyCrush;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.Hashtable;

public class Candy {
	private String color; 
	//Can be Purple, Green, Red, Yellow, Orange, or Blue
	//Color attribute must be capitalized 
	
	
	public void paint(Graphics g, Grid grid, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		

		
		int[] passthrough = {1,x,y};
		int[] transform = translateGridPosition(passthrough,grid);
		
		tx = AffineTransform.getTranslateInstance(transform[0],transform[1]);
		Sprite = getImage(sprites.get(color));
		g2.drawImage(Sprite, tx, null);
	}
	
	public String getColor() {return color;}
	
	private int[] translateGridPosition(int[] a,Grid g) 
	{
		int[] output = {(a[1]*g.squareSize+g.xOffset),a[2]*g.squareSize+g.yOffset};
		return output;
	}
	
	public String toString() {
		return color;
	}
	
	public Candy(String color) {
		sprites = new Hashtable<String, String>();
		sprites.put("Purple","Sprites\\Purple.png");
		sprites.put("Green","Sprites\\Green.png");
		sprites.put("Red","Sprites\\Red.png");
		sprites.put("Yellow","Sprites\\Yellow.png");
		sprites.put("Orange","Sprites\\Orange.png");
		sprites.put("Blue","Sprites\\Blue.png");
		
		if (sprites.containsKey(color)) {
			this.color = color;
		} else {
			this.color = "Invalid";
		}
	}
	
	protected Image getImage(String path) {

	        Image tempImage = null;
	        try {
	            URL imageURL = Candy.class.getResource(path);
	            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
	        } catch (Exception e) {e.printStackTrace();}
	        return tempImage;
	}
	
	private AffineTransform tx;
	private Image Sprite;
	public Hashtable<String, String> sprites;
	
}