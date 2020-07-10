package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AlgorithmFactory1Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateCandidateSearcher() {
		// Act
		boolean isICandidateSearcher = AbstractAlgorithmFactory.getAlgorithmFactory()
				.createCandidateSearcher() instanceof ICandidateSearcher;

		// Assert
		assertTrue(isICandidateSearcher);

	}

	@Test
	public void testCreatePositionOptimizer() {
		// Act
		boolean isIPositionOptimizer = AbstractAlgorithmFactory.getAlgorithmFactory()
				.createPositionOptimizer() instanceof IPositionOptimizer;

		// Assert
		assertTrue(isIPositionOptimizer);
	}

	@Test
	public void testCreateIlluminiationTester() {
		// Act
		boolean isIlluminationTester = AbstractAlgorithmFactory.getAlgorithmFactory()
				.createIlluminiationTester() instanceof IIlluminationTester;

		// Assert
		assertTrue(isIlluminationTester);
	}

	@Test
	public void testCreateOriginalPartialRectanglesFinder() {
		// Act
		boolean isOriginalPartialRectanglesFinder = AbstractAlgorithmFactory.getAlgorithmFactory()
				.createOriginalPartialRectanglesFinder() instanceof IOriginalPartialRectanglesFinder;

		// Assert
		assertTrue(isOriginalPartialRectanglesFinder);
	}

	@Test
	public void testGetAlgorithmFactory1() {
		// Act
		AbstractAlgorithmFactory af1 = AbstractAlgorithmFactory.getAlgorithmFactory();
		AbstractAlgorithmFactory af2 = AbstractAlgorithmFactory.getAlgorithmFactory();

		// Assert
		assertSame(af1, af2);
	}

}
