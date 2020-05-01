package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;

public class CandidateSearcher2 extends CandidateSearcher{

	public CandidateSearcher2() {
		super();
	}
	
	@Override
	List<RectangleWithTag> reduceRectangles(ArrayList<RectangleWithTag> originalRectangles) {
		// iterates over all original partial rectangles and computes the overlap with all rectangles in the combinedSet
		// the new combinedSet includes all tagged overlaps as well as rectangles for which no overlap could be found in the current iteration.
		
		// pre originalRectangles != null && originalRectangles.size()>0
		if (originalRectangles == null || originalRectangles.size() == 0) throw new IllegalArgumentException("No original rectangles provided");
		HashSet<RectangleWithTag> combinedRectanglesWithTagLastIteration = new HashSet<RectangleWithTag>();
		//boolean combinationOccured = false;
		for (int i = 0; i< originalRectangles.size(); i++) {
			RectangleWithTag rectangleWithTagI = originalRectangles.get(i);
			boolean combinationOccured = false;
			Iterator<RectangleWithTag> combinedRectanglesIterator = combinedRectanglesWithTagLastIteration.iterator();
			HashSet<RectangleWithTag> combinedRectanglesWithTagThisIteration = new HashSet<RectangleWithTag>();
			while(combinedRectanglesIterator.hasNext()) {
				RectangleWithTag combinedRectangleWithTag = combinedRectanglesIterator.next();
				Rectangle overlappingRectangle = rectangleWithTagI.overlap(combinedRectangleWithTag); // find overlap
				
				if (overlappingRectangle != null) { 
					combinationOccured = true;
					
					// determine new rectangle and tag it
					HashSet<Integer> tagsOfOverlap= new HashSet<Integer>();
					Iterator<Integer> tagsI = rectangleWithTagI.getTagIterator();
					Iterator<Integer> tagsJ = combinedRectangleWithTag.getTagIterator();	
					while(tagsI.hasNext()) {
						tagsOfOverlap.add(tagsI.next());
					}
					while(tagsJ.hasNext()) {
						tagsOfOverlap.add(tagsJ.next());
					}
					RectangleWithTag overlappingRectangleWithTag = new RectangleWithTag(overlappingRectangle, tagsOfOverlap);
					combinedRectanglesWithTagThisIteration.add(overlappingRectangleWithTag);
				} else { // no overlap -> need to keep combinedRectangle in set
					combinedRectanglesWithTagThisIteration.add(combinedRectangleWithTag);
				}
				
			}
			if (!combinationOccured) { // no overlap occured
				combinedRectanglesWithTagThisIteration.add(rectangleWithTagI);
			} else { // overlap(s) occured > part of rectangleWithTagI has already been added 
				
			} 
			combinedRectanglesWithTagLastIteration = combinedRectanglesWithTagThisIteration;
		}
		return new LinkedList<RectangleWithTag>(combinedRectanglesWithTagLastIteration);
	}

}