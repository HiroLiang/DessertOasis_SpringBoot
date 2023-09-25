package com.dessertoasis.demo.model.recipe;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "ingredients")
public class Ingredient {

	//食材編號
	@Id @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//食材名稱
	@Column(name = "ingredientName",nullable=false,columnDefinition = "nvarchar(50)")
	private String ingredientName;
	
	@OneToMany(mappedBy = "ingredient")
	@JsonIgnoreProperties(value="ingredient",allowSetters = true)
//	@JsonIgnore
	private List<IngredientList> ingredientList;
	
	public Ingredient() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public List<IngredientList> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(List<IngredientList> ingredientList) {
		this.ingredientList = ingredientList;
	}

	
}
