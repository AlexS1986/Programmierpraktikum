package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import fernuni.propra.algorithm.runtime_information.IRuntimeOriginalPartialRectanglesFinder;
import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

/**
 * Provides an algorithm to computes all orginal partial rectangles of an
 * {@link IRoom} instance and to tag these rectangles with tags that correspond
 * to a portion of the {@link IRoom} that is illuminated if the associated
 * partial rectangle is illuminated.
 * <p>
 * The tags/portions correspond to {@link Wall}s of the {@link IRoom} that are
 * illuminated if the associated rectangle is illuminated.
 * <p>
 * Implemented interfaces : {@link IOriginalPartialRectanglesFinder}
 * <p>
 * 
 * @author alex
 *
 */
public class RectanglesFinder  {

	private static double findWallTOL = 0.001; // necessary in order to correctly determine original partial rectangles
												// TODO set it to size of room?
	private HashSet<Integer> allTags = new HashSet<Integer>(); // all portions of the room
	private WallContainerEast wallContainerEast = new WallContainerEast();
	private WallContainerNorth wallContainerNorth = new WallContainerNorth();
	private WallContainerWest wallContainerWest = new WallContainerWest();
	private WallContainerSouth wallContainerSouth = new WallContainerSouth();
	private HashSet<Rectangle> originalRectangles = new HashSet<Rectangle>(); // all original partial rectangles of the
																				// room
	private ArrayList<RectangleWithTag> originalRectanglesTagged = new ArrayList<RectangleWithTag>(); // all tagged
																										// tagged
																										// original
																										// partial
																										// rectangles

	public ArrayList<RectangleWithTag> findRectangles(IRoom room,
			IRuntimeOriginalPartialRectanglesFinder rti) throws OriginalPartialRectanglesFinderException {
		try {
			sortWallsToContainers(room);
			constructOriginalPartialRectangles();
		} catch (WallContainerException | OriginalPartialRectanglesFinderException e) {
			throw new OriginalPartialRectanglesFinderException(e);
		}

		return originalRectanglesTagged;
	}

	public HashSet<Integer> getAllTags() {
		return allTags;

	}

	/**
	 * Sorts all walls of the {@link IRoom} instance to suited containers depending
	 * on their orientation
	 * <p>
	 * Package access is granted for testing purposes.
	 * <p>
	 * 
	 * @param room : The {@link IRoom} for which the {@link Wall}s need to be sorted
	 * @throws OriginalPartialRectanglesFinderException : thrown if wall orientation
	 *                                                  of a wall of the
	 *                                                  {@link IRoom} cannot be
	 *                                                  determined
	 */
	void sortWallsToContainers(IRoom room) throws OriginalPartialRectanglesFinderException {
		Iterator<Wall> wallIterator = room.getWalls();
		try {
			while (wallIterator.hasNext()) {
				Wall nextWall = wallIterator.next();
				if (nextWall.isEastWall()) {
					wallContainerEast.add(nextWall);
				} else if (nextWall.isNorthWall()) {
					wallContainerNorth.add(nextWall);
				} else if (nextWall.isWestWall()) {
					wallContainerWest.add(nextWall);
				} else if (nextWall.isSouthWall()) {
					wallContainerSouth.add(nextWall);
				} else {
					throw new OriginalPartialRectanglesFinderException(
							"Wall orientation cannot be determined! Wall might not be horizontal or vertical");
				}
			}
		} catch (WallContainerException wce) {
			throw new OriginalPartialRectanglesFinderException(wce.getMessage());
		}

	}

	/**
	 * Constructs the original partial rectangles for an {@link IRoom} and tags the
	 * rectangles with the {@link Wall} indices that correspond to {@link Wall}s
	 * that are illuminated if the rectangle is illuminated.
	 * <p>
	 * Package access for testing purposes.
	 * <p>
	 * {@link sortWallsToContainers} needs to be called first.
	 * <p>
	 * 
	 * @throws WallContainerException : {@link Wall} handling does not work.
	 */
	void constructOriginalPartialRectangles() throws WallContainerException {

		// construct original partial rectangles for each north wall
		for (Wall northWall : wallContainerNorth) {
			double yNorth = northWall.getP1().getY();

			// find south wall
			double xWest = northWall.getP2().getX();
			double xEast = northWall.getP1().getX();
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xWest + findWallTOL, xEast - findWallTOL, yNorth);
			double ySouth = nextSouthWall.getP1().getY();

			// add this original partial rectangle
			addOriginalPartialRectangle(yNorth, xWest, xEast, ySouth);
		}

		// construct original partial rectangles for each east wall
		for (Wall eastWall : wallContainerEast) {

			// find north and south wall
			double xEast = eastWall.getP1().getX();


			// find west wall
			double ySouth = eastWall.getP1().getY();
			double yNorth = eastWall.getP2().getY();

			Wall nextWestWall = wallContainerWest.getNearestWall(ySouth + findWallTOL, yNorth - findWallTOL, xEast);
			double xWest = nextWestWall.getP1().getX();

			// add this original partial rectangle
			addOriginalPartialRectangle(yNorth, xWest, xEast, ySouth);
		}

		// construct original partial rectangles for each west wall
		for (Wall westWall : wallContainerWest) {

			// find north and south wall
			double xWest = westWall.getP1().getX();

			// find east wall
			double ySouth = westWall.getP2().getY();
			double yNorth = westWall.getP1().getY();

			Wall nextEastWall = wallContainerEast.getNearestWall(ySouth + findWallTOL, yNorth - findWallTOL, xWest);
			double xEast = nextEastWall.getP1().getX();

			// add this original partial rectangle
			addOriginalPartialRectangle(yNorth, xWest, xEast, ySouth);
		}

		// construct original partial rectangles for each south wall
		for (Wall southWall : wallContainerSouth) {

			// find east and west walls
			double ySouth = southWall.getP1().getY();


			double xEast = southWall.getP2().getX();
			double xWest = southWall.getP1().getX();

			// find north wall
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xWest + findWallTOL, xEast - findWallTOL, ySouth);
			double yNorth = nextNorthWall.getP1().getY();

			// add this original partial rectangle
			addOriginalPartialRectangle(yNorth, xWest, xEast, ySouth);
		}
	}

	/**
	 * Finds tags for original rectangles and adds it to global original rectangles
	 * if this rectangle does not already exist
	 * 
	 * @param yNorth
	 * @param xWest
	 * @param xEast
	 * @param ySouth
	 */
	private void addOriginalPartialRectangle(double yNorth, double xWest, double xEast, double ySouth) {
		Point southWestCorner = new Point(xWest, ySouth);
		Point northEastCorner = new Point(xEast, yNorth);
		Rectangle partialRectangle = new Rectangle(southWestCorner, northEastCorner);

		if (!originalRectangles.contains(partialRectangle)) { // same rectangle does not already exist -> add
			originalRectanglesTagged.add(new RectangleWithTag(partialRectangle, 0));
			// include all tags of this new rectangle to the tags of the room
		}
	}

	

	// for testing
	public Iterator<RectangleWithTag> iteratorOriginalRectangles() {
		return originalRectanglesTagged.iterator();
	}

	// for testing
	Iterator<Wall> eastIterator() {
		return wallContainerEast.iterator();
	}

	Iterator<Wall> northIterator() {
		return wallContainerNorth.iterator();
	}

	Iterator<Wall> westIterator() {
		return wallContainerWest.iterator();
	}

	Iterator<Wall> southIterator() {
		return wallContainerSouth.iterator();
	}

}
