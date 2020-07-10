package fernuni.propra.main;

/**
 * Thrown if the user handles the program incorrectly
 * 
 * @author alex
 *
 */
public class GeneralException extends Exception {

	public GeneralException() {
	}

	public GeneralException(String message) {
		super(message);
	}

	public GeneralException(Throwable cause) {
		super(cause);
	}

}
