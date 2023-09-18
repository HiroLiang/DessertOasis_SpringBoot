package com.dessertoasis.demo.model.pay;

import java.util.List;

import lombok.Data;

@Data
public class PayBody {

	private Integer amount;
	private String currency;
	private String orderId;
	private List<Packages> packages;
	private RedirectUrls redirectUrls;
	public PayBody(Integer amount, String currency, String orderId, List<Packages> packages,
			RedirectUrls redirectUrls) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.orderId = orderId;
		this.packages = packages;
		this.redirectUrls = redirectUrls;
	}
	public PayBody() {
		super();
	}
	
}
