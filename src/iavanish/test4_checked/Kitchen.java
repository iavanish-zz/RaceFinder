package iavanish.test4_checked;
public class Kitchen {
	CookingTable table;
	Storage store;
	ReplenishStock stock;
	
	public Kitchen() {
		table=new CookingTable();
		store=new Storage();
		stock=new ReplenishStock(store);
	}
	
	public synchronized void currentStock(){
		for(Ingredient key: store.ingredients.keySet()){
			System.out.println(key.getName()+" :: "+store.ingredients.get(key));				
        } 
	}

}
