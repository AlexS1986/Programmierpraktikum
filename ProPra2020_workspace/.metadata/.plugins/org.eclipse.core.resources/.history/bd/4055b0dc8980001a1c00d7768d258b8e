package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Point;

public abstract class SolveKAbstract {
	
	public void solve(IRuntimeInformation runTimeInformation, IRoom room) throws CandidateSearcherException {
		List<Point> candidates = generateCandidateSearcher().searchCandidates(room, 
				runTimeInformation);
		
		generatePositionOptimizer().optimizePositions(room, 
				candidates, runTimeInformation); 
		}
	

	protected abstract IPositionOptimizer generatePositionOptimizer();
	

	protected abstract ICandidateSearcher generateCandidateSearcher();
	
	protected abstract void abort();
}
