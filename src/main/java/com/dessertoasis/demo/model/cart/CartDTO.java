package com.dessertoasis.demo.model.cart;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CartDTO {

	private Integer categoryId;
	private Integer interestedId;
	private Integer prodQuantity;
	
	private Integer roomId;
	private LocalDate reservationDate;
	private String reservationTime;
	private String detail;
	
	public CartDTO() {
		
	}
}
