package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;

public class SolveK {
	
	public void solve(IRuntimeInformation runTimeInformation, IRoom room) throws CandidateSearcherException {
		List<Lamp> candidates = AlgorithmFactory.getAlgorithmFactory().createCandidateSearcher().searchCandidates(room, 
				runTimeInformation);
		
		AlgorithmFactory.getAlgorithmFactory().createPositionOptimizer().optimizePositions(room, 
				candidates, runTimeInformation); 
		}
	
	
	void abort() {
		
	}
}
