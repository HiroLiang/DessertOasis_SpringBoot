package com.dessertoasis.demo.model.recipe;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Entity @Table(name = "recipes")
public class Recipes {

	@Id @Column(name="id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//撰寫者ID(連結Member id)
	@JsonIgnoreProperties(value = {"account","email","passwords","emailForCode","code","access","memberStatus","signDate","verificationToken","memberDetail","teacher","recipes","otp","otpGeneratedTime","bank"},allowSetters = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="memberId", nullable = true)
	private Member recipeAuthor;
	
	//分類Id  OneToMany
	@JsonIgnoreProperties(value="recipe", allowSetters=true)
	@OneToMany(mappedBy = "recipe")
	private List<RecipeCategory> recipeCategories;
	
	//食譜步驟
	@JsonIgnoreProperties(value="recipe", allowSetters=true)
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<RecipeSteps> recipeSteps;
	
	@JsonIgnoreProperties(value="recipe", allowSetters=true)
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<IngredientList> ingredientList;
	
	//食譜名稱
	@Column(name = "recipeTitle", nullable=true,columnDefinition = "nvarchar(100)")
	private String recipeTitle;
	
	//成品圖圖庫位址
	@Column(name = "pictureURL",nullable=true,columnDefinition = "nvarchar(MAX)" )
	private String pictureURL;
	
	//食譜簡介
	@Column(name = "recipeIntroduction",nullable=true,columnDefinition = "nvarchar(MAX)")
	private String recipeIntroduction;
	
	//食譜份量(幾人份)
	@Column(name = "ingredientPersons",nullable=true,columnDefinition = "int")
	private Integer ingredientPersons;
	
	//製作時間
	@Column(name = "cookingTime",nullable=true,columnDefinition = "int")
	private Integer cookingTime;
	
	//難度
	@Column(name = "difficulty",nullable=true,columnDefinition = "nvarchar(50)")
	private String difficulty;
	
	//食譜建立時間
	@Column(name = "recipeCreateDate",nullable=true,columnDefinition = "datetime2")
	private LocalDateTime  recipeCreateDate;
	
	//食譜狀態
	@Column(name = "recipeStatus",nullable=true,columnDefinition = "int")
	private Integer recipeStatus;
	
	//食譜每月計數器(統計每月熱門食譜)
	@Column(name = "recipeMonthlyVisitCount",nullable=true,columnDefinition = "int")
	private Integer recipeMonthlyVisitCount;
	
	@JsonIgnore
	@OneToMany(mappedBy="recipes")
	private List<Course> course;
	
	public Recipes() {
	}

	
	public Recipes(Integer id, Member recipeAuthor, String recipeTitle, LocalDateTime recipeCreateDate,
			Integer recipeStatus, Integer recipeMonthlyVisitCount) {
		super();
		this.id = id;
		this.recipeAuthor = recipeAuthor;
		this.recipeTitle = recipeTitle;
		this.recipeCreateDate = recipeCreateDate;
		this.recipeStatus = recipeStatus;
		this.recipeMonthlyVisitCount = recipeMonthlyVisitCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getRecipeAuthor() {
		return recipeAuthor;
	}

	public void setRecipeAuthor(Member recipeAuthor) {
		this.recipeAuthor = recipeAuthor;
	}

	public List<RecipeCategory> getRecipeCategories() {
		return recipeCategories;
	}

	public void setRecipeCategories(List<RecipeCategory> recipeCategories) {
		this.recipeCategories = recipeCategories;
	}

	public List<RecipeSteps> getRecipeSteps() {
		return recipeSteps;
	}

	public void setRecipeSteps(List<RecipeSteps> recipeSteps) {
		this.recipeSteps = recipeSteps;
	}

	public List<IngredientList> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(List<IngredientList> ingredientList) {
		this.ingredientList = ingredientList;
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

	public Integer getIngredientPersons() {
		return ingredientPersons;
	}

	public void setIngredientPersons(Integer ingredientPersons) {
		this.ingredientPersons = ingredientPersons;
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

	public LocalDateTime getRecipeCreateDate() {
		return recipeCreateDate;
	}

	public void setRecipeCreateDate(LocalDateTime recipeCreateDate) {
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

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	
}

	

	