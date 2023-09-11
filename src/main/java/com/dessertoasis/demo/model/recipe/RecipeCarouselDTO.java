package com.dessertoasis.demo.model.recipe;

import java.util.List;

public class RecipeCarouselDTO {
	//食譜id
	private Integer id;
	
	//分類Id取其中分類id
	private List<Integer>recipeCategoryIds;
	
	//食譜名稱
	private String recipeTitle;
	
	//成品圖圖庫位址
	private String pictureURL;
	
	//食譜簡介
	private String recipeIntroduction;
	
	public RecipeCarouselDTO(Integer id, List<Integer> recipeCategoryIds, String recipeTitle, String pictureURL,
			String recipeIntroduction) {
		super();
		this.id = id;
		this.recipeCategoryIds = recipeCategoryIds;
		this.recipeTitle = recipeTitle;
		this.pictureURL = pictureURL;
		this.recipeIntroduction = recipeIntroduction;
	}

	public RecipeCarouselDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Integer> getRecipeCategoryIds() {
		return recipeCategoryIds;
	}

	public void setRecipeCategoryIds(List<Integer> recipeCategoryIds) {
		this.recipeCategoryIds = recipeCategoryIds;
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
	

	
}
