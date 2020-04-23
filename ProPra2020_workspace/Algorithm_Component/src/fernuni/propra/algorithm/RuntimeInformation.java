package fernuni.propra.algorithm;

import java.util.concurrent.TimeUnit;

public class RuntimeInformation implements IRuntimeInformation, IRuntimeReader {
	private long startTime = -1;
	private long stopTime = -1;
	
	private long candidateSearchStartTime = -1;
	private long candidateSearchStopTime = -1;
	
	private long originalPartialRectanglesFindStartTime = -1;
	private long originalPartialRectanglesFindStopTime = -1;
	
	private long optimizePositionsStartTime = -1;
	private long optimizePositionsStopTime = -1;
	
	private long illuminationTestStartTime = -1;
	private long illuminationTestStopTime = -1;
	
	
	@Override
	public void startTimeCandidateSearch() throws LampsRuntimeException {
		if (candidateSearchStartTime != -1 && candidateSearchStopTime != -1) {
			throw new LampsRuntimeException();
		}
		candidateSearchStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeCandidateSearch() throws LampsRuntimeException {
		if (candidateSearchStartTime == -1 || candidateSearchStopTime != -1) {
			throw new LampsRuntimeException();
		}
		candidateSearchStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeCandidateSearch() throws LampsRuntimeException {
		if (candidateSearchStartTime == -1 && candidateSearchStopTime == -1) {
			throw new LampsRuntimeException();
		}
		return candidateSearchStopTime-candidateSearchStartTime;
	}

	@Override
	public void resetTimeCandidateSearch() {
		candidateSearchStartTime = -1;
		candidateSearchStopTime = -1;
		
	}

	@Override
	public void startTimeOriginalPartialRectanglesFind() throws LampsRuntimeException {
		if (originalPartialRectanglesFindStartTime != -1 && originalPartialRectanglesFindStopTime != -1) {
			throw new LampsRuntimeException();
		}
		originalPartialRectanglesFindStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeOriginalPartialRectanglesFind() throws LampsRuntimeException {
		if (originalPartialRectanglesFindStartTime == -1 || originalPartialRectanglesFindStopTime != -1) {
			throw new LampsRuntimeException();
		}
		candidateSearchStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeOriginalPartialRectanglesFind() throws LampsRuntimeException {
		if (originalPartialRectanglesFindStartTime == -1 && originalPartialRectanglesFindStopTime == -1) {
			throw new LampsRuntimeException();
		}
		return originalPartialRectanglesFindStopTime-originalPartialRectanglesFindStartTime;
	}

	@Override
	public void resetTimeOriginalPartialRectanglesFind() {
		originalPartialRectanglesFindStartTime = -1;
		originalPartialRectanglesFindStopTime = -1;	
	}

	@Override
	public void startTimeOptimizePositions() throws LampsRuntimeException {
		if (optimizePositionsStartTime != -1 && optimizePositionsStopTime != -1) {
			throw new LampsRuntimeException();
		}
		optimizePositionsStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeOptimizePositions() throws LampsRuntimeException {
		if (optimizePositionsStartTime == -1 || optimizePositionsStopTime != -1) {
			throw new LampsRuntimeException();
		}
		optimizePositionsStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeOptimizePositions() throws LampsRuntimeException {
		if (optimizePositionsStartTime == -1 && optimizePositionsStopTime == -1) {
			throw new LampsRuntimeException();
		}
		return optimizePositionsStopTime-optimizePositionsStartTime;
	}

	@Override
	public void resetTimeOptimizePositions() {
		optimizePositionsStartTime = -1;
		optimizePositionsStopTime = -1;
		
	}

	@Override
	public void startTimeIlluminationTest() throws LampsRuntimeException {
		if (illuminationTestStartTime != -1 && illuminationTestStopTime != -1) {
			throw new LampsRuntimeException();
		}
		illuminationTestStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeIlluminationTest() throws LampsRuntimeException {
		if (illuminationTestStartTime == -1 || illuminationTestStopTime != -1) {
			throw new LampsRuntimeException();
		}
		illuminationTestStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeIlluminationTest() throws LampsRuntimeException {
		if (illuminationTestStartTime == -1 && illuminationTestStopTime == -1) {
			throw new LampsRuntimeException();
		}
		return illuminationTestStopTime-illuminationTestStopTime;
	}

	@Override
	public void resetTimeIlluminationTest() {
		illuminationTestStartTime = -1;
		illuminationTestStopTime = -1;
		
	}

	@Override
	public void startTime() throws LampsRuntimeException {
		if (startTime != -1 && stopTime != -1) {
			throw new LampsRuntimeException();
		}
		startTime = System.nanoTime();
		
	}

	@Override
	public void stopTime() throws LampsRuntimeException {
		if (startTime == -1 || stopTime != -1) {
			throw new LampsRuntimeException();
		}
		stopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTime() throws LampsRuntimeException {
		if (startTime == -1 && stopTime == -1) {
			throw new LampsRuntimeException();
		}
		return stopTime-startTime;
	}

	@Override
	public void resetTime() {
		startTime = -1;
		stopTime = -1;
		
	}
	
	@Override
	public String toString() {
		long elapsedTimeSeconds = -1;
		try {
			elapsedTimeSeconds = TimeUnit.NANOSECONDS.toSeconds(getElapsedTime());
		} catch (LampsRuntimeException e) {
			return "No time intervall recorded!";
		}
		String outString = "Elapsed time in seconds: ";
		outString = outString + String.valueOf(elapsedTimeSeconds);
		return outString;
	}


}
