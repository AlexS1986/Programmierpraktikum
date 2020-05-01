package fernuni.propra.algorithm.runtime_information;

import java.util.concurrent.TimeUnit;

public class RuntimeInformation implements IRuntimeInformation, IRuntimeReader {
	private volatile long startTime = -1;
	private volatile long stopTime = -1;
	
	private volatile long candidateSearchStartTime = -1;
	private volatile long candidateSearchStopTime = -1;
	
	private volatile long originalPartialRectanglesFindStartTime = -1;
	private volatile long originalPartialRectanglesFindStopTime = -1;
	
	private volatile long optimizePositionsStartTime = -1;
	private volatile long optimizePositionsStopTime = -1;
	
	private volatile long illuminationTestStartTime = -1;
	private volatile long illuminationTestStopTime = -1;
	
	
	@Override
	public void startTimeCandidateSearch() throws RuntimeExceptionLamps {
		if (candidateSearchStartTime != -1 && candidateSearchStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		candidateSearchStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeCandidateSearch() throws RuntimeExceptionLamps {
		if (candidateSearchStartTime == -1 || candidateSearchStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		candidateSearchStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeCandidateSearch() throws RuntimeExceptionLamps {
		if (candidateSearchStartTime == -1 && candidateSearchStopTime == -1) {
			throw new RuntimeExceptionLamps();
		}
		return candidateSearchStopTime-candidateSearchStartTime;
	}

	@Override
	public void resetTimeCandidateSearch() {
		candidateSearchStartTime = -1;
		candidateSearchStopTime = -1;
		
	}

	@Override
	public void startTimeOriginalPartialRectanglesFind() throws RuntimeExceptionLamps {
		if (originalPartialRectanglesFindStartTime != -1 && originalPartialRectanglesFindStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		originalPartialRectanglesFindStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeOriginalPartialRectanglesFind() throws RuntimeExceptionLamps {
		if (originalPartialRectanglesFindStartTime == -1 || originalPartialRectanglesFindStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		candidateSearchStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeOriginalPartialRectanglesFind() throws RuntimeExceptionLamps {
		if (originalPartialRectanglesFindStartTime == -1 && originalPartialRectanglesFindStopTime == -1) {
			throw new RuntimeExceptionLamps();
		}
		return originalPartialRectanglesFindStopTime-originalPartialRectanglesFindStartTime;
	}

	@Override
	public void resetTimeOriginalPartialRectanglesFind() {
		originalPartialRectanglesFindStartTime = -1;
		originalPartialRectanglesFindStopTime = -1;	
	}

	@Override
	public void startTimeOptimizePositions() throws RuntimeExceptionLamps {
		if (optimizePositionsStartTime != -1 && optimizePositionsStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		optimizePositionsStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeOptimizePositions() throws RuntimeExceptionLamps {
		if (optimizePositionsStartTime == -1 || optimizePositionsStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		optimizePositionsStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeOptimizePositions() throws RuntimeExceptionLamps {
		if (optimizePositionsStartTime == -1 && optimizePositionsStopTime == -1) {
			throw new RuntimeExceptionLamps();
		}
		return optimizePositionsStopTime-optimizePositionsStartTime;
	}

	@Override
	public void resetTimeOptimizePositions() {
		optimizePositionsStartTime = -1;
		optimizePositionsStopTime = -1;
		
	}

	@Override
	public void startTimeIlluminationTest() throws RuntimeExceptionLamps {
		if (illuminationTestStartTime != -1 && illuminationTestStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		illuminationTestStartTime = System.nanoTime();
		
	}

	@Override
	public void stopTimeIlluminationTest() throws RuntimeExceptionLamps {
		if (illuminationTestStartTime == -1 || illuminationTestStopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		illuminationTestStopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTimeIlluminationTest() throws RuntimeExceptionLamps {
		if (illuminationTestStartTime == -1 && illuminationTestStopTime == -1) {
			throw new RuntimeExceptionLamps();
		}
		return illuminationTestStopTime-illuminationTestStopTime;
	}

	@Override
	public void resetTimeIlluminationTest() {
		illuminationTestStartTime = -1;
		illuminationTestStopTime = -1;
		
	}

	@Override
	public void startTime() throws RuntimeExceptionLamps {
		if (startTime != -1 && stopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		startTime = System.nanoTime();
		
	}

	@Override
	public void stopTime() throws RuntimeExceptionLamps {
		if (startTime == -1 || stopTime != -1) {
			throw new RuntimeExceptionLamps();
		}
		stopTime = System.nanoTime();
		
	}

	@Override
	public long getElapsedTime() throws RuntimeExceptionLamps {
		if (startTime == -1 && stopTime == -1) {
			throw new RuntimeExceptionLamps();
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
		} catch (RuntimeExceptionLamps e) {
			return "No time intervall recorded!";
		}
		String outString = "Elapsed time in seconds: ";
		outString = outString + String.valueOf(elapsedTimeSeconds);
		return outString;
	}


}
