package fernuni.propra.internal_data_model;


public class Wall extends LineSegment{
	
	public Wall(Point p1, Point p2) {
		super(p1, p2);
	}
	
	
	public boolean isNorthWall() {
		return isHorizontal() && getP2().largerX(getP1());
	}
	
	public boolean isWestWall() {
		return isVertical() && getP1().largerY(getP2());
	}
	
	public boolean isSouthWall() {
		return isHorizontal() && getP1().largerX(getP2());
	}
	
	public boolean isEastWall() {
		return isVertical() && getP2().largerY(getP1());
	}

	
	
}
