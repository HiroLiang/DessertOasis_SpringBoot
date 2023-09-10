package com.dessertoasis.demo.model.recipe;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
public class RecipeIngredientKey implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer recipeId;

	private Integer ingredientId;

	public RecipeIngredientKey() {
	}
	
	public RecipeIngredientKey(Integer recipeId, Integer ingredientId) {
		this.recipeId = recipeId;
		this.ingredientId = ingredientId;
	}
 
	
	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Integer ingredientId) {
		this.ingredientId = ingredientId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(ingredientId, recipeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeIngredientKey other = (RecipeIngredientKey) obj;
		return Objects.equals(ingredientId, other.ingredientId) && Objects.equals(recipeId, other.recipeId);
	}
	
}
