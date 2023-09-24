package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class Shipping {
	
	private String methodId;
	private Integer feeAmount;
	private Address address;
	public Shipping() {
		super();
	}
	public Shipping(String methodId, Integer feeAmount, Address address) {
		super();
		this.methodId = methodId;
		this.feeAmount = feeAmount;
		this.address = address;
	}

}
