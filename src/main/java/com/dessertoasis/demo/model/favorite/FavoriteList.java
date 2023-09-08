package com.dessertoasis.demo.model.favorite;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.member.Member;

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

	public FavoriteList() {
		super();
	}

	public FavoriteList(FavoriteKey favoriteKey) {
		super();
		this.favoriteKey = favoriteKey;
	}

	public FavoriteList(FavoriteKey favoriteKey, Member member, Category category) {
		super();
		this.favoriteKey = favoriteKey;
		this.member = member;
		this.category = category;
	}
	
	
}
