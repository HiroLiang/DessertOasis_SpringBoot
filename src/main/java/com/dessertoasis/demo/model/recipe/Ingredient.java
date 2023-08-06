package com.dessertoasis.demo.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {

	//食材編號
	@Id @Column(name = "ingredientID")
	@GeneratedValue
	private Integer ingredientID;
	
	//分類ID 待連接classification
	@Column(name = "categoryID")
	private Integer categoryID;
	
	//食材名稱
	@Column(name = "ingredientName")
	private String ingredientName;
	
	//份量單位
	@Column(name = "ingredientUnit")
	private String ingredientUnit;
	
	public Ingredient() {
	}

	public Integer getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(Integer ingredientID) {
		this.ingredientID = ingredientID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(String ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}
	
}
