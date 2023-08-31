package com.dessertoasis.demo.model.recipe;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RecipeCategoryKey implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer recipeId;
	
	private Integer categoryId;	

	public RecipeCategoryKey() {
	
	}

	public RecipeCategoryKey(Integer recipeID, Integer categoryId) {
		this.recipeId = recipeID;
		this.categoryId = categoryId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, recipeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeCategoryKey other = (RecipeCategoryKey) obj;
		return Objects.equals(categoryId, other.categoryId) && Objects.equals(recipeId, other.recipeId);
	}

	
	
}
