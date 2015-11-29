package iavanish.test4_checked;
public class PrintStatus {
		
	static PrintStatus obj;
	
	static{
		obj=new PrintStatus();
	}
	
	private PrintStatus() {
		
	}
	
	
	public static PrintStatus getInstance(){
		synchronized(PrintStatus.class) {
			return obj;
		}
	}


	public synchronized void ssPrint(ServiceStaff ss){
		System.out.println("ServiceStaff name=" + ss.name);
		System.out.println("Stock:");
		ss.kitchen.currentStock();
		System.out.println(ss);
	}
	
	public synchronized void ckPrint(Cook c){
		System.out.println(c);
	}

}
