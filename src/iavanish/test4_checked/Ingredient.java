package iavanish.test4_checked;
import java.util.Random;

public class Ingredient {
	char name;
	int reqdQuantity;
	Random random;
	
	public Ingredient() {
		
	}
	public Ingredient(char name) {	
		this.name=name;
	}

	public boolean equals(Object obj) {
		Ingredient other = (Ingredient) obj;
		if (this.name!=other.name)
			return false;
		return true;
	}

	public int getQuantity() {
		return reqdQuantity;
	}

	public void setQuantity(int quantity) {
		this.reqdQuantity = quantity;
	}

	public char getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Ingredient [name=" + name + ", reqdQuantity=" + reqdQuantity + "]";
	}

	
	
}
