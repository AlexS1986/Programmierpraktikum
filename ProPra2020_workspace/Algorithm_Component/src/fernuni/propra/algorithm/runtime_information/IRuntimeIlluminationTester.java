package fernuni.propra.algorithm.runtime_information;

/**
 * An interface that extends the extended interfaces so that implementing
 * classes can declare that they are capable of storing runtime information for
 * the part of the algorithm that deals with checking whether an {@link IRoom}
 * is illuminated.
 * <p>
 * Implementing classes: {@link RuntimeInformation}
 * <p>
 * Extended interfaces: {@link IRuntimeOriginalPartialRectanglesFinder}
 * <p>
 * 
 * @author alex
 *
 */
public interface IRuntimeIlluminationTester extends IRuntimeOriginalPartialRectanglesFinder {
	/**
	 * Start the clock for the part of the algorithm that deals with checking
	 * whether an {@link IRoom} is illuminated.
	 * 
	 * @throws RuntimeExceptionLamps : if not handled correctly
	 */
	void startTimeIlluminationTest() throws RuntimeExceptionLamps;

	/**
	 * Stop the clock for the part of the algorithm that deals with checking whether
	 * an {@link IRoom} is illuminated.
	 * 
	 * @throws RuntimeExceptionLamps
	 */
	void stopTimeIlluminationTest() throws RuntimeExceptionLamps;
}
