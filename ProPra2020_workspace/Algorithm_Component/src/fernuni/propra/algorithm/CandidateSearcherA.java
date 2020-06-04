package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimeCandidateSearcher;
import fernuni.propra.algorithm.runtime_information.RuntimeExceptionLamps;
import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;

public class CandidateSearcherA  implements ICandidateSearcher{

	
	public CandidateSearcherA() {
	}

	@Override
	public List<Lamp> searchCandidates(IRoom room, IRuntimeCandidateSearcher runtimeCandidateSearcher) throws CandidateSearcherException, InterruptedException {
		// computes lists of tagged list of lamps at candidate positions
		
		List<Lamp> centersOfReducedRectangles = null;
		try {
			runtimeCandidateSearcher.startTimeOriginalPartialRectanglesFind();
			ArrayList<RectangleWithTag> originalRectangles = constructOriginalPartialRectangles(room, runtimeCandidateSearcher);
			runtimeCandidateSearcher.stopTimeOriginalPartialRectanglesFind();
			
			List<RectangleWithTag> reducedRectangles = reduceRectangles(originalRectangles);
			
			centersOfReducedRectangles = new LinkedList<Lamp>();
			for (RectangleWithTag rectangle : reducedRectangles) {
				Point point = rectangle.getCenter();
				Lamp lamp = new Lamp(point.getX(), point.getY());
				Iterator<Integer> tagsOfRectangleIterator = rectangle.getTagIterator();
				while(tagsOfRectangleIterator.hasNext()) {
					lamp.addTag(tagsOfRectangleIterator.next());
				}
				centersOfReducedRectangles.add(lamp);
			}
			
		} catch (OriginalPartialRectanglesFinderException  e) {
			throw new CandidateSearcherException(e);
		}	catch (RuntimeExceptionLamps rte) {
			throw new CandidateSearcherException(rte);
		}
		
		return centersOfReducedRectangles;		
	}

	
	ArrayList<RectangleWithTag> constructOriginalPartialRectangles(IRoom room, IRuntimeCandidateSearcher runtimeCandidateSearcher) throws OriginalPartialRectanglesFinderException {
		return new OriginalPartialRectanglesFinder().findOriginalPartialRectangles(room, runtimeCandidateSearcher);
	}

	
	ArrayList<RectangleWithTag> reduceRectangles(ArrayList<RectangleWithTag> originalRectanglesTagged) throws InterruptedException{
		 
		HashSet<RectangleWithTag> outRectangles = new HashSet<RectangleWithTag>();
		for (int i = 0; i< originalRectanglesTagged.size(); i++) {
			
			boolean combinationsOccuredI = false; // if no combinations occurred for rectangle I then save to output
			RectangleWithTag rectangleWithTagI = originalRectanglesTagged.get(i);
			HashSet<RectangleWithTag> intersectedRectanglesI = new HashSet<RectangleWithTag>();
			
			for (int j = i+1; j < originalRectanglesTagged.size(); j++) {
				RectangleWithTag rectangleWithTagJ = originalRectanglesTagged.get(j);
				
				// compute intersection of rec_i with rec_j
				Rectangle overlappingRectangle = rectangleWithTagI.overlap(rectangleWithTagJ);
				
				if(overlappingRectangle != null) { // intersection detected
					combinationsOccuredI = true;
					
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
					
					// add to all intersections of rec_i
					RectangleWithTag overlappingRectangleWithTag = new RectangleWithTag(overlappingRectangle, tagsOfOverlap);
					
					intersectedRectanglesI.add(overlappingRectangleWithTag);
					
						
				}
						
			}
			
			if (combinationsOccuredI == false) {
				intersectedRectanglesI.add(rectangleWithTagI);
			} else { // combinations occurred test reduced rectangles for intersection
				intersectedRectanglesI = new HashSet<RectangleWithTag>(reduceRectangles(new ArrayList<RectangleWithTag>(intersectedRectanglesI)));
			}
			
			outRectangles.addAll(intersectedRectanglesI);
		}
		
		return new ArrayList<RectangleWithTag>(outRectangles);
		
	}
		
		

}
