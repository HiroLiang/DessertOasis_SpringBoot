package com.dessertoasis.demo.model.recipe;

import java.util.List;

import com.dessertoasis.demo.model.category.Category;

public class RecipeCreateDTO {
	
	private Recipes recipe;
	
	private List<Ingredient> ingredients;
	
	private List<Category> categories;

	public RecipeCreateDTO() {
		super();
	}

	public RecipeCreateDTO(Recipes recipe, List<Ingredient> ingredients, List<Category> categories) {
		super();
		this.recipe = recipe;
		this.ingredients = ingredients;
		this.categories = categories;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
		
}
