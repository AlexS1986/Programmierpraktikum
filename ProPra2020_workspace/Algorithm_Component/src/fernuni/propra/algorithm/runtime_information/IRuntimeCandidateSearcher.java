package fernuni.propra.algorithm.runtime_information;

public interface IRuntimeCandidateSearcher extends IRuntimeOriginalPartialRectanglesFinder{
	void startTimeCandidateSearch() throws RuntimeExceptionLamps;
	void stopTimeCandidateSearch() throws RuntimeExceptionLamps;
	void resetTimeCandidateSearch();
}
