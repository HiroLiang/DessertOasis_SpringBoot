package com.dessertoasis.demo.model.recipe;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RecipeCategory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="recipeID")
	private Integer recipeID;
	
	@Column(name="categoryID")
	private Integer categoryId;

	public RecipeCategory() {
	
	}

	public RecipeCategory(Integer recipeID, Integer categoryId) {
		this.recipeID = recipeID;
		this.categoryId = categoryId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, recipeID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeCategory other = (RecipeCategory) obj;
		return Objects.equals(categoryId, other.categoryId) && Objects.equals(recipeID, other.recipeID);
	}

	
	
}
