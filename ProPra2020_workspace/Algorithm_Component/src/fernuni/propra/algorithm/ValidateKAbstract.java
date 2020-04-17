package fernuni.propra.algorithm;

import fernuni.propra.internal_data_model.IRoom;

public abstract class ValidateKAbstract {
	
	boolean validate(IRoom room, IRuntimeIlluminationTester runtimeInfo) {
		return generateIlluminationTester().testIfRoomIsIlluminated(room, runtimeInfo);
	}
	
	protected abstract IIlluminationTester generateIlluminationTester();
	
	protected abstract void abort();

}
