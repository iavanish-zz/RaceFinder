package iavanish.analysis;

import org.aspectj.lang.reflect.SourceLocation;

public class RaceDetectedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RaceDetectedException(SourceLocation exception) {
		System.out.println("Race Detected at: " + exception);
		//	System.exit(1);
	}
	
}
