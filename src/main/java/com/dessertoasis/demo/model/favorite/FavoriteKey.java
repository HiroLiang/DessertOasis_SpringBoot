package com.dessertoasis.demo.model.favorite;

import jakarta.persistence.Embeddable;
import lombok.Data;

//三複合主鍵
@Data
@Embeddable
public class FavoriteKey {

	private Integer memberId;
	private Integer categoryId;
	private Integer itemId;
	public FavoriteKey(Integer memberId, Integer categoryId, Integer itemId) {
		super();
		this.memberId = memberId;
		this.categoryId = categoryId;
		this.itemId = itemId;
	}
	
	public FavoriteKey() {
		super();
	}

	public FavoriteKey(Integer categoryId, Integer itemId) {
		super();
		this.categoryId = categoryId;
		this.itemId = itemId;
	}	
	
}
