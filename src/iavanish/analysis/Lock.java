package iavanish.analysis;

import java.util.HashSet;
import java.util.Set;

public class Lock {

	Set <Object> locks;
	
	public Lock() {
		locks = new HashSet <Object> ();
	}
	
}
