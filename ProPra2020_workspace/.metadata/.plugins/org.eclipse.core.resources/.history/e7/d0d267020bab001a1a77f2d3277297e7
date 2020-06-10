package fernuni.propra.algorithm;


/**
 * Specifies an interface for the construction of parts of an algorithm defined by consistent
 * instances of {@link ICandidateSearcher}, {@link IPositionOptimizer} and {@link IIlluminationTester}.
 * <p>
 * Implements the abstract factory design pattern. Subclasses, i.e. "concrete factories "
 * must implement this interface.
 * <p>
 * 
 * Extending classes: {@link AlgorithmFactory1}
 * 
 * 
 * @author alex
 *
 */
public abstract class AbstractAlgorithmFactory {
	
	/**
	 * Provides an instance of a "concrete factory", that can deliver consistent {@link ICandidateSearcher},
	 * {@link IPositionOptimizer} and {@link IIlluminationTester} objects.
	 * @return An instance of a "concrete factory".
	 */
	public static AbstractAlgorithmFactory getAlgorithmFactory() {
		return AlgorithmFactory1.getAlgorithmFactory1();
	}
	
	/**
	 * Delivers an instance of {@link ICandidateSearcher} that works with the algorithms defined by the "concrete factory".
	 * @return A consistent instance of {@link ICandidateSearcher}.
	 */
	public abstract ICandidateSearcher createCandidateSearcher();
	
	/**
	 * Delivers an instance of {@link IPositionOptimizer} that works with the algorithms defined by the "concrete factory".
	 * @return A consistent instance of {@link IPositionOptimizer}.
	 */
	public abstract IPositionOptimizer createPositionOptimizer();
	
	/**
	 * Delivers an instance of {@link IIlluminationTester} that works with the algorithms defined by the "concrete factory".
	 * @return A consistent instance of {@link IIlluminationTester}
	 */
	public abstract IIlluminationTester createIlluminiationTester();

}
