package com.dessertoasis.demo.model.recipe;

import com.dessertoasis.demo.model.category.Category;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="recipeCategory")
public class RecipeCategory {
	
	@EmbeddedId
	private RecipeCategoryKey id;

	@ManyToOne
	@MapsId("recipeId")
	@JoinColumn(name = "recipeId")
	private Recipes recipe;
	
	@ManyToOne
	@MapsId("categoryId")
	@JoinColumn(name = "categoryId")
	private Category category;
	
	public RecipeCategory() {
	}

}
