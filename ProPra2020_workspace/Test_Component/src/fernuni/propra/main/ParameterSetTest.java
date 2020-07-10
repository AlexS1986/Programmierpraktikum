package fernuni.propra.main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParameterSetTest {
	private ParameterSet p1, p3;
	private String[] validRunParameters = { "s", "sd", "v", "vd", "d" };

	@Before
	public void setUp() throws Exception {
		p1 = new ParameterSet();
		p3 = new ParameterSet();
	}

	@Test
	public void testSetRunParameter() {
		// Arrange
		String invalidParameter = "test";

		// Act
		// valid parameters
		boolean parameterAccepted = true;
		for (String validParameter : validRunParameters) {
			ParameterSet p2 = new ParameterSet();
			try {
				p2.setRunParameter(validParameter);
			} catch (ParameterSetException pe) {
				parameterAccepted = false;
			}
		}

		// invalid parameters
		boolean invalidParameterAccepted = true;
		try {
			p1.setRunParameter(invalidParameter);
		} catch (ParameterSetException pe) {
			invalidParameterAccepted = false;
		}

		// Assert
		assertTrue("A valid parameter has not been " + "accepted", parameterAccepted);
		assertFalse("An invalid parameter has been" + "accepted,", invalidParameterAccepted);

	}

	@Test
	public void testSetInputFile() {
		// Arrange

		// Act
		boolean validParameterAccepted = false;
		try {
			p1.setInputFile("file");
			validParameterAccepted = true;
		} catch (ParameterSetException e) {
		}

		// Act
		boolean invalidParameterAccepted = true;
		try {
			p1.setInputFile(null);
		} catch (ParameterSetException e) {
			invalidParameterAccepted = false;
		}

		// Assert
		assertTrue("A valid parameter has not been " + "accepted", validParameterAccepted);
		assertFalse("An invalid parameter has been" + "accepted,", invalidParameterAccepted);
	}

	@Test
	public void testSetTimeLimit() {
		// Arrange

		// Act
		boolean firstSetWorked = false;
		boolean secondSetWorked = false;
		try {
			p1.setTimeLimit(1);
			firstSetWorked = true;
			p1.setTimeLimit(0);
			secondSetWorked = true;
		} catch (ParameterSetException pe) {

		}

		// Assert
		assertTrue("The first set should have worked", firstSetWorked);
		assertFalse("A second setTimeLimit call should not have" + "been accepted wo Exception.", secondSetWorked);
	}

	@Test
	public void testIsValidParameterSet() {
		// Arrange, Act

		// solve
		boolean testOnlyRunAccepted = true;
		try {
			p1.setRunParameter("sd");
			testOnlyRunAccepted = p1.isValidParameterSet();
		} catch (ParameterSetException e) {
			testOnlyRunAccepted = false;
		}

		boolean testRunPlusIFWorked = true;
		try {
			p1.setInputFile("test");
			p1.isValidParameterSet();
		} catch (ParameterSetException e) {
			testRunPlusIFWorked = false;
		}

		boolean testAllNecessaryParametersWorked = false;
		try {
			p1.setTimeLimit(1);
			p1.isValidParameterSet();
			testAllNecessaryParametersWorked = true;
		} catch (ParameterSetException e) {
		}

		// validate
		boolean testOnlyRunAcceptedV = true;
		try {
			p3.setRunParameter("v");
			p3.isValidParameterSet();
		} catch (ParameterSetException e) {
			testOnlyRunAcceptedV = false;
		}

		boolean testAllNecessaryParametersWorkedV = false;
		try {
			p3.setInputFile("test");
			p3.isValidParameterSet();
			testAllNecessaryParametersWorkedV = true;
		} catch (ParameterSetException e) {
		}

		// Assert

		// solve
		assertFalse("More than the r parameter is required", testOnlyRunAccepted);
		assertFalse("A time limit parameter is required as well", testRunPlusIFWorked);
		assertTrue("This parameter set should have worked", testAllNecessaryParametersWorked);

		// solve
		assertFalse("Just the run parameter is not enough!", testOnlyRunAcceptedV);
		assertTrue("This parameter set should have worked", testAllNecessaryParametersWorkedV);

	}

}