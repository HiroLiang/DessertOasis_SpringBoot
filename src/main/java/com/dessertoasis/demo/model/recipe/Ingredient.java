package com.dessertoasis.demo.model.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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
	
	public Ingredient() {
	}
}
