package CandyCrush;

import java.awt.Point;

public class ArrayPosition { 
	//Used for translating between (x, y) coordinates and [r][c] coordinates
	public int r, c;
	public int x, y;
	
	//Instantiate with (x, y) coordinates
	public ArrayPosition(Point p) {
		c = p.x;
		r = p.y;
		x = p.x;
		y = p.y;
	}
	
	//Instantiate with [row][column] values
	public ArrayPosition(int row, int column) {
		this.r = row;
		this.c = column;
		x = column;
		y = row;
	}
	
	public String toString() {
		return "["+r+"]["+c+"] ("+x+", "+y+")";
	}
	
	public void updateXY(int dx, int dy) {
		this.x += dx;
		this.y += dy;
		this.c += dx;
		this.r += dy;
	}
	
	public void updateRC(int dr, int dc) {
		this.x += dc;
		this.y += dr;
		this.r += dr;
		this.c += dc;
	}
}
