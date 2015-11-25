package iavanish.analysis;

public class RaceDetectedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RaceDetectedException(String exception) {
		super(exception);
		System.err.println("Race Detected at: " + exception);
		System.exit(1);
	}
	
}
