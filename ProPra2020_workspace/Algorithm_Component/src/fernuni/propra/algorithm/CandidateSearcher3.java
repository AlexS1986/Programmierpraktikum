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

public class CandidateSearcher3  implements ICandidateSearcher{

	
	public CandidateSearcher3() {
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
		
		ArrayList<RectangleWithTag> intersectedRectangleWithTags = new ArrayList<RectangleWithTag>();
		
		for (int i = 0; i < originalRectanglesTagged.size(); i++) {
			RectangleWithTag rectangleWithTagI = originalRectanglesTagged.get(i);
			for (int j = i+1; j< originalRectanglesTagged.size(); j++) {
				RectangleWithTag rectangleWithTagJ = originalRectanglesTagged.get(j);
				
				Rectangle overlappingRectangle = rectangleWithTagI.overlap(rectangleWithTagJ);
				
				if(overlappingRectangle != null) { // intersection detected
					
					
					// determine tags of overlap
					HashSet<Integer> tagsOfOverlap= new HashSet<Integer>();
					tagsOfOverlap.addAll(rectangleWithTagI.getCopyOfTags());
					tagsOfOverlap.addAll(rectangleWithTagJ.getCopyOfTags());
					
					// determine all rectangles that also contain center of overlapping rectangle
					
					for (int k = j+1; k < originalRectanglesTagged.size(); k++) {
						RectangleWithTag rectangleWithTagK = originalRectanglesTagged.get(k);
						if(overlappingRectangle.getCenter().isInsideRectangle(rectangleWithTagK.getP1(), rectangleWithTagK.getP3()) ) {
							tagsOfOverlap.addAll(rectangleWithTagK.getCopyOfTags());
						}
					}
					
					// add to all intersections of rec_i
					RectangleWithTag overlappingRectangleWithTag = new RectangleWithTag(overlappingRectangle, tagsOfOverlap);
					
					intersectedRectangleWithTags.add(overlappingRectangleWithTag);
					
						
				}
				
			}
		}
		
		ArrayList<RectangleWithTag> minRectangleWithTags = new ArrayList<RectangleWithTag>();
		
		for (int i = 0; i<intersectedRectangleWithTags.size(); i++) {
			RectangleWithTag rectangleWithTagI = intersectedRectangleWithTags.get(i);
			
			boolean containingRectangleFound = false;
			for (int j = 0; j < intersectedRectangleWithTags.size(); j++) {
				RectangleWithTag rectangleWithTagJ = intersectedRectangleWithTags.get(j);
				if (rectangleWithTagJ.getCopyOfTags().containsAll(rectangleWithTagI.getCopyOfTags()) && (i!=j)) {
					containingRectangleFound = true;
				}
			}
			
			if (!containingRectangleFound) {
				minRectangleWithTags.add(rectangleWithTagI);
			}
		}
		
		return minRectangleWithTags;
	}	
	

}