package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimePositionOptimizer;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

public class PositionOptimizer implements IPositionOptimizer{
	private static List<Lamp> currentBestSolution;
	private static int numberIlluminatedLampsBestSolution;
	private static IIlluminationTester illuminationTester = AbstractAlgorithmFactory.getAlgorithmFactory().createIlluminiationTester();

	public PositionOptimizer() {
	}

	@Override
	public List<Lamp> optimizePositions(IRoom room, List<Lamp> taggedCandidates, IRuntimePositionOptimizer runTimeInformation) throws InterruptedException
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
		
		//HashSet<Integer> illuminated = new HashSet<Integer>();
		

		searchSolution(lamps,0, allTags, numberIlluminatedLampsBestSolution,runTimeInformation);
		
		return currentBestSolution;
		
		 
		
	}
	
	private void searchSolution(ArrayList<Lamp> lamps, int idx, 
			HashSet<Integer> allTags, int numberLampsOn, IRuntimePositionOptimizer runTimeInformation) throws InterruptedException{
		if(Thread.currentThread().isInterrupted()) {
			throw new InterruptedException("Computation interrupted.");
		}
		
		if(illuminationTester.testIfRoomIsIlluminated(lamps.iterator(), allTags, runTimeInformation)) { // valid solution found
			if (numberLampsOn<numberIlluminatedLampsBestSolution) { // new best solution found
				System.out.println("Solution found with " + numberLampsOn + " lamps turned on.");
				currentBestSolution = deepCopyLamps(lamps);
				numberIlluminatedLampsBestSolution = numberLampsOn;
				
				
			} 
			
			if (idx < lamps.size()) {
				Lamp lamp = lamps.get(idx);
				
				/*if (!lamp.getOn()) {
					System.out.println("hi");
				}*/
				//lamp.turnOn(); // lamp is always turned on
				
				
				searchSolution(deepCopyLamps(lamps), idx+1, allTags, numberLampsOn, runTimeInformation);
				
				lamp.turnOff();
				searchSolution(deepCopyLamps(lamps), idx+1, allTags, numberLampsOn-1, runTimeInformation);
				
			}
						
		} else { // not a valid solution, with all lamps > idx turned on 

		}
	}
	
	
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
