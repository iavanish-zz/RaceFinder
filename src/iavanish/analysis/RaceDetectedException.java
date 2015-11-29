package iavanish.analysis;

import org.aspectj.lang.reflect.SourceLocation;

//	Custom Exception class. Just for good Object Oriented practice, nothing special.
public class RaceDetectedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RaceDetectedException(SourceLocation exception) {
		super(exception.toString());
	}
	
}
