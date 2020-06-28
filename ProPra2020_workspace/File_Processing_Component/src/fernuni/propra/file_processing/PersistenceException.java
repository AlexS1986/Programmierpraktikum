package fernuni.propra.file_processing;

/**
 * Thrown if a persistence operation fails.
 * @author alex
 *
 */
public class PersistenceException extends Exception {

	public PersistenceException() {
		super();
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}

}
