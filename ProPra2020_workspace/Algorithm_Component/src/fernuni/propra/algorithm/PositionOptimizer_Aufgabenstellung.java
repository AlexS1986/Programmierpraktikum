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
 * A specific provider of an algorithm that finds a minimum set (and number) of
 * tagged {@link Lamp}s that illuminates an {@link IRoom} instance.
 * <p>
 * The algorithm works as follows like the algorithm specified in [1].
 * 
 * 
 * <p>
 * Implemented interfaces and super classes: {@link IPositionOptimizer}
 * <p>
 * [1] Aufgabenstellung Programmierpraktium SS 2020, Fernuni Hagen
 * @author alex
 *
 */
public class PositionOptimizer_Aufgabenstellung implements IPositionOptimizer {
	private static List<Lamp> currentBestSolution;
	private static int numberIlluminatedLampsBestSolution;
	private static IIlluminationTester illuminationTester = AbstractAlgorithmFactory.getAlgorithmFactory()
			.createIlluminiationTester();

	public PositionOptimizer_Aufgabenstellung() {
	}

	@Override
	public List<Lamp> optimizePositions(List<Lamp> taggedCandidates, IRuntimePositionOptimizer runTimeInformation)
			throws InterruptedException {

		// all lamps are on -> illuminated
		currentBestSolution = taggedCandidates;
		numberIlluminatedLampsBestSolution = taggedCandidates.size();

		HashSet<Integer> allTags = new HashSet<Integer>();
		for (Lamp lamp : taggedCandidates) {
			lamp.turnOff(); // make sure all lamps are turned off
			for (Integer tag : lamp.getCopyOfTags()) {
				allTags.add(tag);
			}
		}

		ArrayList<Lamp> lamps = deepCopyLamps(taggedCandidates);

		

		searchSolution(lamps, 0, allTags, 0, runTimeInformation);

		return currentBestSolution;

	}

	/**
	 * Implements the backtracking algorithm as indicated in [1].
	 * 
	 * @param lamps              : the set of lamps for which an optimal
	 *                           configuration is to be found
	 * @param idx                : index of current iteration
	 * @param allTags            : a set of all portions of the room that need to be
	 *                           illuminated
	 * @param numberLampsOn      : number of lamps that are turned on in the
	 *                           provided lamps argument
	 * @param runTimeInformation : a data structure that can be used to store
	 *                           runtime information
	 * @throws InterruptedException
	 */
	private void searchSolution(ArrayList<Lamp> lamps, int idx, HashSet<Integer> allTags, int numberLampsOn,
			IRuntimePositionOptimizer runTimeInformation) throws InterruptedException {

		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}

		if (illuminationTester.testIfRoomIsIlluminated(lamps.iterator(), allTags, runTimeInformation)) { // valid
																											// solution
																											// found
			if (numberLampsOn <= numberIlluminatedLampsBestSolution) {
				System.out.println("Solution found with " + numberLampsOn + " lamps turned on.");
				currentBestSolution = deepCopyLamps(lamps);
				numberIlluminatedLampsBestSolution = numberLampsOn;
			}

		} else { // not a valid solution
			if (idx < lamps.size()) {
				if (numberLampsOn < numberIlluminatedLampsBestSolution) {
					Lamp lamp = lamps.get(idx);
					lamp.turnOn();
					searchSolution(deepCopyLamps(lamps), idx + 1, allTags, numberLampsOn + 1, runTimeInformation);

					lamp.turnOff();
					searchSolution(deepCopyLamps(lamps), idx + 1, allTags, numberLampsOn, runTimeInformation);

				}
			}
		}
	}

	private static ArrayList<Lamp> deepCopyLamps(List<Lamp> lamps) {
		ArrayList<Lamp> outLamps = new ArrayList<Lamp>(lamps.size());
		Iterator<Lamp> lampsIterator = lamps.iterator();
		while (lampsIterator.hasNext()) {
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
		while (lampIterator.hasNext()) {
			outLamps.add(lampIterator.next().deepCopy());
		}
		return outLamps;

	}

	@Override
	public int getNumberOfOnLampsBestSolution() {
		return numberIlluminatedLampsBestSolution;
	}

}
