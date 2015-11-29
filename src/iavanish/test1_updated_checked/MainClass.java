package iavanish.test1_updated_checked;

public class MainClass {

	public static void main(String[] args) {

		SimpleThreads thread1 = new SimpleThreads("thread1");
		SimpleThreads thread2 = new SimpleThreads("thread2");
		thread1.start();
		thread2.start();
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		synchronized(TargetClass.class) {
			System.out.println("sharedUnsynchronized: " + TargetClass.sharedUnsynchronized);
			System.out.println("sharedSynchronized: " + TargetClass.sharedSynchronized);
		}
		
	}

}
