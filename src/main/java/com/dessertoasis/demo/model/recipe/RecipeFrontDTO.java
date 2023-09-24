package com.dessertoasis.demo.model.recipe;

import java.time.LocalDateTime;

public class RecipeFrontDTO {

	private Integer id;

	private String recipeTitle;

	private String fullName;

	private Integer recipeCategories;

	private LocalDateTime recipeCreateDate;

	private String pictureURL;

	private String difficulty;
	
	private String recipeIntroduction;
	

	public RecipeFrontDTO() {
		super();
	}

	public RecipeFrontDTO(Integer id, String recipeTitle, String fullName, Integer recipeCategories,
			LocalDateTime recipeCreateDate, String pictureURL, String recipeIntroduction) {
		super();
		this.id = id;
		this.recipeTitle = recipeTitle;
		this.fullName = fullName;
		this.recipeCategories = recipeCategories;
		this.recipeCreateDate = recipeCreateDate;
		this.pictureURL = pictureURL;
		this.recipeIntroduction = recipeIntroduction;
	}
	
	public RecipeFrontDTO(Integer id, String recipeTitle, String fullName,
			String pictureURL,String difficulty, String recipeIntroduction) {
		super();
		this.id = id;
		this.recipeTitle = recipeTitle;
		this.fullName = fullName;
		this.pictureURL = pictureURL;
		this.difficulty = difficulty;
		this.recipeIntroduction = recipeIntroduction;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecipeTitle() {
		return recipeTitle;
	}

	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getRecipeCategories() {
		return recipeCategories;
	}

	public void setRecipeCategories(Integer recipeCategories) {
		this.recipeCategories = recipeCategories;
	}

	public LocalDateTime getRecipeCreateDate() {
		return recipeCreateDate;
	}

	public void setRecipeCreateDate(LocalDateTime recipeCreateDate) {
		this.recipeCreateDate = recipeCreateDate;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getRecipeIntroduction() {
		return recipeIntroduction;
	}

	public void setRecipeIntroduction(String recipeIntroduction) {
		this.recipeIntroduction = recipeIntroduction;
	}

}
