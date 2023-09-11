package com.dessertoasis.demo.model.recipe;

import java.util.List;

public class RecipeCreateDTO {
	
	private Recipes recipe;
	
	private List<Ingredient> ingredients;

	public RecipeCreateDTO() {
		super();
	}

	public RecipeCreateDTO(Recipes recipe, List<Ingredient> ingredients) {
		super();
		this.recipe = recipe;
		this.ingredients = ingredients;
	}

	public Recipes getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipes recipe) {
		this.recipe = recipe;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
		

	
}
