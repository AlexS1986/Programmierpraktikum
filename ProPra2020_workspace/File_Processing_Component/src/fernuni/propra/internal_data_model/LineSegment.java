package fernuni.propra.internal_data_model;

import java.util.List;

public class LineSegment {
	private final static double TOL = 0.0001;
	private final Point p1;
	private final Point p2;
	
	
	public LineSegment(Point p1, Point p2) {
		// pre p1 != 0, p2 != 0
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Point getP1() {
		return p1;
	}
	
	public Point getP2() {
		return p2;
	}
	
	public boolean isHorizontal() {
		return p1.sameY(p2) && !p1.isEqual(p2);
	}
	
	public boolean isVertical() {
		return p1.sameX(p2) && !p1.isEqual(p2);
	}
	
	public boolean overlapsXrange(double xLow, double xHigh) {
		if (xLow>xHigh) throw new IllegalArgumentException("xLow > xHigh! Insert a valid range!");
		// pre xLow < xHigh
		boolean p1IsInRange = p1.isInXRange(xLow, xHigh);
		boolean p2IsInRange = p2.isInXRange(xLow, xHigh);
		boolean p1SmallerP2Greater = (new Point(xLow, p1.getY()).largerX(getP1())) &&
				(getP2().largerX(new Point(xHigh, p2.getY()))) ;
		boolean p2SmallerP1Greater = (new Point(xLow, p2.getY()).largerX(getP2())) &&
				(getP1().largerX(new Point(xHigh, p1.getY()))); 
		
		return  p1IsInRange || p2IsInRange || p1SmallerP2Greater || p2SmallerP1Greater;
	}
	
	public boolean overlapsYrange(double yLow, double yHigh) {
		if (yLow>yHigh) throw new IllegalArgumentException("yLow > yHigh! Insert a valid range!");
		// pre yLow < yHigh
		boolean p1IsInRange = p1.isInYRange(yLow, yHigh);
		boolean p2IsInRange = p2.isInYRange(yLow, yHigh);
		boolean p1SmallerP2Greater = (new Point(p1.getX(),yLow).largerY(getP1())) &&
				(getP2().largerY(new Point(p2.getX(),yHigh))) ;
		boolean p2SmallerP1Greater = (new Point(p2.getY(),yLow ).largerY(getP2())) &&
				(getP1().largerY(new Point(p1.getX(),yHigh))); 
		
		return  p1IsInRange || p2IsInRange || p1SmallerP2Greater || p2SmallerP1Greater;
		
	}
	
	public boolean perpendicular(Point p1, Point p2) {
		double dx = this.p2.getX()-this.p1.getX();
		double dy = this.p2.getY()-this.p1.getY();
		
		double dxOther = p2.getX()-p1.getX();
		double dyOther = p2.getY()-p1.getY();
		
		double scalarProduct = dx * dxOther + dy * dyOther;
		
		return Math.abs(scalarProduct) < TOL ;
	}
	
	public boolean perpendicular(LineSegment other) {
		return perpendicular(other.getP1(), other.getP2());
	}
	
	public boolean doesNotIntersectLineSegments(List<LineSegment> lineSegments) {
		if (lineSegments.isEmpty()) return true;
		boolean doesNotIntersect = true;
		for (int j = 0; j < lineSegments.size(); j++) {
			LineSegment tmpLineSegment = lineSegments.get(j);
			if (getP1().isOnLineSegment(tmpLineSegment) || getP2().isOnLineSegment(tmpLineSegment)) {
				return false;
			}
			try {
				Point intersectionPoint = this.intersectionWithLinesegment(tmpLineSegment);
				if (!intersectionPoint.isEqual(this.getP1()) 
						&& !intersectionPoint.isEqual(this.getP2()) ) {
					doesNotIntersect = false;
					break;
				}
			} catch (LineSegmentException e) {
				
			}
		}
		return doesNotIntersect;
	}
	
	public Point intersectionWithLinesegment(LineSegment other) throws LineSegmentException {
		if (perpendicular(other)) {
			double x;
			double y;
			if (isHorizontal()) {
				y = getP1().getY();
				x = other.getP1().getX();
			} else {
				y = other.getP1().getY();
				x = getP1().getX();
			}
			Point outPoint = new Point(x,y);
			if (outPoint.isOnLineSegment(other) && outPoint.isOnLineSegment(this)) {
				return outPoint;
			} else {
				throw new LineSegmentException("Line Segments do not intersect!");
			}
		} else {
			throw new LineSegmentException("Lines are not perpendicular cannot return (unique) intersection point!");
		}
				
	}
		
}
