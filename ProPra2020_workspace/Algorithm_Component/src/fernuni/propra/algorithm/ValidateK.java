package fernuni.propra.algorithm;

import java.util.Iterator;

import fernuni.propra.algorithm.runtime_information.IRuntimeIlluminationTester;
import fernuni.propra.algorithm.runtime_information.RuntimeExceptionLamps;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

public  class ValidateK {
	boolean validate(IRoom room, IRuntimeIlluminationTester runtimeInfo) throws ValidateKException {
		try {
			// turn all lamps on
			Iterator<Lamp> lampIterator = room.getLamps();
			while(lampIterator.hasNext()) {
				lampIterator.next().turnOn();
			}
			
			runtimeInfo.startTimeIlluminationTest();
			boolean isIlluminated = AlgorithmFactory.getAlgorithmFactory().createIlluminiationTester().testIfRoomIsIlluminated(room, runtimeInfo);
			runtimeInfo.stopTimeIlluminationTest();
			return isIlluminated;
			//return computeResultString(room, isIlluminated);
		} catch (IlluminationTesterException e) {
			throw new ValidateKException(e);
		} catch( RuntimeExceptionLamps rte) {
			throw new ValidateKException(rte);
		}
	}
	
	
	
}
