package fernuni.propra.internal_data_model;

/**
 * Thrown if any of the operations of {@link LineSegment} fail.
 * 
 * @author alex
 *
 */
public class LineSegmentException extends Exception {

	public LineSegmentException() {
		super();
	}

	public LineSegmentException(String message) {
		super(message);
	}

	public LineSegmentException(Throwable cause) {
		super(cause);
	}

}
