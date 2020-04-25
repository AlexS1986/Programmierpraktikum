package fernuni.propra.algorithm.util;

import fernuni.propra.internal_data_model.Point;

public class Rectangle {
	private Point p1,p2,p3,p4;
	
	public Rectangle(Point p1, Point p3) {
		if(!isValidRectangle(p1, p3)) {
			throw new IllegalArgumentException("Rectangle not initialized correctly");
		}
		this.p1 = p1;
		this.p2 = new Point(p3.getX(),p1.getY());
		this.p3 = p3;
		this.p4 = new Point(p1.getX(), p3.getY());
	}
	
	public Point getP1() {
		return new Point(p1.getX(), p1.getY());
	}
	
	public Point getP2() {
		return new Point(p2.getX(), p2.getY());
	}
	
	public Point getP3() {
		return new Point(p3.getX(), p3.getY());
	}
	
	public Point getP4() {
		return new Point(p4.getX(), p4.getY());
	}
	
	
	public Rectangle overlap(Rectangle other) {
		Point p1 = new Point(Math.max(this.p1.getX(), other.p1.getX()), 
				Math.max(this.p1.getY(), other.p1.getY()));
		Point p3 = new Point(Math.min(this.p3.getX(), other.p3.getX()), 
				Math.min(this.p3.getY(), other.p3.getY()));
		if(isValidRectangle(p1, p3)) {
			Rectangle outRectangle = new Rectangle(p1, p3);
			if(outRectangle.isCounterClockWise()) {
				return outRectangle;
			} else {
				return null;
			}
		} else {
			return null;
		}
		

		
		/*
		boolean rectanglesOverlap = (other.p1.isInXRange(p1.getX(), p2.getX()) || 
				other.p2.isInXRange(p1.getX(), p2.getX())) && 
				(other.p2.isInYRange(p1.getY(), p4.getY()) ||
						other.p3.isInYRange(p1.getY(), p4.getY())); */
		
		/*Rectangle outRectangle = null;
		if(rectanglesOverlap) {
			Point p1 = new Point(Math.max(p1.getX(), other.p1.getX()), Math.max(p1.getY(), other.p1.getY()));
			Point p3 = new Point(Math.min(p3.getX(), other.p3.getX()), Math.max(p3.getY(), other.p3.getY()));
					
			outRectangle = new Rectangle(p1, p3)
		}*/
	}
	
	public Point getCenter() {
		double width = p2.getX() - p1.getX();
		double height = p3.getY() - p1.getY();
		return new Point(p1.getX()+width/2.0, p1.getY()+height/2.0);
	}
		
	public boolean isCounterClockWise() {
		double dx1 = 0.0;
		double dx2 = p1.getX()-p2.getX();
		double dy1 = p3.getY() - p2.getY();
		double dy2 = 0.0;
		
		return dx1 * dy2 - dx2 * dy1 > 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if(!(o instanceof Rectangle)) {
			return false;
		}
		Rectangle r = (Rectangle) o;
		return getP1().isEqual(r.getP1()) && getP2().isEqual(r.getP2()) 
				&& getP3().isEqual(r.getP3()) && getP4().isEqual(r.getP4()) ;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + p1.hashCode();
		result = 31 * result + p3.hashCode();
		return result;
	}
	
	private static boolean isValidRectangle(Point p1, Point p3) {
		boolean isValidRectangle = p1.getX()< p3.getX() && p3.getY()> p1.getY();
		return isValidRectangle;
	}
	
}
