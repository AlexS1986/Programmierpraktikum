package fernuni.propra.main;

/**
 * Thrown if something went wrong in methods of
 * {@link ParameterSet}.
 * <p>
 * @author alex
 *
 */
public class ParameterSetException extends Exception {

	public ParameterSetException() {
	}

	public ParameterSetException(String message) {
		super(message);
		
	}

	public ParameterSetException(Throwable cause) {
		super(cause);
		
	}

}
