package fernuni.propra.algorithm;

/**
 * An exception that is thrown if the search of potential candidates for lamp
 * positions fails due to some unexpected error.
 * 
 * @author alex
 *
 */
public class CandidateSearcherException extends Exception {

	public CandidateSearcherException() {

	}

	public CandidateSearcherException(String message) {
		super(message);

	}

	public CandidateSearcherException(Throwable cause) {
		super(cause);

	}

}
