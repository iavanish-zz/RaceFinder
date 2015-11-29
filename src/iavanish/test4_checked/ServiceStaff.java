package iavanish.test4_checked;
import java.util.Random;

public class ServiceStaff implements Runnable{
	Kitchen kitchen;
	String name;
	static int count;
	Random random;
	long waitAtStore;
	long waitAtCounter;
	
	public ServiceStaff(Kitchen kit) {
		this.kitchen=kit;
		this.name="SS"+(++count);
	}
	
	public void run() {
		random=new Random();
		PrintStatus p=PrintStatus.getInstance();
		while(true){
			try {
				Thread.sleep(50000);
								
				if(random.nextBoolean())
					cleanCounter();
				else{
					replenishStock();
				}
				p.ssPrint(this);
				
			} 
			catch (InterruptedException e) {
				break;
			}
			
		}
	}

	private void cleanCounter() throws InterruptedException {	
		waitAtCounter=System.currentTimeMillis();
		synchronized (kitchen.table) {
			waitAtCounter=System.currentTimeMillis()-waitAtCounter;
			if(kitchen.table.isDirty()){
				//System.out.println(name+":I am cleaning now");
				Thread.sleep(2000);
				kitchen.table.setDirty(false);
				kitchen.table.notifyAll();
				//System.out.println(name+":I finished cleaning");
			}
			
			
		}
	}

	private void replenishStock() throws InterruptedException {
		waitAtStore=System.currentTimeMillis();
		synchronized (kitchen.store) {	
			waitAtStore=System.currentTimeMillis()-waitAtStore;
			if(kitchen.store.isThreadWaiting){
				//System.out.println(name+":I entered store");
				kitchen.stock.replenish();
				System.out.println("After Replenishing..");
				kitchen.currentStock();
				kitchen.store.notifyAll();
				kitchen.store.isThreadWaiting=false;
				//System.out.println(name+":I replenished the stock I am goin out");
			}
			
			
		}
	}

	@Override
	public String toString() {
		return "[ waitAtStore=" + waitAtStore + ", Counter Status="+(kitchen.table.isDirty()?"dirty":"clean")+", waitAtCounter=" + waitAtCounter + "]";
	}

	
	
}
