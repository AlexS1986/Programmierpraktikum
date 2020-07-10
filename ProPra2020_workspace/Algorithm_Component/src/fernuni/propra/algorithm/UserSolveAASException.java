package fernuni.propra.algorithm;

/**
 * Thrown if something went wrong within the Solve use case (optimized
 * {@link Lamp} positions are found for an {@link IRoom})
 * 
 * @author alex
 *
 */
public class UserSolveAASException extends Exception {

	public UserSolveAASException() {
	}

	public UserSolveAASException(String message) {
		super(message);
	}

	public UserSolveAASException(Throwable cause) {
		super(cause);
	}

}
