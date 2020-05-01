package fernuni.propra.algorithm;

import java.util.HashSet;
import java.util.Iterator;

import fernuni.propra.algorithm.runtime_information.IRuntimeIlluminationTester;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

public interface IIlluminationTester {
	boolean testIfRoomIsIlluminated(IRoom room, IRuntimeIlluminationTester runtimeInfo) throws IlluminationTesterException;
	boolean testIfRoomIsIlluminated(Iterator<Lamp> taggedLampsIterator, HashSet<Integer> allTags, IRuntimeIlluminationTester runtimeInfo);
	boolean testIfRoomIsIlluminated(HashSet<Integer> illuminatedTags, HashSet<Integer> allTags, IRuntimeIlluminationTester runtimeInfo);
}
