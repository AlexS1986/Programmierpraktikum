package fernuni.propra.algorithm;

/**
 * Thrown if something went wrong within the validation algorithm, i.e. the
 * check whether an {@link IRoom} is illuminated by its {@link Lamp}s.
 * <p>
 * 
 * @author alex
 *
 */
public class ValidateKException extends Exception {

	public ValidateKException() {
	}

	public ValidateKException(String message) {
		super(message);
	}

	public ValidateKException(Throwable cause) {
		super(cause);
	}

}
