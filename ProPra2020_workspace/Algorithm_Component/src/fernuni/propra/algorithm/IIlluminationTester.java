package fernuni.propra.algorithm;

import fernuni.propra.internal_data_model.IRoom;

public interface IIlluminationTester {

	boolean testIfRoomIsIlluminated(IRoom room, IRuntimeIlluminationTester runtimeInfo);

}
