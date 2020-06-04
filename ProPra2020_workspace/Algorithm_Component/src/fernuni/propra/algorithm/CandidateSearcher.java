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

public class CandidateSearcher  implements ICandidateSearcher{

	
	public CandidateSearcher() {
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
		
		
		
		ArrayList<RectangleWithTag> lastIteration = originalRectanglesTagged;
		ArrayList<RectangleWithTag> minRectangleWithTags = null;
		
	
		
		boolean intersectFound;
		do {
		
		intersectFound = false;
		ArrayList<RectangleWithTag>intersectedRectangleWithTags = new ArrayList<RectangleWithTag>();
		for (int i = 0; i < lastIteration.size(); i++) {
			RectangleWithTag rectangleWithTagI = lastIteration.get(i);
			boolean intersectFoundI = false;
			for (int j = i+1; j< lastIteration.size(); j++) {
				RectangleWithTag rectangleWithTagJ = lastIteration.get(j);
				
				Rectangle overlappingRectangle = rectangleWithTagI.overlap(rectangleWithTagJ);
				
				if(overlappingRectangle != null) { // intersection detected
					
					intersectFoundI = true;
					
					
					// determine tags of overlap
					HashSet<Integer> tagsOfOverlap= new HashSet<Integer>();
					tagsOfOverlap.addAll(rectangleWithTagI.getCopyOfTags());
					tagsOfOverlap.addAll(rectangleWithTagJ.getCopyOfTags());
					
					// determine all rectangles that also contain center of overlapping rectangle
					
					for (int k = j+1; k < lastIteration.size(); k++) {
						RectangleWithTag rectangleWithTagK = lastIteration.get(k);
						if(overlappingRectangle.getCenter().isInsideRectangle(rectangleWithTagK.getP1(), rectangleWithTagK.getP3()) ) {
							tagsOfOverlap.addAll(rectangleWithTagK.getCopyOfTags());
						}
					}
					
					// add to all intersections of rec_i
					RectangleWithTag overlappingRectangleWithTag = new RectangleWithTag(overlappingRectangle, tagsOfOverlap);
					
					intersectedRectangleWithTags.add(overlappingRectangleWithTag);
									
				}
				
			}
			if (!intersectFoundI) {
				intersectedRectangleWithTags.add(rectangleWithTagI);
			}
		}
		
		
		
		
		// add only those rectangles that are not contained in another rectangle
		minRectangleWithTags = new ArrayList<RectangleWithTag>();
		boolean[] isMinRectangle = new boolean[intersectedRectangleWithTags.size()]; 
		for (int i = 0; i<isMinRectangle.length; i++) {
			isMinRectangle[i] = true;;
		}
		
		for (int i = 0; i<intersectedRectangleWithTags.size(); i++) {
			RectangleWithTag rectangleWithTagI = intersectedRectangleWithTags.get(i);
			
		
			for (int j = i+1; j < intersectedRectangleWithTags.size(); j++) {
				RectangleWithTag rectangleWithTagJ = intersectedRectangleWithTags.get(j);
				boolean iSubsetOfJ = rectangleWithTagJ.getCopyOfTags().containsAll(rectangleWithTagI.getCopyOfTags());
				boolean jSubsetOfI = rectangleWithTagI.getCopyOfTags().containsAll(rectangleWithTagJ.getCopyOfTags());
				
				if (iSubsetOfJ && jSubsetOfI) { // equal
					isMinRectangle[i] = false;
				} else if (iSubsetOfJ) {
					isMinRectangle[i] = false;
				} else if(jSubsetOfI) {
					isMinRectangle[j] = false;
				}
			}
			
		}
		
		for (int i = 0; i<isMinRectangle.length; i++) {
			if (isMinRectangle[i]) {
				minRectangleWithTags.add(intersectedRectangleWithTags.get(i));
			} else {
				intersectFound = true; // overlap detected
			}
		}
		
		lastIteration = minRectangleWithTags;
		
		} while(intersectFound);
		
		return minRectangleWithTags;
	}	
	

}
