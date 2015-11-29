package iavanish.test1_updated_checked;
	
public class TargetClass {

	public static int sharedUnsynchronized;
	public static int sharedSynchronized;
	
	public static void incrementUnsynchronized() {
		synchronized(TargetClass.class) {
			for(int i = 0; i < 100; i++) {
				sharedUnsynchronized++;
			}
		}
	}
	
	public static void decrementUnsynchronized() {
		synchronized(TargetClass.class) {
			for(int i = 0; i < 100; i++) {
				sharedUnsynchronized--;
			}
		}
	}
	
	public static synchronized void incrementSynchronized() {
		for(int i = 0; i < 100; i++) {
			sharedSynchronized++;
		}
	}
	
	public static synchronized void decrementSynchronized() {
		for(int i = 0; i < 100; i++) {
			sharedSynchronized--;
		}
	}
	
}
