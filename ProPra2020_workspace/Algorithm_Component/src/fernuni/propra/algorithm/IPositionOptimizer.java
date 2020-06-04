package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimePositionOptimizer;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;

public interface IPositionOptimizer {

	List<Lamp> optimizePositions(IRoom room, List<Lamp> taggedCandidates, IRuntimePositionOptimizer runTimeInformation) throws InterruptedException;
	
	List<Lamp> getCurrentBestSolution();
	
	int getNumberOfOnLampsBestSolution();

}
