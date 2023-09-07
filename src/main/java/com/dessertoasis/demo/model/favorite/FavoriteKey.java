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
}
