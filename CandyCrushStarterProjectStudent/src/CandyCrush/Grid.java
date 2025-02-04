package CandyCrush;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.SwingUtilities;

public class Grid {
	private Candy[][] grid;
	
	public Grid() {
		grid = new Candy[10][10];
		fillGrid();
	}
	
	//*******************************************
	//*          Complete methods here          *  
	//*******************************************
	public int[] findMaximum(Candy[] candies) { 
		//Write a method to find the maximum length sequence of the same color candies in a row
		//The method returns an integer array formatted in the following way
		//output[0] = length of maximum sequence
		//output[1] = index in array when the maximum sequence occurs
		//Complete here
	    
	    return new int[] {0, 0};
	}
	
	public boolean isAdjacent(ArrayPosition p1, ArrayPosition p2) {
		//Write a method to check if two positions in a 2D array are adjacent to each other
		//Complete here
		
		return true;
	}
	//
	public void swapSpaces(ArrayPosition pos1, ArrayPosition pos2) {
		//Write a method that swaps the positions of two Candy objects given their positions in the 2d array
		//Complete here
		
	}
	
	private void fillGrid() { 
		//Write a method to fill out the 2D array with randomly colored squares
		//Complete here
		
	}
	
	public int inARow() {
		//Write a method to check if the grid contains a row with 3 or more of the same color candies in a row
		//Then return the index of the row
		//Must use the findMaximum method
		//Returns -1 if there are zero rows with 3 or more candies in a row
		
        return -1;
	}
	
	public int inAColumn() {
		//Write a method to check if the grid contains a column with 3 or more of the same color candies in a row
		//Then return the index of the column
		//Returns -1 if there are zero rows with 3 or more candies in a row
		
        return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	//Getters and setters
	public int getLength()           {return grid.length;}
	public int getHeight()           {return grid[0].length;}
	public Candy getValue(int r, int c)   {return grid[r][c];}
	
	public int xOffset = 65;
	public int yOffset = 80;
	public int squareSize = 65;
	public String animationOverride = "";
	
	public void paint(Graphics g) { paintGridOutline(g); }
	public Candy[] getRow(int r) { return grid[r]; }
	
	public Candy[] getColumn(int c) {
        Candy[] column = new Candy[grid.length];

        for (int i = 0; i < grid.length; i++) {
            column[i] = grid[i][c];
        }

        return column;
		
	}
	
	private void paintGridOutline(Graphics g) {
		int l = squareSize*getHeight();
		int w = squareSize*getLength();
		for (int a = 0; a < getLength();a++) {
			g.drawLine(xOffset, yOffset+(squareSize)*a, xOffset+w, yOffset+(squareSize)*a);
			g.drawLine(xOffset+(squareSize)*a, yOffset, xOffset+(squareSize)*a, yOffset+l);
		}
		g.drawLine(xOffset+w, yOffset,xOffset+w,yOffset+l);
		g.drawLine(xOffset, yOffset+l,xOffset+w,yOffset+l);
	}
	
	public boolean checkForEmptySpaces() {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				if (grid[r][c] == null) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void replaceEmpty() {
		while (checkForEmptySpaces()) {
			for (int c = 0; c < grid.length;c++) {
					if (grid[0][c] == null) {
						//animationOverride = "replace";
						grid[0][c] = new Candy(randomColor());
					}
			}
			for (int r = 0; r < grid.length; r++) {
				for (int c = 0; c < grid[r].length;c++) {
					if (grid[r][c] != null && r+1<10 && grid[r+1][c] == null) {
						swapSpaces(new ArrayPosition(r, c),new ArrayPosition(r+1, c));
					}
				}
			}
		}
		animationOverride = "";
	}
	
	private String randomColor() {
		String[] op = new String[]{"Purple","Green","Red","Orange","Blue","Yellow"};
		return op[(int)(Math.random()*6)];
	}
	
	public String toString() {
		String output = "";
		for (int r = 0;r <  grid.length; r++) {
			for (int c = 0; c < grid[r].length;c++) {
				if (grid[r][c] != null) {
					output += grid[r][c].getColor();
					output += " ";
				}
			}
			output += "\n";
		}
		return output;
	}
	
	public void clearRow(int startIndex, int endIndex, int rowIndex) {
		for (int i = startIndex; i <= endIndex && i < grid[0].length; i++) {
            grid[rowIndex][i] = null;
        }
	}
	
	public void clearColumn(int startIndex, int endIndex, int columnIndex) {
		for (int i = startIndex; i <= endIndex && i < grid.length; i++) {
            grid[i][columnIndex] = null;
        }
	}
	
	public boolean checkSwappedPositions(ArrayPosition p1, ArrayPosition p2) {
		Candy[][] checker = grid;
		Candy temp = checker[p1.r][p1.c];
		checker[p1.r][p1.c] = checker[p2.r][p2.c];
		checker[p2.r][p2.c] = temp;
		if (inARow() != -1 || inAColumn() != -1) {
			return false;
		}
		return false;
	}
	
	public void generateNew() {
		
	}
}