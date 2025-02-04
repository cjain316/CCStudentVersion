package CandyCrush;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//*********************************
//* Complete Methods in Grid.java *
//*********************************

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
	Grid grid = new Grid();
	int score = 0;
	int frameDelay = 0;
	String activeAnimation = "none";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main f = new Main();
	}
	
	public void setActiveAnimation(String s) {
		activeAnimation = s;
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tick();
        
        setBackground(g);
        updatePointer(f);
        grid.paint(g);
        
        //paint score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Apple Casual",Font.PLAIN,40));
        g.drawString("Score: " + score, 50, 50);
        
        paintGridContents(g);
        
        scoringCheck();
        grid.replaceEmpty();
        
        clickFunctionUpdate(g);
	}
	
	public void tick() {
		if (frameDelay > 0) {frameDelay-=1;}
		if (!(grid.animationOverride.equals(""))) {
			activeAnimation = grid.animationOverride;
		}
	}
	
	private void paintGridContents(Graphics g) {
		for (int r = 0; r < grid.getLength();r++) {
			for (int c = 0; c < grid.getHeight(); c++) {
				Candy candy = grid.getValue(r, c);
				if (candy!=null) {candy.paint(g, grid, c, r);}
			}
		}
	}

	private ArrayPosition passthrough;
	private boolean mouseDown;
	private void clickFunctionUpdate(Graphics g) {
		ArrayPosition loc = getGridLocation(grid);
		if (loc != null) {
			ArrayPosition temp = loc;
        	if (!mouseDown) {
        		passthrough = loc;
        	} else {
        		g.setColor(new Color(150,150,150));
        		drawPointer(passthrough, grid, g);
        	}
        	g.setColor(Color.WHITE);
        	drawPointer(temp, grid, g);
        }
	}
	
	private Image background;
	private void setBackground(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform tx = AffineTransform.getTranslateInstance(0, -100);
		g2.drawImage(background, tx, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {repaint();}
	
	private Point mousePosition = new Point(0,0);
	private void updatePointer(Container frame) {
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, frame);
        Insets insets = frame.getInsets();
        point.translate(insets.left*-1, insets.top*-1);
        mousePosition.x = (int)point.getX();
        mousePosition.y = (int)point.getY();
    }
	
	private ArrayPosition getGridLocation(Grid g) {
		Point p = mousePosition;
		Point br = new Point(g.getLength()*g.squareSize+g.xOffset, g.getHeight()*g.squareSize+g.yOffset);
		if (p.x > g.xOffset && p.x < br.x) {
			if (p.y > g.yOffset && p.y < br.y) {
				p.translate(g.xOffset*-1, g.yOffset*-1);
				return new ArrayPosition(new Point(p.x/g.squareSize, p.y/g.squareSize));
			}
		}
		return null;
	}
	
	private Point translateGridPosition(ArrayPosition a,Grid g) {
		return new Point(a.x*g.squareSize+g.xOffset, a.y*g.squareSize+g.yOffset);
	}
	
	public int scoringCheck() {
		int updateScore = 0;
		Candy[] arr = new Candy[] {};
		String type;
		
		while (grid.inARow() != -1 || grid.inAColumn() != -1) {
			int index, max = 0;
			int inarow = grid.inARow();
			int inacol = grid.inAColumn();
			
			if (inarow != -1) { //If a row exists with 3 or more in a row
				arr = grid.getRow(inarow);
				type = "row";
			} else { //If a column exists with 3 or more in a row
				arr = grid.getColumn(inacol);
				type = "column";
			}
			
			index = grid.findMaximum(arr)[1];
			max = grid.findMaximum(arr)[0];
			
			if (type.equals("row")) {
				grid.clearRow(index, index+max-1, inarow);
			} else if (type.equals("column")) {
				grid.clearColumn(index, index+max-1, inacol);
			}
			
			updateScore += 100*max;
		}
		score += updateScore;
		return updateScore;
	}
	
	private JFrame f;
	private Timer t;
    public Main() {
        f = new JFrame("Candy Crush");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800,800);
        f.add(this);
        f.addMouseListener(this);
        f.addKeyListener(this);
        f.setResizable(false);
        
        t = new Timer(7, this);
        t.start();
        f.setVisible(true);
        
        this.background = getImage("Sprites\\bgFull.png");
    }
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		updatePointer(f);
		
		ArrayPosition loc = getGridLocation(grid);
		if (loc == null) {return;}
		
		ArrayPosition p1 = passthrough;
		ArrayPosition p2 = loc;
    	
    	if (grid.isAdjacent(p1, p2) && grid.checkSwappedPositions(p1, p2)) {
    		grid.swapSpaces(p1, p2);
    	}
	}

	protected Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Main.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
	}
	
	private void drawPointer(ArrayPosition gridPosition, Grid grid, Graphics g) {
		Point tl = translateGridPosition(gridPosition, grid);
		g.fillRect(tl.x+2, tl.y+2,20,6);
		g.fillRect(tl.x+2, tl.y+2,6,20);
		
		g.fillRect(tl.x+43, tl.y+2,20,6);
		g.fillRect(tl.x+58, tl.y+2,6,20);
		
		g.fillRect(tl.x+2, tl.y+58,20,6);
		g.fillRect(tl.x+2, tl.y+43,6,20);
		
		g.fillRect(tl.x+43, tl.y+58,20,6);
		g.fillRect(tl.x+58, tl.y+43,6,20);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}