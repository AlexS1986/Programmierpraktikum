package fernuni.propra.algorithm;

/**
 * An abstract factory that configures the solution algorithm by instantiating
 * specific instances of the interfaces {@link ICandidateSearcher},
 * {@link IPositionOptimizer} and {@link IIlluminationTester} that are to be
 * used within the algorithm.
 * <p>
 * The {@link AlgorithmFactory2} implements the "abstract factory" (concrete
 * factory) and "singleton" design patterns.
 * <p>
 * 
 * Implemented interfaces and super classes: {@link AbstractAlgorithmFactory}
 * 
 * @author alex
 *
 */
public class AlgorithmFactory2 extends AbstractAlgorithmFactory {
	private static AlgorithmFactory2 singleton;

	private AlgorithmFactory2() {
	};

	static AlgorithmFactory2 getConcreteAlgorithmFactory() {
		if (singleton == null) {
			singleton = new AlgorithmFactory2();
		}
		return singleton;
	}

	@Override
	public ICandidateSearcher createCandidateSearcher() {
		return new CandidateSearcher();
	}

	@Override
	public IPositionOptimizer createPositionOptimizer() {
		return new PositionOptimizer_Aufgabenstellung();
	}

	@Override
	public IIlluminationTester createIlluminiationTester() {
		return new IlluminationTester();
	}

	@Override
	public IOriginalPartialRectanglesFinder createOriginalPartialRectanglesFinder() {
		return new OriginalPartialRectanglesFinder();
	}

}
