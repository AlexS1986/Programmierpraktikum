package fernuni.propra.algorithm;

import java.util.Iterator;

import fernuni.propra.algorithm.runtime_information.IRuntimeIlluminationTester;
import fernuni.propra.algorithm.runtime_information.RuntimeExceptionLamps;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

/**
 * A controll class that controls the program flow for the validation of an
 * {@link IRoom} instance, i.e. for the test whether that {@link IRoom} is
 * illuminated by its associated {@link Lamp}s or not.
 * <p>
 * Delegates the algorithm to an instance of {@link IIlluminationTester}, which
 * is obtained from a call to the {@link AbstractAlgorithmFactory}.
 * <p>
 * Furthermore a data structure is given to {@link ValidateK} in order to store
 * runtime information to that data structure.
 * <p>
 * 
 * @author alex
 *
 */
public class ValidateK {
	/**
	 * Checks whether an {@link IRoom} instance is illuminated or not by delegating
	 * to {@link IIlluminationTester}.
	 * 
	 * @param room        : the {@link IRoom} to be checked
	 * @param runtimeInfo : {@link IRoom} the data structure to which
	 *                    {@link ValidateK} will write runtime information.
	 * @return
	 * @throws ValidateKException
	 */
	boolean validate(IRoom room, IRuntimeIlluminationTester runtimeInfo) throws ValidateKException {
		try {
			// turn all lamps on
			Iterator<Lamp> lampIterator = room.getLamps();
			while (lampIterator.hasNext()) {
				lampIterator.next().turnOn();
			}
			runtimeInfo.startTimeIlluminationTest();
			boolean isIlluminated = AbstractAlgorithmFactory.getAlgorithmFactory().createIlluminiationTester()
					.testIfRoomIsIlluminated(room, runtimeInfo);
			runtimeInfo.stopTimeIlluminationTest();
			return isIlluminated;
		} catch (IlluminationTesterException e) {
			throw new ValidateKException(e);
		} catch (RuntimeExceptionLamps rte) {
			throw new ValidateKException(rte);
		}
	}

}
