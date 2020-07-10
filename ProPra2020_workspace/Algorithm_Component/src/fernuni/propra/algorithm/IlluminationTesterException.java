package fernuni.propra.algorithm;

/**
 * An exception that is thrown if test that checks whether a room is illuminated
 * or not fails due to some unexpected error.
 * <p>
 * 
 * @author alex
 *
 */
public class IlluminationTesterException extends Exception {
	public IlluminationTesterException() {
	}

	public IlluminationTesterException(String message) {
		super(message);
	}

	public IlluminationTesterException(Throwable cause) {
		super(cause);
	}

}
