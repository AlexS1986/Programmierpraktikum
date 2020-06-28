package fernuni.propra.internal_data_model;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Point} represents a geometric point in a x-y plane. The {@link Point} class 
 * also provides functionality to support the overall algorithm.
 * <p>
 * Extending classes: {@link Lamp}
 * <p>
 * @author alex
 *
 */
public class Point {
	private final double x;
	private final double y;
	private final static double TOL = 0.000001;
	
	/* precision at which doubles should be compared 
	to identify equal Points */
	private final static int PRECISION = 100000; 
	
	/* A large number representing infinity */
	private final static double INF = Double.MAX_VALUE;
	
	/**
	 * Constructor
	 * @param x : the x-coordinate of the new {@link Point}
	 * @param y : the y-coordinate of the new {@link Point}
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return the x-coordinate
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * 
	 * @return the y-coordinate
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Checks whether this {@link Point} is on a {@link LineSegment}.
	 * @param p1 : The start {@link Point} of the {@link LineSegment}
	 * @param p2 : The end {@link Point} of the {@link LineSegment}
	 * @return A boolean representing whether this {@link Point} 
	 * is on the {@link LineSegment}
	 * defined by p1 and p2
	 */
	public boolean isOnLineSegment(Point p1, Point p2) {
		if (!p1.sameX(p2) && !p1.sameY(p2)) throw 
		new IllegalArgumentException("Input is not a horizontal or vertical line!");
		boolean xAgrees = this.sameX(p1) 
				&& this.sameX(p2);
		boolean yAgrees = Point.agrees(getY(), p1.getY()) 
				&& Point.agrees(getY(), p2.getY());
		boolean xInRange = isInRange(getX(), p1.getX(), p2.getX());
		boolean yInRange = isInRange(getY(), p1.getY(), p2.getY());
		return (xAgrees && yInRange) || (yAgrees && xInRange);
	}
	
	public boolean isOnLineSegment (LineSegment lineSegment) {
		return isOnLineSegment(lineSegment.getP1(), lineSegment.getP2());
	}
	
	
	boolean sameX(Point other) {
		return Point.agrees(this.x, other.x);
	}
	
	boolean sameY(Point other) {
		return Point.agrees(this.y, other.y);
	}
	
	boolean largerX(Point other) {
		return Point.isLarger(this.x, other.x);
	}
	
	boolean largerY(Point other) {
		return Point.isLarger(this.y, other.y);
	}
	
	public boolean isInsidePolygon(List<LineSegment> lineSegments) {
		ArrayList<LineSegment> arrayLinesSegments = new ArrayList<LineSegment>(lineSegments);
		
		// pre lineSegment must be a valid polygonial
		LineSegment testLineSegXP = new LineSegment(this, new Point(INF, getY()));
		LineSegment testLineSegYP = new LineSegment(this, new Point(getX(), INF));
		
		int intersectionCountXP = 0;
		int intersectionCountYP = 0;
		for (LineSegment lineSegment : lineSegments) {
			try {
				testLineSegXP.intersectionWithLinesegment(lineSegment);
				if (isOnLineSegment(lineSegment.getP1(), lineSegment.getP2())) {
					return true; // if point is on wall -> point is in polygonial
				} else {
					intersectionCountXP++;
				}
			} catch (LineSegmentException e) {
			}
			
			try {
				testLineSegYP.intersectionWithLinesegment(lineSegment);
				if (isOnLineSegment(lineSegment.getP1(), lineSegment.getP2())) {
					return true; // if point is on wall -> point is in polygonial
				} else {
					intersectionCountYP++;
				}
			} catch (LineSegmentException e) {
			}
			
			
		}

		if ((intersectionCountXP % 2) != 0 || (intersectionCountYP % 2) != 0) { // if number of intersections is odd -> point is in polygonial
			return true;
		} else {
			return false;
		}	
	}
	
	public boolean isInXRange(double xLow, double xHigh) {
		if (xLow>xHigh) throw new IllegalArgumentException("xLow > xHigh");
		return isInRange(getX(), xLow, xHigh);
	}
	
	public boolean isInYRange(double yLow, double yHigh) {
		if(yLow > yHigh) throw new IllegalArgumentException("yLow > yHigh");
		return isInRange(getY(), yLow, yHigh);
	}
	
	public boolean isInsideRectangle(Point p1, Point p3) {
		return isInXRange(p1.getX(), p3.getX()) && isInYRange(p1.getY(), p3.getY());
	}
	
	private static boolean agrees(double x, double x1) {
		return Math.abs(x-x1)<TOL;
	}
	
	private static boolean isLarger(double x, double x1) {
		return x-x1 > TOL;
	}
	
	private static  boolean isInRange(double x, double x1, double x2) {
		return (Math.min(x1, x2) < x + TOL) && (Math.max(x1, x2) > x - TOL);				
	}
	
	private boolean isEqual(Point other) {
		if (other == null) return false;
			return (Math.round(getX() * PRECISION) == Math.round(other.getX() * PRECISION)) 
					&& (Math.round(getY() * PRECISION) == Math.round(other.getY() * PRECISION));
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if(!(o instanceof Point)) {
			return false;
		}
		
		Point point = (Point) o;
		return isEqual(point);
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) Math.round(x * PRECISION);
		result = 31 * result + (int) Math.round(y * PRECISION);
		return result;
	}
	
	
	


}
