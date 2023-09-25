package com.dessertoasis.demo.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ingredientList")
public class IngredientList {

//	//複合主鍵
//	@EmbeddedId
//	private RecipeIngredientKey id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 食譜id
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@MapsId("recipeId")
	@JoinColumn(name = "recipeId")
//	@JsonIgnoreProperties({"recipeAuthor", "recipeCategories","recipeSteps",
//		"ingredientList","ingredientList","recipeTitle","pictureURL",
//		"recipeIntroduction","cookingTime","difficulty","recipeCreateDate",
//		"recipeStatus","recipeMonthlyVisitCount","course"})
	@JsonIgnoreProperties(value="ingredientList",allowSetters = true)
//	@JsonIgnore
	private Recipes recipe;

	// 食材id
//	@MapsId("ingredientId")
	@ManyToOne
	@JoinColumn(name = "ingredientId")

	@JsonIgnoreProperties(value="ingredientList",allowSetters = true)
	private Ingredient ingredient;

	// 食材份量
	@Column(name = "ingredientQuantity", nullable = false, columnDefinition = "FLOAT")
	private float ingredientQuantity;

	// 食材單位
	@Column(name = "ingredientUnit", nullable = false, columnDefinition = "nvarchar(20)")
	private String ingredientUnit;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public IngredientList() {
	}

//	public RecipeIngredientKey getId() {
//		return id;
//	}
//
//	public void setId(RecipeIngredientKey id) {
//		this.id = id;
//	}

	public Recipes getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipes integer) {
		this.recipe = integer;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public float getIngredientQuantity() {
		return ingredientQuantity;
	}

	public void setIngredientQuantity(float ingredientQuantity) {
		this.ingredientQuantity = ingredientQuantity;
	}

	public String getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(String ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}

}
