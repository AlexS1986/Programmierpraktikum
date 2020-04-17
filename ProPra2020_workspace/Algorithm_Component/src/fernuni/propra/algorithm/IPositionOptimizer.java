package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Point;

public interface IPositionOptimizer {

	void optimizePositions(IRoom room, List<Point> candidates, IRuntimePositionOptimizer runTimeInformation);

}