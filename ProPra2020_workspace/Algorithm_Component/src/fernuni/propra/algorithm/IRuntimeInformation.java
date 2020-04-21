package fernuni.propra.algorithm;

public interface IRuntimeInformation extends IRuntimeCandidateSearcher, 
	IRuntimePositionOptimizer, IRuntimeIlluminationTester, IRuntimeReader{
	void startTime();
	void stopTime();
	void resetTime();
}
