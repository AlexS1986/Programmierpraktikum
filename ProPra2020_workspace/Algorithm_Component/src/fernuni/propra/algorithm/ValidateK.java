package fernuni.propra.algorithm;

import fernuni.propra.internal_data_model.IRoom;

public  class ValidateK {
	
	boolean validate(IRoom room, IRuntimeIlluminationTester runtimeInfo) throws IlluminationTesterException {
		return AlgorithmFactory.getAlgorithmFactory().createIlluminiationTester().testIfRoomIsIlluminated(room, runtimeInfo);
	}
	
	
	void abort() {
		
	}

}
