package com.dessertoasis.demo.model.cart;

import java.util.List;

import lombok.Data;

@Data
public class CartToOrderDTO {
	private List<Integer> cartIds;
	private String prodOrderAddress;
	
	public CartToOrderDTO() {
		
	}
}
