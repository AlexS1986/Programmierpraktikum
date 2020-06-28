package fernuni.propra.internal_data_model;

import java.util.List;



/**
 * A geometric object that represents a line segment.
 * <p>
 * The line segment is defined by a starting {@link Point} P1,
 * and an end {@link Point} P2.
 * <p>
 * The {@link LineSegment} provides functionality, such as
 * <p>
 * 1.) Tests that check whether the {@link LineSegment} is horizontal
 * 	   or vertical
 * <p>
 * 2.) A test that checks whether the projection of that {@link LineSegment}
 * 	   on the x-/y-axis overlaps a specified x- or y- range
 * <p>
 * 3.) A check whether this {@link LineSegment} is perpendicular  to
 * 	   another {@link LineSegment}.
 * <p>
 * 4.) A check that computes whether this {@link LineSegement}
 *     penetrates any other {@link LineSegment}s of a provided set.
 * <p>
 * 5.) The possibility to compute the intersection with another 
 * 		{@link LineSegment}
 * <p>
 * 6.) A check for equality with another {@link LineSegment}.
 * 
 * <p>
 * Extending classes : {@link Wall}
 * <p>
 * 
 * @author alex
 *
 */
public class LineSegment {
	// for geometric operations
	private final static double TOL = 0.0001;
	private final Point p1; // starting point
	private final Point p2; // end point
	
	
	/**
	 * Constructor
	 * @param p1 : Start {@link Point}
	 * @param p2 : End {@link Point}
	 */
	public LineSegment(Point p1, Point p2) {
		// pre p1 != 0, p2 != 0
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * 
	 * @return Start {@link Point}
	 */
	public Point getP1() {
		return p1;
	}
	
	/**
	 * 
	 * @return End {@link Point}
	 */
	public Point getP2() {
		return p2;
	}
	
	/**
	 * @return A boolean that represents whether this
	 * {@link LineSegment} is horizontal.
	 */
	public boolean isHorizontal() {
		return p1.sameY(p2) && !p1.equals(p2);
	}
	
	/**
	 * @return A boolean that represents whether this
	 * {@link LineSegment} is vertical.
	 */
	public boolean isVertical() {
		return p1.sameX(p2) && !p1.equals(p2);
	}
	
	/**
	 * 
	 * @param xLow
	 * @param xHigh
	 * @return A boolean that represents whether this
	 * {@link LineSegment} overlaps a range defined by xLow <= x <=xHigh
	 * when this {@link LineSegment} is projected on the x-axis.
	 */
	public boolean overlapsXrange(double xLow, double xHigh) {
		if (xLow>xHigh) throw 
		new IllegalArgumentException("xLow > xHigh! Insert a valid range!");
		// pre xLow < xHigh
		boolean p1IsInRange = p1.isInXRange(xLow, xHigh);
		boolean p2IsInRange = p2.isInXRange(xLow, xHigh);
		boolean p1SmallerP2Greater = (new Point(
				xLow, p1.getY()).largerX(getP1())) &&
				(getP2().largerX(new Point(xHigh, p2.getY()))) ;
		boolean p2SmallerP1Greater = (new Point(
				xLow, p2.getY()).largerX(getP2())) &&
				(getP1().largerX(new Point(xHigh, p1.getY()))); 
		
		return  p1IsInRange || p2IsInRange || 
				p1SmallerP2Greater || p2SmallerP1Greater;
	}
	
	/**
	 * 
	 * @param yLow
	 * @param yHigh
	 * @return A boolean that represents whether this
	 * {@link LineSegment} overlaps a range defined by yLow <= y <=yHigh
	 * when this {@link LineSegment} is projected on the y-axis.
	 */
	public boolean overlapsYrange(double yLow, double yHigh) {
		if (yLow>yHigh) throw 
		new IllegalArgumentException("yLow > yHigh! Insert a valid range!");
		// pre yLow < yHigh
		boolean p1IsInRange = p1.isInYRange(yLow, yHigh);
		boolean p2IsInRange = p2.isInYRange(yLow, yHigh);
		boolean p1SmallerP2Greater = (new Point(
				p1.getX(),yLow).largerY(getP1())) &&
				(getP2().largerY(new Point(p2.getX(),yHigh))) ;
		boolean p2SmallerP1Greater = (new Point(
				p2.getY(),yLow ).largerY(getP2())) &&
				(getP1().largerY(new Point(p1.getX(),yHigh))); 
		
		return  p1IsInRange || p2IsInRange || p1SmallerP2Greater || p2SmallerP1Greater;
		
	}
	
	/**
	 * 
	 * @param p1 : The start {@link Point} of another {@link LineSegment}
	 * @param p2 : The end {@link Point} of another {@link LineSegment}
	 * @return A boolean that represents whether this {@link LineSegment} is
	 * perpendicular to the {@link LineSegment} defined by p1 and p2.
	 */
	public boolean perpendicular(Point p1, Point p2) {
		double dx = this.p2.getX()-this.p1.getX();
		double dy = this.p2.getY()-this.p1.getY();
		
		double dxOther = p2.getX()-p1.getX();
		double dyOther = p2.getY()-p1.getY();
		
		double scalarProduct = dx * dxOther + dy * dyOther;
		
		return Math.abs(scalarProduct) < TOL ;
	}
	
	/**
	 * 
	 * @param other : {@link LineSegment} that is to be
	 * be tested whether its perpendicular to this {@link LineSegment}
	 * @return
	 */
	public boolean perpendicular(LineSegment other) {
		return perpendicular(other.getP1(), other.getP2());
	}
	
	/**
	 * A test that checks whether this {@link LineSegment}s penetrates
	 * any of the {@link LineSegment}s in a provided set of
	 * {@link LineSegment}s
	 * @param lineSegments : the set of {@link LineSegment}s to 
	 * be checked
	 * @return A boolean that states whether this {@link LineSegment}s 
	 * penetrates
	 * any of the {@link LineSegment}s
	 *  in a provided set of {@link LineSegment}s
	 * 
	 */
	public boolean penetratesLineSegments(List<LineSegment> lineSegments) {
		if (lineSegments.isEmpty()) return false;
		boolean penetrates = false;
		for (int j = 0; j < lineSegments.size(); j++) {
			LineSegment tmpLineSegment = lineSegments.get(j);
			try {
				Point intersectionPoint = 
						this.intersectionWithLinesegment(tmpLineSegment);
				if (!intersectionPoint.equals(this.getP1()) && 
						!intersectionPoint.equals(this.getP2())) {
					penetrates = true;
					break;
				}
			} catch (LineSegmentException e) {
				
			}
		}
		return penetrates;
	}
	
	/**
	 * Computes the intersection of this {@link LineSegment} 
	 * with another {@link LineSegment}. The intersection is 
	 * not computed for line segments but for the complete
	 * infinitely long lines defined by the {@link LineSegment}s
	 * 
	 * @param other : another {@link LineSegment}
	 * @return A {@link Point} that represents the intersection of
	 * this {@link LineSegment} with "other".
	 * @throws LineSegmentException : If {@link LineSegments} are
	 * not perpendicular.
	 */
	public Point intersectionWithLinesegment(LineSegment other) 
			throws LineSegmentException {
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
			if (outPoint.isOnLineSegment(other) && 
					outPoint.isOnLineSegment(this)) {
				return outPoint;
			} else {
				throw new LineSegmentException(
						"Line Segments do not intersect!");
			}
		} else {
			throw new LineSegmentException(
					"Lines are not perpendicular cannot"
					+ " return (unique) intersection point!");
		}
				
	}
	
	@Override
	public boolean equals(Object o) { // LineSegments are equal 
		//if their start and end points  are both equal 
		if (o == this) return true;
		if(!(o instanceof LineSegment)) {
			return false;
		}
		LineSegment ls = (LineSegment) o;
		return getP1().equals(ls.getP1()) && getP2().equals(ls.getP2()) ;
	}
	
		
}
