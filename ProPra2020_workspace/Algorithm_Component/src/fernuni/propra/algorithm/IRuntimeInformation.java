package fernuni.propra.algorithm;

public interface IRuntimeInformation extends IRuntimeCandidateSearcher, 
	IRuntimePositionOptimizer, IRuntimeIlluminationTester, IRuntimeReader{
	void startTime() throws LampsRuntimeException;
	void stopTime() throws LampsRuntimeException;
	void resetTime();
}
