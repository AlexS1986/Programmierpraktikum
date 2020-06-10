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

public class CandidateSearcher_old  implements ICandidateSearcher{

	
	public CandidateSearcher_old() {
	}

	@Override
	public List<Lamp> searchCandidates(IRoom room, IRuntimeCandidateSearcher runtimeCandidateSearcher) throws CandidateSearcherException, InterruptedException {
		// computes lists of tagged list of lamps at candidate positions
		
		List<Lamp> centersOfReducedRectangles = null;
		try {
			runtimeCandidateSearcher.startTimeOriginalPartialRectanglesFind();
			ArrayList<RectangleWithTag> originalRectangles = AbstractAlgorithmFactory.getAlgorithmFactory().createOriginalPartialRectanglesFinder().findOriginalPartialRectangles(room, runtimeCandidateSearcher);
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

	
	

	
	List<RectangleWithTag> reduceRectangles(ArrayList<RectangleWithTag> originalRectangles) throws InterruptedException{
		
		// TODO die die im letzten schritt keinen overlap hatten müssen auch nicht mehr angeschaut werden
		
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
					
					
					if (Thread.currentThread().isInterrupted()) {
						throw new InterruptedException();
					}
					
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
		
		

}
