package iavanish.test4_checked;
import java.util.HashMap;

public class Storage {
	HashMap<Ingredient,Integer> ingredients;
	boolean isThreadWaiting;
	
	public Storage() {
		ingredients=new HashMap<Ingredient,Integer>();	
		Ingredient ig;
		for (int i = 65; i < 70; i++) {
			ig=new Ingredient((char)(i));
			ig.setQuantity(0);
			ingredients.put(ig, 0);
		}
	}

	
	public synchronized void addIngredient(Ingredient e){
		e=findIng(e);
		ingredients.put(e, e.getQuantity()+10);
	}
	
	public synchronized boolean isIngredientAvailable(Ingredient e){
		Ingredient eg=findIng(e);
		//System.out.println(ingredients.get(eg)+":"+eg.name);
		if(ingredients.get(eg)>e.getQuantity())
			return true;
		return false;
	}
	
	public synchronized Ingredient findIng(Ingredient e){
		for(Ingredient key: ingredients.keySet()){
			if(key.equals(e))
				return key;
        } 		
		return e;
	}
	
	public synchronized void consumeIngredients(Ingredient e1,Ingredient e2){
		Ingredient eg1=findIng(e1);
		Ingredient eg2=findIng(e2);
		ingredients.put(eg1, ingredients.get(eg1)-e1.getQuantity());
		ingredients.put(eg2, ingredients.get(eg2)-e2.getQuantity());
	}
	
}
