package fernuni.propra.algorithm;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.internal_data_model.Wall;

/**
 * An abstract class that represent a generic {@link Wall} container for a
 * certain type of {@link Wall}s, where the type of the
 * {@link WallContainerAbstract} must be specified by implementing subclasses.
 * <p>
 * {@link Wall}s in the container are sorted according to total order specified
 * by a {@link Comparator}<{@link Wall}>, where the ordering must be specified
 * by implementing subclasses. The total ordering is done by mapping
 * {@link Wall}s on double numbers and using the total ordering defined by the
 * ordering of real numbers.
 * <p>
 * {@link Wall}s can be added if they are of the correct type.
 * <p>
 * The {@link WallContainerAbstract} can also return the nearest ( in the sense
 * of the specified ordering), valid wall, where validity of a {@link Wall} must
 * be specified by implementing subclasses.
 * <p>
 * Implements the template pattern, where templates are given for the two core
 * functionalities, i.e. adding walls and obtaining a nearest wall in some
 * sense. Subclasses must fill in the blanks by implementing the abstract
 * methods.
 * <p>
 * Implementing classes : {@link WallContainerEast}, {@link WallContainerNorth},
 * {@link WallContainerSouth}, {@link WallContainerWest}
 * <p>
 * 
 * @author alex
 *
 */
public abstract class WallContainerAbstract implements Iterable<Wall> {

	protected List<Wall> walls; // the walls in this container

	/**
	 * Constructor
	 */
	public WallContainerAbstract() {
		walls = new LinkedList<Wall>();

	}

	/**
	 * A method that allows to add a {@link Wall} of the correct type to the
	 * container.
	 * 
	 * @param wall : the {@link Wall} to be added
	 * @throws WallContainerException : thrown if wall is not of the correct type.
	 */
	public void add(Wall wall) throws WallContainerException {
		if (!isCorrectWallType(wall))
			throw new WallContainerException(
					"Wall does not " + "have the correct orientation for this wall container!");
		walls.add(wall);
		walls.sort(getComparator());
	}

	/**
	 * A method that allows to search for the next {@link Wall} (in the sense of the
	 * total ordering defined by the implementing subclass). The search can be
	 * further specified by defining a range of doubles, whose meaning also needs to
	 * be specified by the implementing subclasses and the clients.
	 * 
	 * @param low   : lower limit of the range
	 * @param high  : upper limit of the range
	 * @param limit : limit to be used to find the next wall according to the
	 *              ordering.
	 * @return the next {@link Wall}
	 * @throws WallContainerException : if range is not set correctly
	 */
	public Wall getNearestWall(double low, double high, double limit) throws WallContainerException {
		if (low > high)
			throw new WallContainerException(); // not a valid range
		Iterator<Wall> iterator = walls.iterator(); // walls are ordered
		Wall nextWall;
		while (iterator.hasNext()) {
			nextWall = iterator.next();
			if (isValidWall(nextWall, limit, low, high)) { // wall fits the range and is also the next wall according to
															// the ordering
				return nextWall;
			}
		}
		return null;
	}

	/**
	 * Provides access to all the walls in the container by returning an iterator.
	 */
	public Iterator<Wall> iterator() { // TODO return only a copy?
		return walls.iterator();
	}

	/**
	 * Computes whether a {@link Wall} has is indeed a subsequent - relative to the
	 * limit - {@link Wall} with respect to to the total ordering of this container.
	 * 
	 * @param wall
	 * @param limit : limit that characterizes the wall and is used to decide if
	 *              wall is indeed a subsequent {@link Wall} with respect to the
	 *              total ordering of {@link Wall}s in the container.
	 * @param low   : lower end of the range that is used to further specify the
	 *              search for the next {@link Wall}
	 * @param high  : higher end of the range that is used to further specify the
	 *              search for the next {@link Wall}
	 * @return a boolean that shows whether the {@link Wall} is a valid {@link Wall}
	 *         that matches the semantics of {@link getNearestWall}
	 */
	protected abstract boolean isValidWall(Wall wall, double limit, double low, double high);

	/**
	 * 
	 * @return A {@link Comparator}<{@link Wall}> that specifies the total ordering
	 *         that is used in this container.
	 */
	protected abstract Comparator<Wall> getComparator();

	/**
	 * 
	 * @param wall : the {@link Wall} to be checked.
	 * @return a boolean that indicates whether wall has the suited type for this
	 *         container.s
	 */
	protected abstract boolean isCorrectWallType(Wall wall);
}
