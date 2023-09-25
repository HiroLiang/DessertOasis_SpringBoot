package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class ConfirmPackages {
	
	private String id;
	private Integer amount;
	private Integer userFeeAmount;
	public ConfirmPackages(String id, Integer amount, Integer userFeeAmount) {
		super();
		this.id = id;
		this.amount = amount;
		this.userFeeAmount = userFeeAmount;
	}
	public ConfirmPackages() {
		super();
	}
}
