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
 * tags all original partial rectangles with tags of covered walls
 * @author alex
 *
 */
public class OriginalPartialRectanglesFinder implements IOriginalPartialRectanglesFinder{
	
	private static double findWallTOL = 0.001;
	private HashSet<Integer> allTags = new HashSet<Integer>();
	private WallContainerEast wallContainerEast  = new WallContainerEast();
	private WallContainerNorth wallContainerNorth = new WallContainerNorth();
	private WallContainerWest wallContainerWest = new WallContainerWest();
	private WallContainerSouth wallContainerSouth = new WallContainerSouth();
	private HashSet<Rectangle> originalRectangles = new HashSet<Rectangle>();
	private ArrayList<RectangleWithTag> originalRectanglesTagged = new ArrayList<RectangleWithTag>();
	
	@Override
	public ArrayList<RectangleWithTag> findOriginalPartialRectangles(IRoom room, IRuntimeOriginalPartialRectanglesFinder rti) throws OriginalPartialRectanglesFinderException {
		try {
			sortWallsToContainers(room);
			constructOriginalPartialRectangles();
		} catch (WallContainerException | OriginalPartialRectanglesFinderException e) {
			throw new OriginalPartialRectanglesFinderException(e);
		}
		
		return originalRectanglesTagged;
	}

	@Override
	public HashSet<Integer> getAllTags() { 
		return allTags;
	
	}
	
	void sortWallsToContainers(IRoom room) throws WallContainerException, OriginalPartialRectanglesFinderException {
		Iterator<Wall> wallIterator = room.getWalls();
		while(wallIterator.hasNext()) {
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
				throw new OriginalPartialRectanglesFinderException("Wall orientation cannot be determined! Wall might not be horizontal or vertical");
			}
		}
	}
	
	void constructOriginalPartialRectangles() throws WallContainerException {
		
		int rectangleNo = 0;
		
		for( Wall northWall : wallContainerNorth) {
			double yNorth = northWall.getP1().getY();
			double westXLimit = northWall.getP2().getX();
			double eastXLimit = northWall.getP1().getX();
			
			Wall nextWestWall = wallContainerWest.getNearestWall(yNorth - findWallTOL, 
					yNorth - findWallTOL, westXLimit);
			
			Wall nextEastWall = wallContainerEast.getNearestWall(yNorth - findWallTOL, yNorth - findWallTOL, eastXLimit);
			
			double xWest = nextWestWall.getP1().getX();
			double xEast = nextEastWall.getP1().getX();
			
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xWest+findWallTOL, xEast-findWallTOL, yNorth);
			double ySouth = nextSouthWall.getP1().getY();
			
			
			addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}
		
		for (Wall eastWall: wallContainerEast) {
			double xEast = eastWall.getP1().getX();
			double southYLimit = eastWall.getP1().getY();
			double northYLimit = eastWall.getP2().getY();
			
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xEast- findWallTOL, xEast - findWallTOL, southYLimit);
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xEast- findWallTOL, xEast - findWallTOL, northYLimit);
			
			double ySouth = nextSouthWall.getP1().getY();
			double yNorth = nextNorthWall.getP1().getY();
			
			Wall nextWestWall = wallContainerWest.getNearestWall(ySouth+findWallTOL, yNorth-findWallTOL, xEast);
			double xWest = nextWestWall.getP1().getX();
			
			addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}
		
		
		for (Wall westWall: wallContainerWest) {
			double xWest = westWall.getP1().getX();
			double southYLimit = westWall.getP2().getY();
			double northYLimit = westWall.getP1().getY();
			
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xWest + findWallTOL, xWest + findWallTOL, southYLimit);
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xWest + findWallTOL, xWest + findWallTOL, northYLimit);
			
			double ySouth = nextSouthWall.getP1().getY();
			double yNorth = nextNorthWall.getP1().getY();
			
			Wall nextEastWall = wallContainerEast.getNearestWall(ySouth+findWallTOL, yNorth-findWallTOL, xWest);
			double xEast = nextEastWall.getP1().getX();
			
			addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}
		
		for (Wall southWall: wallContainerSouth) {
			double ySouth = southWall.getP1().getY();
			double eastXLimit = southWall.getP2().getX();
			double westXLimit = southWall.getP1().getX();
			
			Wall nextEastWall = wallContainerEast.getNearestWall(ySouth + findWallTOL, ySouth + findWallTOL, eastXLimit);
			Wall nextWestWall = wallContainerWest.getNearestWall(ySouth + findWallTOL, ySouth + findWallTOL, westXLimit);
			
			double xEast = nextEastWall.getP1().getX();
			double xWest = nextWestWall.getP1().getX();
			
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xWest+findWallTOL, xEast-findWallTOL, ySouth);
			double yNorth = nextNorthWall.getP1().getY();
			
			addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}		
	} 
	
	private void addOriginalPartialRectangle(int rectangleNo,double yNorth, double xWest, double xEast, double ySouth) {
		Point southWestCorner = new Point(xWest,ySouth);
		Point northEastCorner = new Point(xEast,yNorth);
		Rectangle partialRectangle = new Rectangle(southWestCorner, northEastCorner);
		
		
		if (!originalRectangles.contains(partialRectangle)) { // same rectangle does not already exist -> add
			HashSet<Integer> allWallsCoveredByRectangleBoundary = new HashSet<Integer>();
			
			findTagsOfAllCoveredWalls(partialRectangle, allWallsCoveredByRectangleBoundary);
			

			originalRectangles.add(partialRectangle);
			originalRectanglesTagged.add(new RectangleWithTag(partialRectangle, allWallsCoveredByRectangleBoundary));
			allTags.addAll(allWallsCoveredByRectangleBoundary);
		}
	}

	private void findTagsOfAllCoveredWalls(Rectangle partialRectangle,
			HashSet<Integer> allWallsCoveredByRectangleBoundary) {
		
		//check east walls
		LineSegment eastWall = new LineSegment(partialRectangle.getP2(), partialRectangle.getP3());
		for (Wall wall : wallContainerEast) {
			if (wall.getP1().isOnLineSegment(eastWall) && wall.getP2().isOnLineSegment(eastWall)) { // wall of current rectangles covers a wall of of the room -> if the rectangle is illuminated then this wall is also illuminated
				allWallsCoveredByRectangleBoundary.add(wall.getTag());
				allTags.add(wall.getTag());
			}
		}
		
		//check north walls
		LineSegment northWall = new LineSegment(partialRectangle.getP3(), partialRectangle.getP4());
		for (Wall wall : wallContainerNorth) {
			if (wall.getP1().isOnLineSegment(northWall) && wall.getP2().isOnLineSegment(northWall)) { // wall of current rectangles covers a wall of of the room -> if the rectangle is illuminated then this wall is also illuminated
				allWallsCoveredByRectangleBoundary.add(wall.getTag());
				allTags.add(wall.getTag());
			}
		}
		
		//check west walls
		LineSegment westWall = new LineSegment(partialRectangle.getP4(), partialRectangle.getP1());
		for (Wall wall : wallContainerWest) {
			if (wall.getP1().isOnLineSegment(westWall) && wall.getP2().isOnLineSegment(westWall)) { // wall of current rectangles covers a wall of of the room -> if the rectangle is illuminated then this wall is also illuminated
				allWallsCoveredByRectangleBoundary.add(wall.getTag());
				allTags.add(wall.getTag());
			}
		}
		
		//check south walls
		LineSegment southWall = new LineSegment(partialRectangle.getP1(), partialRectangle.getP2());
		for (Wall wall : wallContainerSouth) {
			if (wall.getP1().isOnLineSegment(southWall) && wall.getP2().isOnLineSegment(southWall)) { // wall of current rectangles covers a wall of of the room -> if the rectangle is illuminated then this wall is also illuminated
				allWallsCoveredByRectangleBoundary.add(wall.getTag());
				allTags.add(wall.getTag());
			}
		}
	}
	
	public Iterator<RectangleWithTag> iteratorOriginalRectangles() {
		return originalRectanglesTagged.iterator();
	}
	
	// TODO for tests
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
