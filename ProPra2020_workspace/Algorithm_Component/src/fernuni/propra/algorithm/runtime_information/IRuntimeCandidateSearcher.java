package fernuni.propra.algorithm.runtime_information;

/**
 * An interface that extends the extended interfaces so that implementing
 * classes can declare that they are capable of storing runtime information for
 * the part of the algorithm that deals with finding candidates for {@link Lamp}
 * positions.
 * <p>
 * Implementing classes: {@link RuntimeInformation}
 * <p>
 * Extended interfaces: {@link IRuntimeOriginalPartialRectanglesFinder}
 * <p>
 * 
 * @author alex
 *
 */
public interface IRuntimeCandidateSearcher extends IRuntimeOriginalPartialRectanglesFinder {
	/**
	 * Start the clock for the part of the algorithm that deals with finding
	 * candidates for {@link Lamp} positions.
	 * 
	 * @throws RuntimeExceptionLamps : if not handled correctly
	 */
	void startTimeCandidateSearch() throws RuntimeExceptionLamps;

	/**
	 * Stop the clock for the part of the algorithm that deals with finding
	 * candidates for {@link Lamp} positions.
	 * 
	 * @throws RuntimeExceptionLamps
	 */
	void stopTimeCandidateSearch() throws RuntimeExceptionLamps;
}
