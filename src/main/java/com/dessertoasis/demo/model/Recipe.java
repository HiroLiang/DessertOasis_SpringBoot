package com.dessertoasis.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "recipes")
public class Recipe {

	@Id @Column(name="RECIPEID") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeID;
	
	@Column(name = "RECIPETITLE")
	private String recipeTitle;
	
	@Column(name = "PICTUREURL")
	private String pictureURL;
	
	@Column(name = "RECIPEINTRODUCTION")
	private String recipeIntroduction;
	
	@Column(name = "CATEGORYID")
	private Integer categoryID;
	
	@Column(name = "RECIPEAUTHORID")
	private Integer recipeAuthorID;
	
	@Column(name = "COOKINGTIME")
	private Integer cookingTime;
	
	@Column(name = "DIFFICULTY")
	private String difficulty;
	
	@Column(name = "RECIPESTEPS")
	private String recipeSteps;
	
	public Recipe() {
	}

	
	
	@Override
	public String toString() {
		return "Recipe [recipeID=" + recipeID + ", recipeTitle=" + recipeTitle + ", pictureURL=" + pictureURL
				+ ", recipeIntroduction=" + recipeIntroduction + ", categoryID=" + categoryID + ", recipeAuthorID="
				+ recipeAuthorID + ", cookingTime=" + cookingTime + ", difficulty=" + difficulty + ", recipeSteps="
				+ recipeSteps + "]";
	}

	public Recipe(Integer recipeID, String recipeTitle, String pictureURL, String recipeIntroduction,
			Integer categoryID, Integer recipeAuthorID, Integer cookingTime, String difficulty, String recipeSteps) {
		super();
		this.recipeID = recipeID;
		this.recipeTitle = recipeTitle;
		this.pictureURL = pictureURL;
		this.recipeIntroduction = recipeIntroduction;
		this.categoryID = categoryID;
		this.recipeAuthorID = recipeAuthorID;
		this.cookingTime = cookingTime;
		this.difficulty = difficulty;
		this.recipeSteps = recipeSteps;
	}

	public Integer getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(Integer recipeID) {
		this.recipeID = recipeID;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getRecipeIntroduction() {
		return recipeIntroduction;
	}

	public void setRecipeIntroduction(String recipeIntroduction) {
		this.recipeIntroduction = recipeIntroduction;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public Integer getRecipeAuthorID() {
		return recipeAuthorID;
	}

	public void setRecipeAuthorID(Integer recipeAuthorID) {
		this.recipeAuthorID = recipeAuthorID;
	}

	public Integer getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Integer cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getRecipeSteps() {
		return recipeSteps;
	}

	public void setRecipeSteps(String recipeSteps) {
		this.recipeSteps = recipeSteps;
	}

}
