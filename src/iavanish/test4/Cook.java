package iavanish.test4;

public class Cook implements Runnable{
	Kitchen kitchen;
	Dish dish;
	String name;
	static int count;
	long totalTimeToCook;
	long waitAtStore;
	long waitAtCounter;
	
	
	public Cook(Kitchen kit) {
		this.kitchen=kit;
		dish=new Dish();
		name="Cook"+(++count);
	}
	
	
	public void run() {
		try {
			totalTimeToCook=System.currentTimeMillis();
			getIngredients();
			prepareDish();
			totalTimeToCook= System.currentTimeMillis()-totalTimeToCook;
			PrintStatus p=PrintStatus.getInstance();
			p.ckPrint(this);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void prepareDish() throws InterruptedException {		
		synchronized (kitchen.table) {
			//System.out.println(name+":I am cooking now");
			waitAtCounter=System.currentTimeMillis();
			while(kitchen.table.isDirty()){
				kitchen.table.wait();
			}
			waitAtCounter=System.currentTimeMillis()-waitAtCounter;
			Thread.sleep(dish.recipe.getCookingTime());
			kitchen.table.setDirty(true);
			//System.out.println(name+":I finished cooking");
		}
	}

	

	private void getIngredients() throws InterruptedException {		
		synchronized (kitchen.store) {
			//System.out.println(name+":I entered store to get ingredients");
			waitAtStore=System.currentTimeMillis();
			boolean ig1=kitchen.store.isIngredientAvailable(dish.recipe.getIngredient1());
			boolean ig2=kitchen.store.isIngredientAvailable(dish.recipe.getIngredient2());
			while(!ig1 && !ig2){
				//System.out.println("Ingredient not available.. I am going to wait now..");
				kitchen.store.isThreadWaiting=true;
				if(!ig1){
					kitchen.stock.require(dish.recipe.ingredient1);
				}
				if(!ig2){
					kitchen.stock.require(dish.recipe.ingredient2);
				}
				kitchen.store.wait();
				ig1=kitchen.store.isIngredientAvailable(dish.recipe.getIngredient1());
				ig2=kitchen.store.isIngredientAvailable(dish.recipe.getIngredient2());
			}
			waitAtStore=System.currentTimeMillis()-waitAtStore;
			kitchen.store.consumeIngredients(dish.recipe.getIngredient1(), dish.recipe.getIngredient2());
			Thread.sleep(1000);
			//System.out.println(name+":I collected I am going out");
			
		}
	}


	@Override
	public String toString() {
		return "Cook [name=" + name +", "+dish.recipe+", totalTimeToCook=" + totalTimeToCook + ", waitAtStore=" + waitAtStore
				+ ", waitAtCounter=" + waitAtCounter + "]";
	}	

}
