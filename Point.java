package application;
import java.util.Objects;

public class Point {
	public int x;
	public int y;
	
	public Point( int a, int b) {
		x = a;
		y = b;
	}
	public Point() {
		
	}
	public void setXY( int a, int b) {
		x = a;
		y = b;
	}
	public boolean equals ( Object o) {
		if ( o == this ) {
			return true;
		}
		if (! (o instanceof Point ) ) {
            return false;
        }
		else {
			Point p = (Point) o;
			return x == p.x && y == p.y;
		}
	}
	@Override
	public int hashCode () {
		 int hash = 1;
	     hash = hash * 17 + x;
	     hash = hash * 31 + y;
	     return hash;
	}

}
