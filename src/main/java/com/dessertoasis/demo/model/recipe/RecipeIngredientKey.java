package com.dessertoasis.demo.model.recipe;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RecipeIngredientKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "recipeID")
	private Integer recipeID;

	@Column(name = "ingredientID")
	private Integer ingredientID;

	public RecipeIngredientKey() {
	}
	
	

	public RecipeIngredientKey(Integer recipeID, Integer ingredientID) {
		this.recipeID = recipeID;
		this.ingredientID = ingredientID;
	}



	@Override
	public int hashCode() {
		return Objects.hash(recipeID,ingredientID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		RecipeIngredientKey that = (RecipeIngredientKey) obj;

		return recipeID == that.recipeID && ingredientID == that.ingredientID;
	}

}
