package com.dessertoasis.demo.model.recipe;

import com.dessertoasis.demo.model.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="recipeCategory")
public class RecipeCategory {
	
//	@EmbeddedId
//	private RecipeCategoryKey id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
//	@JsonIgnoreProperties("recipeCategories")
	@JsonIgnore
	@ManyToOne
//	@MapsId("recipeId")
	@JoinColumn(name = "recipeId",referencedColumnName = "id")
	private Recipes recipe;
	
//	@JsonIgnoreProperties("recipeCategories")
	@JsonIgnore
	@ManyToOne
//	@MapsId("categoryId")
	@JoinColumn(name = "categoryId",referencedColumnName = "id")
	private Category category;
	
	public RecipeCategory() {
	}

}
