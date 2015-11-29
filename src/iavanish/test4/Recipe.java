package iavanish.test4;
import java.util.Random;

public class Recipe {
	
	Ingredient ingredient1;
	Ingredient ingredient2;
	int cookingTime;
	Random random;
	
	public Recipe() {
		random=new Random();
		ingredient1=new Ingredient((char)(65+random.nextInt(5)));
		ingredient2=new Ingredient((char)(65+random.nextInt(5)));
		while(ingredient2.equals(ingredient1))
			ingredient2=new Ingredient((char)(65+random.nextInt(5)));
		cookingTime=2000+random.nextInt(10000);
		ingredient1.setQuantity(2+random.nextInt(9));
		ingredient2.setQuantity(2+random.nextInt(9));
	}

	
	public Ingredient getIngredient1() {
		return ingredient1;
	}

	
	public Ingredient getIngredient2() {
		return ingredient2;
	}

	
	public int getCookingTime() {
		return cookingTime;
	}


	@Override
	public String toString() {
		return "Recipe [ingredient1=" + ingredient1 + ", ingredient2=" + ingredient2 + ", cookingTime=" + cookingTime
				+ "]";
	}

	

}
