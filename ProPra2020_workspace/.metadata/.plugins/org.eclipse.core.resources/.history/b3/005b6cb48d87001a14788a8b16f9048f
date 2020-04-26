package fernuni.propra.algorithm;

public class AlgorithmFactory {
	private static AlgorithmFactory singleton; // konkrete abstrakte Fabrik ist singleton
	
	private AlgorithmFactory() {}
	
	public static AlgorithmFactory getAlgorithmFactory() {
		if (singleton == null) {
			singleton = new AlgorithmFactory();
		}
		
		return singleton;
	}

	public ICandidateSearcher createCandidateSearcher() {
		return new CandidateSearcher();
	}

	public IPositionOptimizer createPositionOptimizer() {
		return new PositionOptimizer();
	}
	
	public IIlluminationTester createIlluminiationTester() {
		return new IlluminationTester();
	}

}
