package application;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	public HashMap<Point,Token> tokenBoard;
	//Number of token slots in the y direction
	public int height;
	//Number of token slots in the x direction
	public int width;
	//A reference to the most recently inserted token
	public Token latestToken;
	//The minimum tokens in a row to win the game
	public int numToWin;
	
	public Board() {
		tokenBoard = new HashMap<>();
		height = 6;
		width = 7;
	}
	public Board(int a, int b, int n) {
		tokenBoard = new HashMap<>();
		width = a;
		height = b;
		numToWin = n;
		
	}
	//Returns true if there is a free space in the given column
	public boolean hasFreeSpaceY( int column ) {
		if ( getFreeSpaceY(column) == height ) {
			return false;
		}
		return true;
	}
	//Returns the first free space in the given column;
	public int getFreeSpaceY(int column ) {
		int b = 0 ;
		while ( tokenBoard.get(new Point(column,b)) != null) {
			b++;
		}
		return b;
	}
	//Inserts a Token Object at the given column and color
	//The Token's neighbors are then found
	public void insertToken( int a, Color c) {
		int b = getFreeSpaceY(a);
		while ( tokenBoard.get(new Point(a,b)) != null) {
			b++;
		}
		Token newToken = new Token( new Point(a,b));
		newToken.color = c;
		tokenBoard.put(newToken.coordinates,newToken);
		latestToken = newToken;
		Point tempPoint = new Point();
		//S
		tempPoint.setXY(a, b-1);
		if ( tempPoint.y >= 0 && tokenBoard.get(tempPoint ) != null ) {
			newToken.S = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).N = newToken;
		}
		//SE
		tempPoint.setXY(a + 1, b - 1);
		if (tempPoint.x < width && tempPoint.y >= 0 && tokenBoard.get(tempPoint) != null   ) {
			newToken.SE = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).NW = newToken;		
		}
		//E
		tempPoint.setXY(a+1, b);
		if ( tempPoint.x < width && tokenBoard.get(tempPoint) != null  ) {
			newToken.E = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).W = newToken;
		}
		//NE
		tempPoint.setXY(a+1, b+1);
		if (tempPoint.x < width && tempPoint.y < height && tokenBoard.get(tempPoint) != null   ) {
			newToken.NE = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).SW = newToken;
		}
		//N
		tempPoint.setXY(a, b+1);
		if (tempPoint.y < height && tokenBoard.get(tempPoint) != null   ) {
			newToken.N = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).S = newToken;
		}
		//NW
		tempPoint.setXY(a-1, b+1);
		if (tempPoint.y < height && tempPoint.x >= 0&&  tokenBoard.get(tempPoint) != null   ) {
			newToken.NW = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).SE = newToken;
		}
		//W
		tempPoint.setXY(a-1, b);
		if (tempPoint.x >= 0 &&  tokenBoard.get(tempPoint) != null   ) {
			newToken.W = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).E = newToken;
		}
		//SW
		tempPoint.setXY(a-1, b-1);
		if (tempPoint.y >= 0 && tempPoint.x >= 0 &&  tokenBoard.get(tempPoint) != null   ) {
			newToken.SW = tokenBoard.get(tempPoint);
			tokenBoard.get(tempPoint).NE = newToken;
		}
	}
	//Given a point, the method finds the Token at the point and determines 
	//if there is a connect four at the given location
	public boolean hasConnect( Point p) {
		Token tempToken = tokenBoard.get(p);
		Token givenToken = tokenBoard.get(p);
		int count = 1;
		//Check S
		while ( tempToken.S != null && tempToken.S.color == givenToken.color) {
			count++;
			tempToken = tempToken.S;
		}
		if ( count >= numToWin) {
			return true;
		}
		//Check E/W direction for connect four
		tempToken = tokenBoard.get(p);
		count = 1;
		while ( tempToken.E != null && tempToken.E.color == givenToken.color ) {
			count++;
			tempToken = tempToken.E;
		}
		tempToken = tokenBoard.get(p);
		while ( tempToken.W != null && tempToken.W.color == givenToken.color ) {
			count++;
			tempToken = tempToken.W;
		}
		if ( count >= numToWin) {
			return true;
		}
		//Check NE/SW direction
		tempToken = tokenBoard.get(p);
		count = 1;
		while ( tempToken.NE != null && tempToken.NE.color == givenToken.color ) {
			count++;
			tempToken = tempToken.NE;
		}
		tempToken = tokenBoard.get(p);
		while ( tempToken.SW != null && tempToken.SW.color == givenToken.color ) {
			count++;
			tempToken = tempToken.SW;
		}
		if ( count >= numToWin) {
			return true;
		}
		//Check NW/SE direction
		tempToken = tokenBoard.get(p);
		count = 1;
		while ( tempToken.NW != null && tempToken.NW.color == givenToken.color ) {
			count++;
			tempToken = tempToken.NW;
		}
		tempToken = tokenBoard.get(p);
		while ( tempToken.SE != null && tempToken.SE.color == givenToken.color ) {
			count++;
			tempToken = tempToken.SE;
		}
		if ( count >= numToWin) {
			return true;
		}
		return false;
	}
}
