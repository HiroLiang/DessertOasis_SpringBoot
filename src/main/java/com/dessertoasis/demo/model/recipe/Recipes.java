package com.dessertoasis.demo.model.recipe;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@Entity @Table(name = "recipes")
public class Recipes {

	@Id @Column(name="id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//撰寫者ID(連結Member id)
	@ManyToOne
	@JoinColumn(name="memberId", nullable = false)
	private Member recipeAuthor;
	
	//分類Id  OneToMany
	@OneToMany(mappedBy = "recipe")
	private List<RecipeCategory> recipeCategories;
	
	//食譜步驟
	@OneToMany(mappedBy = "recipe")
	private List<RecipeSteps> recipeSteps;
	
	@OneToMany()
	private List<IngredientList> ingredientList;
	
	//食譜名稱
	@Column(name = "recipeTitle", nullable=false,columnDefinition = "nvarchar(100)")
	private String recipeTitle;
	
	//成品圖圖庫位址
	@Column(name = "pictureURL",nullable=false,columnDefinition = "nvarchar(MAX)" )
	private String pictureURL;
	
	//食譜簡介
	@Column(name = "recipeIntroduction",nullable=false,columnDefinition = "nvarchar(MAX)")
	private String recipeIntroduction;
	
	//製作時間
	@Column(name = "cookingTime",nullable=false,columnDefinition = "int")
	private Integer cookingTime;
	
	//難度
	@Column(name = "difficulty",nullable=true,columnDefinition = "nvarchar(50)")
	private String difficulty;
	
	//食譜建立時間
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recipeCreateDate",nullable=false,columnDefinition = "datetime2")
	private LocalDateTime  recipeCreateDate;
	
	//食譜狀態
	@Column(name = "recipeStatus",nullable=false,columnDefinition = "int")
	private Integer recipeStatus;
	
	//食譜每月計數器(統計每月熱門食譜)
	@Column(name = "recipeMonthlyVisitCount",nullable=true,columnDefinition = "int")
	private Integer recipeMonthlyVisitCount;
	
	@JsonIgnore
	@OneToMany(mappedBy="recipes")
	private List<Course> course;
	
	public Recipes() {
	}

	public Recipes(Member recipeAuthor, List<RecipeCategory> recipeCategories, List<RecipeSteps> recipeSteps,
			String recipeTitle, String pictureURL, String recipeIntroduction, Integer cookingTime,
			LocalDateTime recipeCreateDate, Integer recipeStatus) {
		super();
		this.recipeAuthor = recipeAuthor;
		this.recipeCategories = recipeCategories;
		this.recipeSteps = recipeSteps;
		this.recipeTitle = recipeTitle;
		this.pictureURL = pictureURL;
		this.recipeIntroduction = recipeIntroduction;
		this.cookingTime = cookingTime;
		this.recipeCreateDate = recipeCreateDate;
		this.recipeStatus = recipeStatus;
	}
	
	
}

	

	