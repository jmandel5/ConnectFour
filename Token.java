package application;
import java.awt.*;

public class Token {
	//Coordinate
	public Point coordinates;
	//Neighbor Tokens
	public Token N ;
	public Token NE ;
	public Token E ;
	public Token SE ;
	public Token S ;
	public Token SW;
	public Token W;
	public Token NW;
	//Token Color
	public Color color;
	
	public Token (Point p ) {
		N = null;
		NE = null;
		E = null;
		SE = null;
		SW = null;
		W = null;
		NW = null;
		color = null;
		coordinates = p;
	}
	public Token () {
		coordinates = new Point();
	}
	
}
