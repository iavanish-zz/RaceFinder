package iavanish.test1_updated_checked;

public class SimpleThreads implements Runnable {

	private Thread thread;
	private String threadName;
	
	public SimpleThreads(String threadName) {
		this.threadName = threadName;
	}
	
	public void run() {
		if(threadName.equals("thread1")) {
			TargetClass.incrementSynchronized();
			TargetClass.incrementUnsynchronized();
		}
		else if(threadName.equals("thread2")) {
			TargetClass.decrementSynchronized();
			TargetClass.decrementUnsynchronized();
		}
	}
	
	public void start() {
		
		if(thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
		
	}
	
}
