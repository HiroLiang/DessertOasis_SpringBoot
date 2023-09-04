package com.dessertoasis.demo.model.recipe;

import java.util.Date;
import java.util.List;

public class RecipeDTO {
	//食譜id
	private Integer id;
	
	//撰寫者ID(連結Member id)
	private Integer recipeAuthorId;
	
	//分類Id取其中分類id
	private List<Integer>recipeCategoryIds;
	
	//食譜名稱
	private String recipeTitle;
	
	//成品圖圖庫位址
	private String pictureURL;
	
	//食譜簡介
	private String recipeIntroduction;
	
	//製作時間
	private Integer cookingTime;
	
	//難度
	private String difficulty;
	
	//食譜建立時間
	private Date recipeCreateDate;
	
	//食譜狀態
	private Integer recipeStatus;
	
	//食譜每月計數器(統計每月熱門食譜)
	private Integer recipeMonthlyVisitCount;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRecipeAuthorId() {
		return recipeAuthorId;
	}

	public void setRecipeAuthorId(Integer recipeAuthorId) {
		this.recipeAuthorId = recipeAuthorId;
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

	public Date getRecipeCreateDate() {
		return recipeCreateDate;
	}

	public void setRecipeCreateDate(Date recipeCreateDate) {
		this.recipeCreateDate = recipeCreateDate;
	}

	public Integer getRecipeStatus() {
		return recipeStatus;
	}

	public void setRecipeStatus(Integer recipeStatus) {
		this.recipeStatus = recipeStatus;
	}

	public Integer getRecipeMonthlyVisitCount() {
		return recipeMonthlyVisitCount;
	}

	public void setRecipeMonthlyVisitCount(Integer recipeMonthlyVisitCount) {
		this.recipeMonthlyVisitCount = recipeMonthlyVisitCount;
	}
	
	
}
