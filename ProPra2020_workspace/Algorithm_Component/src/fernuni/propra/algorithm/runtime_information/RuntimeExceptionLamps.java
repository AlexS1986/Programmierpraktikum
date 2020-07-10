package fernuni.propra.algorithm.runtime_information;

/**
 * Thrown if a data structure for the storing of runtime information, e.g.
 * {@link RuntimeInformation}, is used incorrectly. E.g. if methods are called
 * in a wrong order (time is stopped before a clock is started.
 * 
 * @author alex
 *
 */
public class RuntimeExceptionLamps extends Exception {

	public RuntimeExceptionLamps() {
	}

	public RuntimeExceptionLamps(String message) {
		super(message);
	}

	public RuntimeExceptionLamps(Throwable cause) {
		super(cause);
	}

}
