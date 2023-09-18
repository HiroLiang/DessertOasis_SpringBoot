package com.dessertoasis.demo.model.pay;

import lombok.Data;

@Data
public class Products {

	private String id;
	private String name;
	private String imageUrl;
	private Integer quantity;
	private Integer price;
	private Integer originalPrice;
	public Products(String id, String name, String imageUrl, Integer quantity, Integer price, Integer originalPrice) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.quantity = quantity;
		this.price = price;
		this.originalPrice = originalPrice;
	}
	public Products() {
		super();
	}
	
}
