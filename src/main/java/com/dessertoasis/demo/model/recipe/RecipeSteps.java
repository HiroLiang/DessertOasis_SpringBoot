package com.dessertoasis.demo.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "recipeSteps")
public class RecipeSteps {

	//食譜步驟id
//	@JsonIgnore
	@Id @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//食譜id fk
	@ManyToOne
	@JsonIgnoreProperties(value="recipeSteps", allowSetters = true)
	@JoinColumn(name = "recipeId",referencedColumnName = "id")
	private Recipes recipe;
	 
	//對應食譜的第幾步驟
	@Column(name = "stepNumber",nullable=false,columnDefinition = "int")
	private Integer stepNumber;
	
	//食譜對應步驟圖片
	@Column(name = "stepPicture",nullable=true,columnDefinition = "nvarchar(MAX)")
	private String stepPicture;
	
	//食譜對應步驟文字內容
	@Column(name = "stepContext",nullable=false,columnDefinition = "nvarchar(MAX)")
	private String stepContext;
	
	public RecipeSteps() {
	}

}
