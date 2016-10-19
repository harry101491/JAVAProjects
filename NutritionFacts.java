package com.harshit;

/**
 * 
 * @author harshitpareek
 * This program provides a better alternative to the problem in which a class contain 
 * some required fields and lot more optional fields. We are trying to implement the 
 * builder pattern to solve the same. This approach is better than Javabeans approach
 * and Telescoping constructor approach.
 */
// first showing the telescoping constructor pattern
// In this approach we provide multiple constructors or static factories
// which contain one constructor having all required fields and all other 
// constructors contain one optional for every optional field.
// Note: this approach is not highly readable and it is hard to code the client side.
public class NutritionFacts 
{
	// implementing builder pattern
	private final int servingSize;  //(mL) required
	private final int servings;		// per container required
	private final int calories;      // optional
	private final int fat; 			// (g) optional
	private final int sodium; 		// (mg) optional
	private final int carbohydrate; // (g) optional
	
	public NutritionFacts(Builder builder) 
	{
		this.servingSize = builder.servingSize;
		this.servings = builder.servings;
		this.calories = builder.calories;
		this.fat = builder.fat;
		this.sodium = builder.sodium;
		this.carbohydrate = builder.carbohydrate;
	}
	
	public static class Builder
	{
		private final int servingSize;  //(mL) required
		private final int servings;		// per container required
		
		private int calories = 0;      // optional
		private int fat = 0; 			// (g) optional
		private int sodium = 0; 		// (mg) optional
		private int carbohydrate = 0; // (g) optional
		
		public Builder(int servingSize, int servings)
		{
			this.servingSize = servingSize;
			this.servings = servings;
		}
		public Builder calories(int calories)
		{
			this.calories = calories;
			return this;
		}
		public Builder fat(int fat)
		{
			this.fat = fat;
			return this;
		}
		public Builder sodium(int sodium)
		{
			this.sodium = sodium;
			return this;
		}
		public Builder carbohydrate(int carbohydrate)
		{
			this.carbohydrate = carbohydrate;
			return this;
		}
		// build method
		public NutritionFacts build()
		{
			return new NutritionFacts(this);
		}
	}
	/*
	// Javabeans pattern
	private int servingSize = -1;  //(mL) required
	private int servings = -1;		// per container required
	private int calories = 0;      // optional
	private int fat = 0; 			// (g) optional
	private int sodium = 0; 		// (mg) optional
	private int carbohydrate = 0; // (g) optional
	// setter methods for the java beans pattern
	public void setServingSize(int servingSize)
	{
		this.servingSize = servingSize;
	}
	*/
	/*
	// required and optional fields
	private final int servingSize;  //(mL) required
	private final int servings;		// per container required
	private final int calories;      // optional
	private final int fat; 			// (g) optional
	private final int sodium; 		// (mg) optional
	private final int carbohydrate; // (g) optional
	
	// constructor with the required fields
	public NutritionFacts (int servingSize, int servings)
	{
		this(servingSize,servings,0);
	}
	public NutritionFacts (int servingSize, int servings, int calories)
	{
		this(servingSize,servings,calories,0);
	}
	public NutritionFacts (int servingSize, int servings, int calories ,int fat)
	{
		this(servingSize,servings,calories,fat,0);
	}
	public NutritionFacts (int servingSize, int servings, int calories ,int fat,int sodium)
	{
		this(servingSize,servings,calories,fat,sodium,0);
	}
	public NutritionFacts (int servingSize, int servings, int calories ,int fat,int sodium,int carbohydrate)
	{
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
	*/
	@Override
	public String toString()
	{
		return String.format("Requried fields : %d, %d Optional fields : %d, %d, %d, %d", servingSize, servings, calories, fat, sodium, carbohydrate);
	}
	public static void main(String args[])
	{
		// constructing the object by the help of builder method
		NutritionFacts facts = new NutritionFacts.Builder(4, 120).fat(4).sodium(8).carbohydrate(0).build();
		
		//NutritionFacts facts = new NutritionFacts(5, 10, 0, 0, 5, 10);
		System.out.println(facts);
	}
}
