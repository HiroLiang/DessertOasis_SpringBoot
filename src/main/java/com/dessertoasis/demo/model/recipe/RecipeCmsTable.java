package com.dessertoasis.demo.model.recipe;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecipeCmsTable {

	private Integer id;
	private String recipeTitle;
	private String fullName;
//	private Integer recipeCategories;
	private LocalDateTime recipeCreateDate;
	private Integer recipeStatus;
	private Integer recipeMonthlyVisitCount;
	
	
	public RecipeCmsTable() {
		super();
	}
	
	public RecipeCmsTable(Integer id, String recipeTitle, String fullName, /*Integer recipeCategories,*/
			LocalDateTime recipeCreateDate, Integer recipeStatus, Integer recipeMonthlyVisitCount) {
		super();
		this.id = id;
		this.recipeTitle = recipeTitle;
		this.fullName = fullName;
//		this.recipeCategories = recipeCategories;
		this.recipeCreateDate = recipeCreateDate;
		this.recipeStatus = recipeStatus;
		this.recipeMonthlyVisitCount = recipeMonthlyVisitCount;
	}
	
}
