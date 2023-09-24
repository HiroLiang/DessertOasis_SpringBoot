package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class Info {
	
	private String paymentAccessToken;
	private PaymentUrl paymentUrl;
	public Info(String paymentAccessToken, PaymentUrl paymentUrl) {
		super();
		this.paymentAccessToken = paymentAccessToken;
		this.paymentUrl = paymentUrl;
	}
	public Info() {
		super();
	}

}
