package iavanish.test4;
import java.util.ArrayList;

public class ReplenishStock {
	ArrayList<Ingredient> ings;
	Storage store;
	
	public ReplenishStock(Storage s) {
		this.ings=new ArrayList<Ingredient>();
		this.store=s;
	}
	
	public synchronized void  replenish(){
		for (int i = 0; i < ings.size(); i++) {
			store.addIngredient(ings.get(i));
		} 
		ings.clear();
				
	}
	
	public synchronized void require(Ingredient e){
		ings.add(e);
		
	}
	
	public void print(){
		System.out.println("Stock to be filled");
		for (int i = 0; i < ings.size(); i++) {
			System.out.println(ings.get(i).name);
		} 
	}

}
