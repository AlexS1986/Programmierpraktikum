package fernuni.propra.algorithm.runtime_information;

/**
 * An interface that extends the extended interfaces so that implementing
 * classes can declare that they are capable of storing overall runtime
 * information.
 * <p>
 * Implementing classes: {@link RuntimeInformation}
 * <p>
 * Extended interfaces: {@link IRuntimeCandidateSearcher},
 * {@link IRuntimePositionOptimizer}, {@link IRuntimeIlluminationTester},
 * {@link IRuntimeReader}
 * <p>
 * 
 * @author alex
 *
 */
public interface IRuntimeInformation
		extends IRuntimeCandidateSearcher, IRuntimePositionOptimizer, IRuntimeIlluminationTester, IRuntimeReader {
	/**
	 * Start the clock for the overall computation.
	 * 
	 * @throws RuntimeExceptionLamps : if not handled correctly
	 */
	void startTime() throws RuntimeExceptionLamps;

	/**
	 * Stop the clock for the overall computation.
	 * 
	 * @throws RuntimeExceptionLamps
	 */
	void stopTime() throws RuntimeExceptionLamps;
}
