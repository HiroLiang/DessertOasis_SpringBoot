package com.dessertoasis.demo.model.recipe;

import java.util.Date;
import java.util.List;

public class RecipeCreateDTO {
	
	//食譜名稱
	private String recipeTitle;
	
	//食譜簡介
	private String recipeIntroduction;
	
	//成品圖圖庫位址
	private String pictureURL;
	
	//食譜人分
	private Integer ingredientPersons;
	
	//製作時間
	private Integer cookingTime;
	
	//難度
	private String difficulty;
	
	//撰寫者ID(連結Member id)
	private Integer recipeAuthorId;
		
	//分類Id取其中分類id
	private List<Integer>recipeCategoryIds;
	
	//步驟
	private List<String>steps;
		
	//食譜建立時間
	private Date recipeCreateDate;
	
	//食譜狀態
	private Integer recipeStatus;
		
		

}
