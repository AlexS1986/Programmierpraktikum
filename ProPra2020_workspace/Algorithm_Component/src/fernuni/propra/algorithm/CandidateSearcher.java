package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class CandidateSearcher  implements ICandidateSearcher{
	private WallContainerEast wallContainerEast  = new WallContainerEast();
	private WallContainerNorth wallContainerNorth = new WallContainerNorth();
	private WallContainerWest wallContainerWest = new WallContainerWest();
	private WallContainerSouth wallContainerSouth = new WallContainerSouth();
	private ArrayList<RectangleWithTag> originalRectangles = new ArrayList<RectangleWithTag>();

	
	

	@Override
	public List<Point> searchCandidates(IRoom room, IRuntimeCandidateSearcher runtimeCandidateSearcher) throws CandidateSearcherException {
		
		
		try {
			sortWallsToContainers(room);
			
			constructOriginalPartialRectangles();
			
			reduceRectangles();
			
			
		} catch (WallContainerException  wce) {
			throw new CandidateSearcherException(wce);
		}
		
		return null;	
		
	}



	/* void reduceRectangles() {
		
		HashSet<RectangleWithTag> reducedRectangles = new HashSet<RectangleWithTag>();
		ArrayList<RectangleWithTag> lastIterationReducedRectangles = originalRectangles;
		
		
		HashSet<Integer> originalTagsAlreadyConsideredInOverLap = new HashSet<Integer>();
		
		boolean combinationsOccured = false; // to determine to stop further iterations
		for (int i = 0; i< lastIterationReducedRectangles.size(); i++) {
			RectangleWithTag rectangleWithTagI = lastIterationReducedRectangles.get(i);
			boolean validOverlapFoundForI = false; // if no valid overlap found -> keep partial area
			for (int j = i+1; j< lastIterationReducedRectangles.size(); j++) {
				RectangleWithTag rectangleWithTagJ = lastIterationReducedRectangles.get(j);
				Rectangle overlappingRectangle = rectangleWithTagI.overlap(rectangleWithTagJ);	
				
				if (overlappingRectangle != null) { // overlap found
					HashSet<Integer> tagsOfOverlap= new HashSet<Integer>();
				
					Iterator<Integer> tagsI = rectangleWithTagI.getTagIterator();
					Iterator<Integer> tagsJ = rectangleWithTagJ.getTagIterator();
					
					while(tagsI.hasNext()) {
						tagsOfOverlap.add(tagsI.next());
					}
					
					while(tagsJ.hasNext()) {
						tagsOfOverlap.add(tagsJ.next());
					}
					
					RectangleWithTag newRectangleWithTag = new RectangleWithTag(overlappingRectangle, null);
					newRectangleWithTag.addTags(tagsOfOverlap.iterator());
					

					
					if (newRectangleWithTag.getCopyOfTags().containsAll(rectangleWithTagI.getCopyOfTags())) { // tags of rI are subset of overlap
						reducedRectangles.add(newRectangleWithTag);
						originalTagsAlreadyConsideredInOverLap.addAll(tagsOfOverlap);
						combinationsOccured = true;
						validOverlapFoundForI = true;
					}
							
				}
			}
			
			if (!validOverlapFoundForI && !originalTagsAlreadyConsideredInOverLap.containsAll(rectangleWithTagI.getCopyOfTags())) {
				reducedRectangles.add(rectangleWithTagI);
			}
			
		
		}
		
	} */
	
	
	List<RectangleWithTag> reduceRectangles() {
		
		// die die im letzten schritt keinen overlap hatten müssen auch nicht mehr angeschaut werden
		
		
		ArrayList<RectangleWithTag> lastIterationRectangles = originalRectangles;
		
		
		boolean combinationsOccured; // to determine to stop further iterations
		do {
			
			combinationsOccured = false;
			HashSet<RectangleWithTag> currentIterationRectangles = new HashSet<RectangleWithTag>();
			
			HashSet<Integer> originalTagsAlreadyCoveredInIteration = new HashSet<Integer>();
			
			
			for (int i = 0; i< lastIterationRectangles.size(); i++) {
				RectangleWithTag rectangleWithTagI = lastIterationRectangles.get(i);
				boolean validOverlapFoundForI = false; // if no valid overlap found -> keep partial area
				for (int j = i+1; j< lastIterationRectangles.size(); j++) {
					RectangleWithTag rectangleWithTagJ = lastIterationRectangles.get(j);
					Rectangle overlappingRectangle = rectangleWithTagI.overlap(rectangleWithTagJ);	
					
					if (overlappingRectangle != null) { // overlap found
						
						// determine tags of overlap
						HashSet<Integer> tagsOfOverlap= new HashSet<Integer>();
						Iterator<Integer> tagsI = rectangleWithTagI.getTagIterator();
						Iterator<Integer> tagsJ = rectangleWithTagJ.getTagIterator();	
						while(tagsI.hasNext()) {
							tagsOfOverlap.add(tagsI.next());
						}
						while(tagsJ.hasNext()) {
							tagsOfOverlap.add(tagsJ.next());
						}
						
						RectangleWithTag overlappingRectangleWithTag = new RectangleWithTag(overlappingRectangle, tagsOfOverlap);
						
						//add to next iteration rectangles if rectangle does not already exist
						if (!currentIterationRectangles.contains(overlappingRectangleWithTag)) { 
							currentIterationRectangles.add(overlappingRectangleWithTag);
							originalTagsAlreadyCoveredInIteration.addAll(overlappingRectangleWithTag.getCopyOfTags()); // all original rectangles are now covered
							combinationsOccured = true;
							validOverlapFoundForI = true;
						}
									
					}
				}
				
				if (!validOverlapFoundForI && !originalTagsAlreadyCoveredInIteration.containsAll(rectangleWithTagI.getCopyOfTags())) { // TODO test for contains necessary? 
					currentIterationRectangles.add(rectangleWithTagI);
					originalTagsAlreadyCoveredInIteration.addAll(rectangleWithTagI.getCopyOfTags());
				}
			}
			
			lastIterationRectangles = new ArrayList<RectangleWithTag>(currentIterationRectangles);
			
			
		} while(combinationsOccured);
		
		return lastIterationRectangles;
	}
		
		
		
		/*boolean combinationsOccured = false;
		for (int i = 0; i< lastIterationReducedRectangles.size(); i++) {
			RectangleWithTag rectangleWithTagI = lastIterationReducedRectangles.get(i);
			for (int j = i+1; j< lastIterationReducedRectangles.size(); j++) {
				RectangleWithTag rectangleWithTagJ = lastIterationReducedRectangles.get(j);
				Rectangle overlappingRectangle = rectangleWithTagI.overlap(rectangleWithTagJ);	
				if (overlappingRectangle != null) {
					combinationsOccured = true;
					
					if (!reducedRectangles.contains(overlappingRectangle)) { // rectangles that already have been created are not added again but only additional tags may be added
						Iterator<Integer> iTags = rectangleWithTagI.getTagIterator();
						Iterator<Integer> jTags = rectangleWithTagJ.getTagIterator();
						
						RectangleWithTag rectangleWithTag = new RectangleWithTag(overlappingRectangle, null); // TODO warum nicht gleich ein RectangleWithTag generieren?
						rectangleWithTag.addTags(jTags);
						rectangleWithTag.addTags(iTags);
						reducedRectangles.add(rectangleWithTag);
					} else {
						Iterator<Integer> iTags = rectangleWithTagI.getTagIterator();
						Iterator<Integer> jTags = rectangleWithTagJ.getTagIterator();
						
						RectangleWithTag rectangleWithTag = reducedRectangles.
					}
					
				}
			}
			
		} */
		
		
		// TODO Auto-generated method stub
		



	void sortWallsToContainers(IRoom room) throws WallContainerException, CandidateSearcherException {
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
				throw new CandidateSearcherException("Wall orientation cannot be determined!");
			}
		}
	}
	
	void constructOriginalPartialRectangles() throws WallContainerException {
		
		int rectangleNo = 0;
		
		for( Wall northWall : wallContainerNorth) {
			double yNorth = northWall.getP1().getY();
			double westXLimit = northWall.getP2().getX();
			double eastXLimit = northWall.getP1().getX();
			
			Wall nextWestWall = wallContainerWest.getNearestWall(yNorth, 
					yNorth, westXLimit);
			
			Wall nextEastWall = wallContainerEast.getNearestWall(yNorth, yNorth, eastXLimit);
			
			double xWest = nextWestWall.getP1().getX();
			double xEast = nextEastWall.getP1().getX();
			
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xWest, xEast, yNorth);
			double ySouth = nextSouthWall.getP1().getY();
			
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}
		
		for (Wall eastWall: wallContainerEast) {
			double xEast = eastWall.getP1().getX();
			double southYLimit = eastWall.getP1().getY();
			double northYLimit = eastWall.getP2().getY();
			
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xEast, xEast, southYLimit);
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xEast, xEast, northYLimit);
			
			double ySouth = nextSouthWall.getP1().getY();
			double yNorth = nextNorthWall.getP1().getY();
			
			Wall nextWestWall = wallContainerWest.getNearestWall(ySouth, yNorth, xEast);
			double xWest = nextWestWall.getP1().getX();
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}
		
		
		for (Wall westWall: wallContainerWest) {
			double xWest = westWall.getP1().getX();
			double southYLimit = westWall.getP2().getY();
			double northYLimit = westWall.getP1().getY();
			
			Wall nextSouthWall = wallContainerSouth.getNearestWall(xWest, xWest, southYLimit);
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xWest, xWest, northYLimit);
			
			double ySouth = nextSouthWall.getP1().getY();
			double yNorth = nextNorthWall.getP1().getY();
			
			Wall nextEastWall = wallContainerEast.getNearestWall(ySouth, yNorth, xWest);
			double xEast = nextEastWall.getP1().getX();
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}
		
		for (Wall southWall: wallContainerSouth) {
			double ySouth = southWall.getP1().getY();
			double eastXLimit = southWall.getP2().getX();
			double westXLimit = southWall.getP1().getX();
			
			Wall nextEastWall = wallContainerEast.getNearestWall(ySouth, ySouth, eastXLimit);
			Wall nextWestWall = wallContainerWest.getNearestWall(ySouth, ySouth, westXLimit);
			
			double xEast = nextEastWall.getP1().getX();
			double xWest = nextWestWall.getP1().getX();
			
			Wall nextNorthWall = wallContainerNorth.getNearestWall(xWest, xEast, ySouth);
			double yNorth = nextNorthWall.getP1().getY();
			
			rectangleNo = addOriginalPartialRectangle(rectangleNo, yNorth, xWest, xEast, ySouth);
		}	

	}



	private int addOriginalPartialRectangle(int rectangleNo, double yNorth, double xWest, double xEast, double ySouth) {
		Point southWestCorner = new Point(xWest,ySouth);
		Point northEastCorner = new Point(xEast,yNorth);
		List<Integer> initialTags = new ArrayList<Integer>(); initialTags.add(rectangleNo++);
		RectangleWithTag partialRectangle = new RectangleWithTag(southWestCorner, northEastCorner, initialTags);
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
