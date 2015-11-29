package iavanish.analysis;

import java.util.HashSet;
import java.util.Set;

public class Lock {

	//	a set that can hold references to any type of lock
	Set <Object> locks;
	
	public Lock() {
		locks = new HashSet <Object> ();
	}
	
}
