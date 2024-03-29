package fernuni.propra.internal_data_model;

/**
 * A class that represents walls of an {@link IRoom} and supports special
 * functionality that is needed within the algorithm.
 * <p>
 * {@link Wall}s can be tagged. This is typically done to subsequently tag
 * {@link Lamp}s with the same tag, as if to show that if this {@link Lamp} is
 * turned on, then {@link Wall} with the same tag is also illuminated.
 * <p>
 * A {@link Wall} can also provide information on whether it is a north, west,
 * south or east {@link Wall}.
 * <p>
 * Extended classes: {@link LineSegment}
 * <p>
 * 
 * @author alex
 *
 */
public class Wall extends LineSegment {
	private int tag;

	/**
	 * Constructor
	 * 
	 * @param p1  : The start corner {@link Point}
	 * @param p2  : The end corner {@link Point}
	 * @param tag : The tag of the new {@link Wall}
	 */
	public Wall(Point p1, Point p2, int tag) {
		super(p1, p2);
		this.tag = tag;
	}

	/**
	 * Constructor
	 * 
	 * @param lineSegment : The {@link LineSegment} that the new {@link Wall} should
	 *                    be identical with in a geometrical sense
	 * @param tag         : The tag of the new {@link Wall}
	 */
	public Wall(LineSegment lineSegment, int tag) {
		super(lineSegment.getP1(), lineSegment.getP2());
		this.tag = tag;
	}

	/**
	 * 
	 * @return A boolean that represents whether this {@link Wall} is a north
	 *         {@link Wall}, i.e. the interior of a potential associated
	 *         {@link IRoom} is to its south.
	 */
	public boolean isNorthWall() {
		return isHorizontal() && getP1().largerX(getP2());
	}

	/**
	 * 
	 * @return A boolean that represents whether this {@link Wall} is a west
	 *         {@link Wall}, i.e. the interior of a potential associated
	 *         {@link IRoom} is to its east.
	 */
	public boolean isWestWall() {
		return isVertical() && getP1().largerY(getP2());
	}

	/**
	 * 
	 * @return A boolean that represents whether this {@link Wall} is a south
	 *         {@link Wall}, i.e. the interior of a potential associated
	 *         {@link IRoom} is to its north.
	 */
	public boolean isSouthWall() {
		return isHorizontal() && getP2().largerX(getP1());
	}

	/**
	 * 
	 * @return A boolean that represents whether this {@link Wall} is an east
	 *         {@link Wall}, i.e. the interior of a potential associated
	 *         {@link IRoom} is to its west.
	 */
	public boolean isEastWall() {
		return isVertical() && getP2().largerY(getP1());
	}

	/**
	 * 
	 * @return The tag of this {@link Wall}.
	 */
	public int getTag() {
		return tag;
	}

}
