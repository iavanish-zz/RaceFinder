package iavanish.test4;
public class MainClass {

	public static void main(String[] args) throws InterruptedException {
		Kitchen kitchen=new Kitchen();
		int numOfCooks=4;
		int numOfSS=2;
		
		kitchen.currentStock();
		System.out.println();
		
		Cook[] cooks=new Cook[numOfCooks];
		ServiceStaff[] ss=new ServiceStaff[numOfSS];
		Thread[] cookThread=new Thread[numOfCooks];
		Thread[] ssThread=new Thread[numOfSS];
		
		for (int i = 0; i < ss.length; i++) {
			ss[i]=new ServiceStaff(kitchen);
			ssThread[i]=new Thread(ss[i]);
			ssThread[i].start();			
		}
		
		for (int i = 0; i < cooks.length; i++) {
			cooks[i]=new Cook(kitchen);
			cookThread[i]=new Thread(cooks[i]);
			cookThread[i].start();			
		}
		
		for (int i = 0; i < cooks.length; i++) {
			cookThread[i].join();			
		}
		
		for (int i = 0; i < ss.length; i++) {
			ssThread[i].interrupt();			
		}
		System.out.println("Finishing for the day..Bye");
	}
}
