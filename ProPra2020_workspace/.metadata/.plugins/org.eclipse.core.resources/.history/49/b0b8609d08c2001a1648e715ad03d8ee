package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimeCandidateSearcher;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;

/**
 * 
 * A provider of an algorithm that can compute a {@link List} of potential {@link Lamp} positions 
 * for an instance of {@link IRoom}.
 * 
 * <p>
 * Implementing classes: {@link CandidateSearcher}
 * <p>
 * 
 * @author alex
 *
 *
 *  
 */
public interface ICandidateSearcher {

	/**
	 * A method that provides the functionality of {@link ICandidateSearcher} to callers. It returns a {@link List} of
	 * {@link Lamp}s that are potential lamp positions at which lamps might be placed to illuminate the room.
	 * @param room : an instance of {@link IRoom} for which the lamp positions are to be determined.
	 * @param runtimeCandidateSearcher : an instance of {@link IRuntimeCandidateSearcher} to which runtime information can be saved
	 * @return a {@link List} of
	 * {@link Lamp}s that contains potential lamp positions for the provided {@link IRoom}
	 * @throws CandidateSearcherException : thrown if an error occurs during execution
	 * @throws InterruptedException : thrown if the executing thread is interrupted, e.g. to stop after a certain time
	 */
	List<Lamp> searchCandidates(IRoom room, IRuntimeCandidateSearcher runtimeCandidateSearcher) throws CandidateSearcherException, InterruptedException;

}
