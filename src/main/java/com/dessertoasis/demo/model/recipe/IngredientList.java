package com.dessertoasis.demo.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredientList")
public class IngredientList {

	@EmbeddedId
	private RecipeIngredientKey id;
	
	@ManyToOne
	@MapsId("recipeID")
	@JoinColumn(name = "recipeID", referencedColumnName = "recipeID")
	private Recipe recipe;
	
	@ManyToOne
	@MapsId("ingredientID")
	@JoinColumn(name = "ingredientID", referencedColumnName ="ingredientID")
	private Ingredient ingredient;
	
	@Column(name="ingredientQuantity")
	private double ingredientQuantity;
	
		
	
	public RecipeIngredientKey getId() {
		return id;
	}



	public void setId(RecipeIngredientKey id) {
		this.id = id;
	}



	public Recipe getRecipe() {
		return recipe;
	}



	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}



	public Ingredient getIngredient() {
		return ingredient;
	}



	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}



	public double getIngredientQuantity() {
		return ingredientQuantity;
	}



	public void setIngredientQuantity(double ingredientQuantity) {
		this.ingredientQuantity = ingredientQuantity;
	}



	public IngredientList() {
	}

}
