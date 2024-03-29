package fernuni.propra.algorithm;

import java.util.ArrayList;
import java.util.HashSet;

import fernuni.propra.algorithm.runtime_information.IRuntimeOriginalPartialRectanglesFinder;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;

/**
 * A provider of an algorithm that can find the original partial rectangles for
 * an {@link IRoom} instance.
 * <p>
 * Implementing classes: {@link OriginalPartialRectanglesFinder}
 * <p>
 * 
 * @author alex
 *
 */
public interface IOriginalPartialRectanglesFinder {
	/**
	 * The original partial rectangles of an {@link IRoom} are tagged with
	 * {@link Integer}s that denote "parts" (e.g. walls) of the room that are
	 * illuminated if the associated partial rectangle is illuminated. If all tags
	 * are illuminated then the room is illuminated. This method returns a set that
	 * contains all tags of all original partial rectangles of the {@link IRoom}.
	 * <p>
	 * The {@link findOriginalPartialRectangles} method needs to be called first in
	 * order for {@link getAllTags}() to work.
	 * 
	 * @return : {@link HashSet}<{@link Integer}> a set of all tags of the original
	 *         partial rectangles of the {@link IRoom} parameter of the previously
	 *         called {@link findOriginalPartialRectangles} method.
	 */
	HashSet<Integer> getAllTags();

	/**
	 * Returns all original partial rectangles of an {@link IRoom} parameter and
	 * saves runtime information to the instance of
	 * {@link IRuntimeOriginalPartialRectanglesFinder}. All rectangles are tagged
	 * with Integers that denote the parts of the room (e.g. walls) that are
	 * illuminated if the rectangle is illuminated. If all tags are illuminated then
	 * it must follow that the room is illuminated. Identical rectangles are only
	 * stored once.
	 * 
	 * @param room : the {@link IRoom} instance for which the original partial
	 *             rectangles are to be determined
	 * @param rt   : a data structure of type
	 *             {@link IRuntimeOriginalPartialRectanglesFinder} that can be used
	 *             to store runtime information.
	 * @return a list of tagged original partial rectangles of the room.
	 * @throws OriginalPartialRectanglesFinderException
	 */
	ArrayList<RectangleWithTag> findOriginalPartialRectangles(IRoom room, IRuntimeOriginalPartialRectanglesFinder rt)
			throws OriginalPartialRectanglesFinderException;

}
