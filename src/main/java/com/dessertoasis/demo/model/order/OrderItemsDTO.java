package com.dessertoasis.demo.model.order;

import java.util.List;

import lombok.Data;

@Data
public class OrderItemsDTO {
	private List<ProdOrderItem> prodOrderItems;
	private List<CourseOrderItem> courserOrderItems;
	private List<Reservation> reservations;
}
