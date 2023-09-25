package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class PaymentUrl {
	
	private String app;
	private String web;
	public PaymentUrl(String app, String web) {
		super();
		this.app = app;
		this.web = web;
	}
	public PaymentUrl() {
		super();
	}

}
