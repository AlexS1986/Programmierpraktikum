package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

public class PositionOptimizer implements IPositionOptimizer{
	private static List<Lamp> currentBestSolution;
	private static int numberIlluminatedLampsBestSolution;
	private static IIlluminationTester illuminationTester = AlgorithmFactory.getAlgorithmFactory().createIlluminiationTester();

	public PositionOptimizer() {
	}

	@Override
	public List<Lamp> optimizePositions(IRoom room, List<Lamp> taggedCandidates, IRuntimePositionOptimizer runTimeInformation) {
 
		// all lamps are on -> illuminated
		currentBestSolution = taggedCandidates;
		numberIlluminatedLampsBestSolution = taggedCandidates.size();
		
		
		HashSet<Integer> allTags = new HashSet<Integer>();
		for (Lamp lamp : taggedCandidates) {
			lamp.turnOff(); // make sure all lamps are turned off
			Iterator<Integer> tagIterator = lamp.iteratorTag();
			while(tagIterator.hasNext()) {
				allTags.add(tagIterator.next());
			}
		}
		
		ArrayList<Lamp> lamps = deepCopyLamps(taggedCandidates);
		
		//HashSet<Integer> illuminated = new HashSet<Integer>();
		

		searchSolution(lamps,0, allTags, 0,runTimeInformation);
		
		return currentBestSolution;
		
		 
		
	}
	
	private void searchSolution(ArrayList<Lamp> lamps, int idx, 
			HashSet<Integer> allTags, int numberLampsOn, IRuntimePositionOptimizer runTimeInformation) {
		if(illuminationTester.testIfRoomIsIlluminated(lamps.iterator(), allTags, runTimeInformation)) { // valid solution found
			if (numberLampsOn<=numberIlluminatedLampsBestSolution) {
				currentBestSolution = deepCopyLamps(lamps);
				numberIlluminatedLampsBestSolution = numberLampsOn;
			}
		} else { // not a valid solution
			if (idx < lamps.size()) {
				if(numberLampsOn<numberIlluminatedLampsBestSolution) {
					Lamp lamp = lamps.get(idx);
					lamp.turnOn();	
					searchSolution(deepCopyLamps(lamps), idx+1, allTags, numberLampsOn+1, runTimeInformation);
					
					lamp.turnOff();
					searchSolution(deepCopyLamps(lamps), idx+1, allTags, numberLampsOn, runTimeInformation);
									
				}
			}
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
