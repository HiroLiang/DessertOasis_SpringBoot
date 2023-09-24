package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class Recipient {
	private String firstName;
	private String lastName;
	private String firstNameOptional;
	private String lastNameOptional;
	private String email;
	private String phoneNo;
	public Recipient(String firstName, String lastName, String firstNameOptional, String lastNameOptional, String email,
			String phoneNo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.firstNameOptional = firstNameOptional;
		this.lastNameOptional = lastNameOptional;
		this.email = email;
		this.phoneNo = phoneNo;
	}
	public Recipient() {
		super();
	}
	

}
