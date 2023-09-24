package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class ConfirmBody {
	
	private Integer amount;
	private String currency;
	public ConfirmBody(Integer amount, String currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}
	public ConfirmBody() {
		super();
	}

}
