package iavanish.test2_checked;
	
public class TargetClass {

	public static int sharedUnsynchronized;
	public static int sharedSynchronized;
	
	public static synchronized void incrementUnsynchronized() {
		for(int i = 0; i < 10; i++) {
			sharedUnsynchronized++;
		}
	}
	
	public static synchronized void decrementUnsynchronized() {
		for(int i = 0; i < 10; i++) {
			sharedUnsynchronized--;
		}
	}
	
	public static synchronized void incrementSynchronized() {
		for(int i = 0; i < 10; i++) {
			sharedSynchronized++;
		}
	}
	
	public static synchronized void decrementSynchronized() {
		for(int i = 0; i < 10; i++) {
			sharedSynchronized--;
		}
	}
	
}
