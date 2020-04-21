package fernuni.propra.algorithm;

import fernuni.propra.internal_data_model.IRoom;

public class UserSolveAAS {
	private SolveK solveControl = new SolveK();
	
	public void solve(IRoom room) throws UserSolveAASException {
		IRuntimeInformation runtimeInformation = new RuntimeInformation();
		try {
			solveControl.solve(runtimeInformation, room);
		} catch (CandidateSearcherException e) {
			throw new UserSolveAASException(e.getMessage());
		}
	}

}
