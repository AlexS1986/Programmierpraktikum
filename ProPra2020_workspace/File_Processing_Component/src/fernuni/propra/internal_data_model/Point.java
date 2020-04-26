package fernuni.propra.internal_data_model;

import java.util.ArrayList;
import java.util.List;

public class Point {
	private final double x;
	private final double y;
	private final static double TOL = 0.0001;
	private final static int PRECISION = 1000;
	public final static double INF = 100000000;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean isEqual(Point other) {
		if (other == null) return false;
			return (Math.round(getX() * PRECISION) == Math.round(other.getX() * PRECISION)) 
					&& (Math.round(getY() * PRECISION) == Math.round(other.getY() * PRECISION));
		//return (Math.abs(getX()-other.getX())  + Math.abs(getY()-other.getY()))  < TOL;
	}
	
	public boolean isOnLineSegment(Point p1, Point p2) {
		if (!p1.sameX(p2) && !p1.sameY(p2)) throw new IllegalArgumentException("Input is not a horizontal or vertical line!");
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
				/*if (lineSegment.getP1().isOnLineSegment(testLineSeg.getP1(), testLineSeg.getP2()) ||
					lineSegment.getP2().isOnLineSegment(testLineSeg.getP1(), testLineSeg.getP2())) {
					intersectedLineSegmentHasEndPointOnTestLineSegCount ++;
				} */
			} catch (LineSegmentException e) {
			}
			
			try {
				testLineSegYP.intersectionWithLinesegment(lineSegment);
				if (isOnLineSegment(lineSegment.getP1(), lineSegment.getP2())) {
					return true; // if point is on wall -> point is in polygonial
				} else {
					intersectionCountYP++;
				}
				/*if (lineSegment.getP1().isOnLineSegment(testLineSeg.getP1(), testLineSeg.getP2()) ||
					lineSegment.getP2().isOnLineSegment(testLineSeg.getP1(), testLineSeg.getP2())) {
					intersectedLineSegmentHasEndPointOnTestLineSegCount ++;
				} */
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
