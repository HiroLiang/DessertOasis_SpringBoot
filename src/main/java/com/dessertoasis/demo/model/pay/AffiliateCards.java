package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class AffiliateCards {
	private String cardType;
	private String cardId;
	public AffiliateCards() {
		super();
	}
	public AffiliateCards(String cardType, String cardId) {
		super();
		this.cardType = cardType;
		this.cardId = cardId;
	}
	

}
