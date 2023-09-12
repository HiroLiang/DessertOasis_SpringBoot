package com.dessertoasis.demo.model.cart;

import java.util.List;

import lombok.Data;

@Data
public class CartListDTO {
	private List<ProductCartDTO> productCartDTOs;
	private List<CourseCartDTO> courseCartDTOs;
	private List<ReservationCartDTO> reservationCartDTOs;
	private String prodOrderAddress;
	
	public CartListDTO() {
		
	}
}
