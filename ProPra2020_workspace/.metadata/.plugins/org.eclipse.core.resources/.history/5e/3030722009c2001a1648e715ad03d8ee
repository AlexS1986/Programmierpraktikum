package fernuni.propra.algorithm;

import java.util.Comparator;
import java.util.Iterator;

import fernuni.propra.internal_data_model.Wall;

/**
 * A specific container that stores west walls. Those {@link Wall}s can be specified by
 * two {@link Point}s in a horizontal-vertical coordinate system. The {@link Wall}s
 * in this container are ordered in descending order with respect to the horizontal component 
 * (x-component) of their {@link Point}s.
 * <p>
 * The total ordering requested by {@link WallContainerAbstract} is such that walls
 * <p>
 * Extended classes and implemented interfaces: {@link WallContainerAbstract}.
 * <p>
 * @author alex
 *
 */
public class WallContainerWest extends WallContainerAbstract{
	
	@Override
	protected boolean isValidWall(Wall wall, double limit, double low, double high) {
		boolean isValidWall = wall.overlapsYrange(low, high) &&  wall.getP1().getX()<=limit;
		return isValidWall;
	}


	@Override
	protected Comparator<Wall> getComparator() {
		return new Comparator<Wall>() { 
			@Override
			public int compare(Wall o1, Wall o2) {
				if (o1.getP1().getX() > o2.getP1().getX()) {
					return -1;
				} else if (o1.getP1().getX()<o2.getP1().getX()) {
					return 1;
				} 
				return 0;
			}
		};
	}


	@Override
	protected boolean isCorrectWallType(Wall wall) {
		return wall.isWestWall();
	}

}
