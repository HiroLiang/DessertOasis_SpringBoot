package com.dessertoasis.demo.model.pay;

import java.util.List;

import lombok.Data;

@Data
public class MerchantReference {
	
	private List<AffiliateCards> affiliateCards;

	public MerchantReference(List<AffiliateCards> affiliateCards) {
		super();
		this.affiliateCards = affiliateCards;
	}

	public MerchantReference() {
		super();
	}

}
