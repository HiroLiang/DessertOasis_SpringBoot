package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class PayInfo {
	
	private String method;
	private Integer amount;
	private String creditCardNickname;
	private String creditCardBrand;
	private String maskedCreditCardNumber;
	public PayInfo() {
		super();
	}
	public PayInfo(String method, Integer amount, String creditCardNickname, String creditCardBrand,
			String maskedCreditCardNumber) {
		super();
		this.method = method;
		this.amount = amount;
		this.creditCardNickname = creditCardNickname;
		this.creditCardBrand = creditCardBrand;
		this.maskedCreditCardNumber = maskedCreditCardNumber;
	}

}
