package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class Address {
	
	private String country;
	private String postalCode;
	private String state;
	private String city;
	private String detail;
	private String optional;
	private Recipient recipient;
	public Address(String country, String postalCode, String state, String city, String detail, String optional,
			Recipient recipient) {
		super();
		this.country = country;
		this.postalCode = postalCode;
		this.state = state;
		this.city = city;
		this.detail = detail;
		this.optional = optional;
		this.recipient = recipient;
	}
	public Address() {
		super();
	}
	

}
