package fernuni.propra.internal_data_model;


public class Wall extends LineSegment{
	private int tag;
	
	
	public Wall(Point p1, Point p2, int tag) {
		super(p1, p2);
		this.tag = tag;
	}
	
	public Wall(LineSegment lineSegment, int tag) {
		super(lineSegment.getP1(), lineSegment.getP2());
		this.tag = tag;
	}
	
	public boolean isNorthWall() {
		return isHorizontal() && getP1().largerX(getP2());
	}
	
	public boolean isWestWall() {
		return isVertical() && getP1().largerY(getP2());
	}
	
	public boolean isSouthWall() {
		return isHorizontal() && getP2().largerX(getP1());
	}
	
	public boolean isEastWall() {
		return isVertical() && getP2().largerY(getP1());
	}
	
	public int getTag() {
		return tag;
	}

	
	
}
