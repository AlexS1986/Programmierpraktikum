package fernuni.propra.algorithm;

public class RuntimeInformation implements IRuntimeInformation, IRuntimeReader {
	private long startTime = -1;
	private long stopTime = -1;
	
	private long candidateSearchStartTime = -1;
	private long candidateSearchStopTime = -1;
	
	private long optimizePositionsStartTime = -1;
	private long optimizePositionsStopTime = -1;
	
	private long illuminationTestStartTime = -1;
	private long illuminationTestStopTime = -1;
	
	
	@Override
	public void startTimeCandidateSearch() throws LampsRuntimeException {
		if (candidateSearchStartTime != -1 && candidateSearchStopTime != -1) {
			throw new LampsRuntimeException("Reset candidate search timer first");
		}
		candidateSearchStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeCandidateSearch() throws LampsRuntimeException {
		if (candidateSearchStartTime == -1 || candidateSearchStopTime != -1) {
			throw new LampsRuntimeException("Reset candidate search timer first or start candidate search timer before stopping");
		}
		candidateSearchStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeCandidateSearch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetTimeCandidateSearch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTimeOriginalPartialRectanglesFind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTimeOriginalPartialRectanglesFind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getElapsedTimeOriginalPartialRectanglesFind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetTimeOriginalPartialRectanglesFind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTimeOptimizePositions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTimeOptimizePositions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getElapsedTimeOptimizePositions() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetTimeOptimizePositions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTimeIlluminationTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTimeIlluminationTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getElapsedTimeIlluminationTest() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetTimeIlluminationTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getElapsedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetTime() {
		// TODO Auto-generated method stub
		
	}


}
