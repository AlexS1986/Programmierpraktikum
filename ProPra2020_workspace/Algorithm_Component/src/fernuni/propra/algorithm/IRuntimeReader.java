package fernuni.propra.algorithm;

public interface IRuntimeReader {
	long getElapsedTimeCandidateSearch();
	long getElapsedTimeOptimizePositions();
	long getElapsedTimeOriginalPartialRectanglesFind();
	long getElapsedTime();
	long getElapsedTimeIlluminationTest();
}
