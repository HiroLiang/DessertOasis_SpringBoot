package com.dessertoasis.demo.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ingredientList")
public class IngredientList {

	//複合主鍵
	@EmbeddedId
	private RecipeIngredientKey id;
	
	//食譜id
	@ManyToOne
	@MapsId("recipeId")
	@JoinColumn(name = "recipeId", referencedColumnName = "recipeId")
	private Recipes recipe;
	
	//食材id
	@ManyToOne
	@MapsId("ingredientId")
	@JoinColumn(name = "ingredientID", referencedColumnName ="ingredientId")
	private Ingredient ingredient;
	
	//食材份量
	@Column(name="ingredientQuantity",nullable=false,columnDefinition = "FLOAT")
	private float ingredientQuantity;
	
	//食材單位
	@Column(name="ingredientUnit",nullable=false,columnDefinition = "nvarchar(20)")
	private String ingredientUnit;
	
	public IngredientList() {
	}

}
