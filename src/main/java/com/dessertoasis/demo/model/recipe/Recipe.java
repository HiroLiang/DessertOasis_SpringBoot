package com.dessertoasis.demo.model.recipe;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.membership.Membership;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity @Table(name = "recipes")
public class Recipe {

	@Id @Column(name="recipeID") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeID;
	
	//食譜名稱
	@Column(name = "recipeTitle")
	private String recipeTitle;
	
	//成品圖圖庫位址
	@Column(name = "pictureURL")
	private String pictureURL;
	
	//食譜簡介
	@Column(name = "recipeIntroduction")
	private String recipeIntroduction;
	
	//食譜分類ID
	@Column(name = "categoryID")
	private Integer categoryID;
	
	//撰寫者ID(連結Member id)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="recipeAuthorID")
	private Membership recipeAuthorID;
	
	//製作時間
	@Column(name = "cookingTime")
	private Integer cookingTime;
	
	//難度
	@Column(name = "difficulty")
	private String difficulty;
	
	//食譜步驟
	@Column(name = "recipeSteps")
	private String recipeSteps;
	
	//食譜建立時間
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recipeCreateDate")
	private Date recipeCreateDate;
	
	//食譜每月計數器(統計每月熱門食譜)
	@Column(name = "recipeMonthlyVisitCount")
	private Integer recipeMonthlyVisitCount;
	
	public Recipe() {
	}

	public Recipe(String recipeTitle, String pictureURL, String recipeIntroduction,
			Integer categoryID, Membership recipeAuthorID, Integer cookingTime, String difficulty, String recipeSteps) {
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

	public Membership getRecipeAuthorID() {
		return recipeAuthorID;
	}

	public void setRecipeAuthorID(Membership recipeAuthorID) {
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
