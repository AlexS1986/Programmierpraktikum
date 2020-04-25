package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class OriginalPartialRectanglesFinder implements IOriginalPartialRectanglesFinder{
	
	private static double findWallTOL = 0.001;
	private HashSet<Integer> allTags = new HashSet<Integer>();
	private WallContainerEast wallContainerEast  = new WallContainerEast();
	private WallContainerNorth wallContainerNorth = new WallContainerNorth();
	private WallContainerWest wallContainerWest = new WallContainerWest();
	private WallContainerSouth wallContainerSouth = new WallContainerSouth();
	private ArrayList<RectangleWithTag> originalRectangles = new ArrayList<RectangleWithTag>();

	public OriginalPartialRectanglesFinder() {
		// TODO Auto-generated constructor stub
	}
	
	//public static OriginalPartialRectanglesFinder getOriginalPartialRectanglesFinder() {
	//	if (singleton == null) {
	//		singleton = new OriginalPartialRectanglesFinder();
	//	}
	//	return singleton;
	//}
	
	@Override
	public ArrayList<RectangleWithTag> findOriginalPartialRectangles(IRoom room, IRuntimeOriginalPartialRectanglesFinder rti) throws OriginalPartialRectanglesFinderException {
		try {
			sortWallsToContainers(room);
			constructOriginalPartialRectangles();
		} catch (WallContainerException | OriginalPartialRectanglesFinderException e) {
			throw new OriginalPartialRectanglesFinderException(e);
		}
		
		return originalRectangles;
	}

	@Override
	public HashSet<Integer> getAllTags() { // TODO findOriginalPartialRectangles needs to be called first
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
			
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
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
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
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
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
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
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}		
	} 
	
	private int addOriginalPartialRectangle(int rectangleNo, double yNorth, double xWest, double xEast, double ySouth) {
		Point southWestCorner = new Point(xWest,ySouth);
		Point northEastCorner = new Point(xEast,yNorth);
		int tag = rectangleNo++;
		RectangleWithTag partialRectangle = new RectangleWithTag(southWestCorner, northEastCorner, tag);
		allTags.add(tag);
		originalRectangles.add(partialRectangle);
		return rectangleNo;
	}
	
	public Iterator<RectangleWithTag> iteratorOriginalRectangles() {
		return originalRectangles.iterator();
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
