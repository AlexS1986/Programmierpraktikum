package fernuni.propra.algorithm;

/**
 * Thrown if something went wrong with the validation of an {@link IRoom} instance.
 * @author alex
 *
 */
public class UserValidateAASException extends Exception {

	public UserValidateAASException() {
	}

	public UserValidateAASException(String message) {
		super(message);
	}

	public UserValidateAASException(Throwable cause) {
		super(cause);
	}

}
