package fernuni.propra.algorithm.runtime_information;

/**
 * An interface that extends the extended interfaces so that implementing classes can declare
 * that they are capable of storing runtime information for the part of the algorithm that deals
 * with finding the original partial rectangles of the {@link IRoom}.
 * <p>
 * Implementing classes: {@link RuntimeInformation}
 * <p>
 * @author alex
 *
 */
public interface IRuntimeOriginalPartialRectanglesFinder {
	/**
	 * Start the clock for the part of the algorithm that deals
	 * with finding the original partial rectangles of the {@link IRoom}..
	 * @throws RuntimeExceptionLamps : if not handled correctly 
	 */
	void startTimeOriginalPartialRectanglesFind() throws RuntimeExceptionLamps;
	
	/**
	 * Stop the clock for the part of the algorithm that deals
	 * with finding the original partial rectangles of the {@link IRoom}.
	 * @throws RuntimeExceptionLamps : if not handled correctly 
	 */
	void stopTimeOriginalPartialRectanglesFind() throws RuntimeExceptionLamps;
}
