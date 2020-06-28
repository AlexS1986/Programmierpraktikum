package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimePositionOptimizer;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

/**
 * A specific provider of an algorithm that finds a minimum set (and number) of tagged {@link Lamp}s that 
 * illuminates an {@link IRoom} instance.
 * <p>
 * The algorithm works as follows:
 * <p>
 * 1.) Global (to this class) fields are introduced 
 * that store the currently best solution ({@link List}<{@link Lamp}>) and the number of {@link Lamp}s
 * that are turned on in the currently best solution
 * <p>
 * 2.) All {@link Lamp}s in the supplied list are turned on (i.e. the {@link IRoom} represented by the portions
 *     represented by the tags of the {@link Lamp}s is illuminated. The number of illuminated {@link Lamp}s is
 *     consequently set to the size of the originally supplied set of lamps.
 * <p>
 * 3.) An index idx that can be used to navigate the set of {@link Lamp}s is introduced (idx = 0 initially)
 *     and the ideal configuration of {@link Lamp}s is computed by recursively calling the 
 *     method searchSolution in the manner of a backtracking algorithm. The method follows the following pseudo-code
 *     <p>
 *     PROCEDURE searchSolution (lamps, idx) {
       <p>
       if ( check if all portions of room are illuminated) { // if not all other branches cannot illuminate the room either
       <p>
            if(number of turned on lamps < number of on lamps in best solution) {
       <p>
            best solution  = lamps
       <p>
            number of lamps in best solution = number of turned on lamps } 
		<p>
        if (idx < size of lamps) {
        <p>
            searchSolution(lamps, idx+1)
        <p>
            turn off lamp[idx]
        <p>
            searchSolution(lamps, idx+1)
        <p>
        } 
        <p>  
    }
    <p>
  } 
 <p>
 * 4.) The computation can be interrupted by interrupting the exectuting thread. The computation will
 *     stop immediately with an {@link InterruptedException}.
 * <p>
 * 5.) The currently available best solution can now be obtained.    
 *      
 * <p> 
 * Implemented interfaces and super classes: {@link IPositionOptimizer}
 * 
 * @author alex
 *
 */
public class PositionOptimizer implements IPositionOptimizer{
	private static List<Lamp> currentBestSolution;
	private static int numberIlluminatedLampsBestSolution;
	private static IIlluminationTester illuminationTester = AbstractAlgorithmFactory.getAlgorithmFactory().createIlluminiationTester();

	public PositionOptimizer() {
	}

	@Override
	public List<Lamp> optimizePositions(List<Lamp> taggedCandidates, IRuntimePositionOptimizer runTimeInformation) throws InterruptedException
	{
 
		// all lamps are on -> illuminated
		currentBestSolution = taggedCandidates;
		numberIlluminatedLampsBestSolution = taggedCandidates.size();
		
		
		HashSet<Integer> allTags = new HashSet<Integer>();
		for (Lamp lamp : taggedCandidates) {
			lamp.turnOn(); // make sure all lamps are turned on
			Iterator<Integer> tagIterator = lamp.iteratorTag();
			while(tagIterator.hasNext()) {
				allTags.add(tagIterator.next());
			}
		}
		
		ArrayList<Lamp> lamps = deepCopyLamps(taggedCandidates);
		searchSolution(lamps,0, allTags, numberIlluminatedLampsBestSolution,runTimeInformation);
		
		return currentBestSolution;
		
		 
		
	}
	
	/**
	 * Implements the backtracking algorithm as indicated in the commentary on the {@link PositionOptimizer} class.
	 * @param lamps : the set of lamps for which an optimal configuration is to be found
	 * @param idx : index of current iteration
	 * @param allTags : a set of all portions of the room that need to be illuminated
	 * @param numberLampsOn : number of lamps that are turned on in the provided lamps argument
	 * @param runTimeInformation : a data structure that can be used to store runtime information
	 * @throws InterruptedException
	 */
	private void searchSolution(ArrayList<Lamp> lamps, int idx, 
			HashSet<Integer> allTags, int numberLampsOn, IRuntimePositionOptimizer runTimeInformation) throws InterruptedException{
		if(Thread.currentThread().isInterrupted()) {
			throw new InterruptedException("Computation interrupted.");
		}
		
		if(illuminationTester.testIfRoomIsIlluminated(lamps.iterator(), allTags, runTimeInformation)) { /* valid solution found, else case 
			does not need to be investigated since it all lamps with an idx larger than the supplied idx are already turned on*/
			if (numberLampsOn<numberIlluminatedLampsBestSolution) { // new best solution found
				System.out.println("Solution found with " + numberLampsOn + " lamps turned on.");
				currentBestSolution = deepCopyLamps(lamps);
				numberIlluminatedLampsBestSolution = numberLampsOn;		
			} 
			if (idx < lamps.size()) { // there are further configurations that can be investigated in this branch
				Lamp lamp = lamps.get(idx);
				//branch 1
				// lamp does not need to be turned on since it has been initialized as turned on
				searchSolution(deepCopyLamps(lamps), idx+1, allTags, numberLampsOn, runTimeInformation);
				
				//branch2
				lamp.turnOff();
				searchSolution(deepCopyLamps(lamps), idx+1, allTags, numberLampsOn-1, runTimeInformation);
				
			}
						
		} else { // not a valid solution, with all lamps > idx turned on 

		}
	}
	
	/**
	 * Provides funtionality to deep copy an ArrayList of {@link Lamp}s
	 * @param lamps
	 * @return a deep copy of the provided list of {@link Lamp}s
	 */
	private static ArrayList<Lamp> deepCopyLamps(List<Lamp> lamps) {
		ArrayList<Lamp> outLamps = new ArrayList<Lamp>(lamps.size());
		Iterator<Lamp> lampsIterator = lamps.iterator();
		while(lampsIterator.hasNext()) {
			Lamp lamp = lampsIterator.next();
			outLamps.add(lamp.deepCopy());
		}
		return outLamps;
	}
	
	
	private static HashSet<Integer> deepCopyHashSet(HashSet<Integer> hashSet) {
		HashSet<Integer> outHashSet = new HashSet<Integer>();
		for (Integer integer : hashSet) {
			Integer outInteger = (int) integer;
			outHashSet.add(outInteger);
		}
		return outHashSet;
	}

	@Override
	public List<Lamp> getCurrentBestSolution() {
		if (currentBestSolution == null) {
			return null;
		}
		List<Lamp> outLamps = new LinkedList<Lamp>();
		Iterator<Lamp> lampIterator = currentBestSolution.iterator();
		while(lampIterator.hasNext()) {
			outLamps.add(lampIterator.next().deepCopy());
		}
		return outLamps;
		
	}

	@Override
	public int getNumberOfOnLampsBestSolution() {
		return numberIlluminatedLampsBestSolution;
	}
	
	
	

}
