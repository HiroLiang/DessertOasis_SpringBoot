package com.dessertoasis.demo.model.pay;

import java.util.List;

import lombok.Data;
@Data
public class Packages {
	
	private String id;
	private Integer amount;
	private Integer userFee;
	private String name;
	private List<Products> products;
	public Packages(String id, Integer amount, Integer userFee, String name, List<Products> products) {
		super();
		this.id = id;
		this.amount = amount;
		this.userFee = userFee;
		this.name = name;
		this.products = products;
	}
	public Packages() {
		super();
	}
	
}
