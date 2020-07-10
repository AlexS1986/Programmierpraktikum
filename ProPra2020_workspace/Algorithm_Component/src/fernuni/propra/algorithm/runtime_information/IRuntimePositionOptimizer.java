package fernuni.propra.algorithm.runtime_information;

public interface IRuntimePositionOptimizer extends IRuntimeIlluminationTester {
	void startTimeOptimizePositions() throws RuntimeExceptionLamps;

	void stopTimeOptimizePositions() throws RuntimeExceptionLamps;

	void resetTimeOptimizePositions();

}
