package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimePositionOptimizer;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;

/**
 * A provider of an algorithm that finds a minimum set (and number) of tagged
 * {@link Lamp}s that illuminates an {@link IRoom} instance.
 * <p>
 * Implementing classes: {@link PositionOptimizer}, 
 * {@link PositionOptimizer_Aufgabenstellung}
 * 
 * @author alex
 *
 */
public interface IPositionOptimizer {
	/**
	 * A method that initiates the computation of an optimal (i.e. a set with a
	 * minimum number of lamps that are turned on) set of lamps, represented by a
	 * {@link List}<{@link Lamp}>.
	 * <p>
	 * The set of {@link Lamp}s that should be minimized is supplied as a
	 * {@link List} of tagged {@link Lamp}s. The tags of each {@link Lamp} uniquely
	 * denote the portion an {@link IRoom} that is illuminated by each {@link Lamp}.
	 * The union of all such tags must be equivalent to all portions of the
	 * {@link IRoom} i.e. if the union of all tags associated with illuminated
	 * {@link Lamp}s is equal to the set of all tags, the {@link IRoom} must be
	 * illuminated.
	 * <p>
	 * Detailed runtime information can be stored to an
	 * {@link IRuntimePositionOptimizer} instance
	 * <p>
	 * The computation may be interrupted, by interrupting the executing thread.
	 * Implementations need to guarantee that an {@link InterruptedException} is
	 * thrown as fast as possible in this case.
	 * <p>
	 * 
	 * @param taggedCandidates   : <{@link List}<{@link Lamp}> . The set of
	 *                           {@link Lamp}s that should be minimized is supplied
	 *                           as a {@link List} of tagged {@link Lamp}s. The tags
	 *                           of each {@link Lamp} uniquely denote the portion an
	 *                           {@link IRoom} that is illuminated by each
	 *                           {@link Lamp}. The union of all such tags must be
	 *                           equivalent to all portions of the {@link IRoom}
	 *                           i.e. if the union of all tags associated with
	 *                           illuminated {@link Lamp}s is equal to the set of
	 *                           all tags, the {@link IRoom} must be illuminated.
	 * @param runTimeInformation : Detailed runtime information can be stored to an
	 *                           {@link IRuntimePositionOptimizer} instance
	 * @return A {@link List}<{@link Lamp}> that represents the best solution (i.e.
	 *         a set with a minimum number of {@link Lamp}s that are turned on)
	 *         after the computation has finished.
	 * @throws InterruptedException
	 */
	List<Lamp> optimizePositions(List<Lamp> taggedCandidates, IRuntimePositionOptimizer runTimeInformation)
			throws InterruptedException;

	/**
	 * A method that allows to get the currently available best solution (i.e. a set
	 * with a minimum number of {@link Lamp}s that are turned on) as a
	 * {@link List}<{@link Lamp}>, where a minimum number of {@link Lamp}s is turned
	 * on that still illuminates the {@link IRoom} represented by the tags of the
	 * Lamps.
	 * <p>
	 * Should only be called after {@link optimizePositions}. Otherwise no solution
	 * is available and 0 will be returned.
	 * <p>
	 * 
	 * @return A list of {@link Lamp}s that represents the currently available best
	 *         solution.
	 */
	List<Lamp> getCurrentBestSolution();

	/**
	 * Returns the number of turned on {@link Lamp}s in the currently available best
	 * solution.
	 * <p>
	 * Should only be called after {@link optimizePositions}. Otherwise no solution
	 * is available and 0 will be returned.
	 * 
	 * @return The number of turned on {@link Lamp}s in the currently available best
	 *         solution.
	 */
	int getNumberOfOnLampsBestSolution();

}
