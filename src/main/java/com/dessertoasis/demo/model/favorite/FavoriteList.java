package com.dessertoasis.demo.model.favorite;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.recipe.Recipes;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity @Data
public class FavoriteList {

	@EmbeddedId
	private FavoriteKey favoriteKey;
	
	@ManyToOne
	@JoinColumn(name="memberId", referencedColumnName = "id",insertable = false,updatable = false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="categoryId", referencedColumnName = "id",insertable = false,updatable = false)
	private Category category;
	
}
