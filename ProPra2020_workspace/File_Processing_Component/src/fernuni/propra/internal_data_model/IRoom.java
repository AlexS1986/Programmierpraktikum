package fernuni.propra.internal_data_model;

import java.util.Iterator;
import java.util.List;

/**
 * An interface that specifies the functionality and properties of a room, as
 * defined in [1].
 * <p>
 * An {@link IRoom}, i.e. a room, is specified by a {@link List}<{@link Point}>
 * that contains its corners. {@link Wall}s are defined by the line segment
 * between two successive {@link Point}s in that list. It must be ensured that
 * {@link Wall}s can only have vertical or horizontal orientation, and that
 * these orientations alternate, with respect to the order defined by the corner
 * list.
 * <p>
 * A {@link IRoom} may also contain {@link Lamp}s that must be contained in the
 * polygon which is defined by the {@link Wall}s. All {@link Lamp}s (i.e. also
 * candidates that are not part of a potential optimal solution) are included.
 * In order to obtain the {@link Lamp}s that are part of the optimal solution it
 * is necessary to consider only the {@link Lamp}s that are turned on.
 * <p>
 * A {@link IRoom} is also associated with an ID (a String).
 * <p>
 * Implementing classes: {@link Room}.
 * <p>
 * [1] Aufgabenstellung Programmierpraktikum Fernuniversität Hagen, SS2020
 * <p>
 * 
 * @author alex
 *
 */
public interface IRoom {
	/**
	 * 
	 * @return an {@link Iterator}<{@link Lamp}> that can be used to iterate over
	 *         all the {@link Lamp}s of an {@link IRoom}.
	 */
	Iterator<Lamp> getLamps();

	/**
	 * 
	 * @return The number of all {@link Lamp}s (including lamp candidates, that are
	 *         not part of an optimal solution)
	 */
	int getNumberOfLamps();

	/**
	 * Adds a {@link Lamp} to the set of lamps of this {@link IRoom}.
	 * 
	 * @param lamp : the {@link Lamp} to be added.
	 */
	void addLamp(Lamp lamp);

	/**
	 * 
	 * @return an {@link Iterator}<{@link Point}> that can be used to iterate over
	 *         all corners of this {@link IRoom} in counter clockwise ordering
	 */
	Iterator<Point> getCorners();

	/**
	 * 
	 * @return an {@link Iterator}<{@link Wall}> that can be used to iterate over
	 *         all {@link Wall}s of this {@link IRoom}.
	 */
	Iterator<Wall> getWalls();

	/**
	 * 
	 * @return The minimum x-coordinate of any {@link Point} of this {@link IRoom}
	 */
	double getMinX();

	/**
	 * 
	 * @return The maximum x-coordinate of any {@link Point} of this {@link IRoom}
	 */
	double getMaxX();

	/**
	 * 
	 * @return The minimum y-coordinate of any {@link Point} of this {@link IRoom}
	 */
	double getMinY();

	/**
	 * 
	 * @return The maximum y-coordinate of any {@link Point} of this {@link IRoom}
	 */
	double getMaxY();

	/**
	 * 
	 * @return The ID of this {@link IRoom} as a String
	 */
	String getID();

	/**
	 * Replaces the set of {@link Lamp}s of this {@link IRoom} with the input
	 * parameter
	 * 
	 * @param lamps : The new set of {@link Lamp}s of this {@link IRoom}.
	 */
	void replaceLamps(List<Lamp> lamps);

	/**
	 * 
	 * @return A String that shows the {@link Lamp} positions and status (on or off)
	 *         for each {@link Lamp} in this {@link IRoom}.
	 */
	String printLampPositions();

}
