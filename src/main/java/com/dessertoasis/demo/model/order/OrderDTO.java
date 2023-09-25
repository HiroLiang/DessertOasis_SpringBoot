package com.dessertoasis.demo.model.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dessertoasis.demo.model.member.Member;

import lombok.Data;

@Data
public class OrderDTO {
	
	private Integer ordId;
	private Member member;
	private LocalDateTime ordDate;
	private String ordStatus;
	private LocalDateTime updateDate;
	private String prodOrderAddress;
	private List<ReservationDTO> reservations;
	private List<ProdOrderItemDTO> prodOrderItems;
	private List<CourseOrderItemDTO> courseOrderItems;
	private Integer total;
	
	public OrderDTO() {
		
	}
	
	public OrderDTO(Order order) {
		this.ordId = order.getId();
		this.member = order.getMember();
		this.ordDate = order.getOrdDate();
		this.ordStatus = order.getOrdStatus();
		this.updateDate = order.getUpdateDate();
		this.prodOrderAddress = order.getProdOrderAddress();
		
		Integer total = 0;
		if (order.getProdOrderItems() != null) {
			List<ProdOrderItemDTO> prodOrderItems	= new ArrayList<>();
			for (ProdOrderItem prodOrderItem : order.getProdOrderItems()) {
				ProdOrderItemDTO itemDto = new ProdOrderItemDTO(prodOrderItem);
				prodOrderItems.add(itemDto);
				total += itemDto.getProdPrice() * itemDto.getProdQuantity();
			}
			this.prodOrderItems = prodOrderItems;
		}
		
		if (order.getCourseOrderItems() != null) {
			List<CourseOrderItemDTO> courseOrderItems = new ArrayList<>();
			for (CourseOrderItem courseOrderItem : order.getCourseOrderItems()) {
				CourseOrderItemDTO itemDto = new CourseOrderItemDTO(courseOrderItem);
				courseOrderItems.add(itemDto);
				total += itemDto.getCoursePrice();
			}
			this.courseOrderItems = courseOrderItems;
		}
		
		if (order.getReservations() != null) {
			List<ReservationDTO> reservations = new ArrayList<>();
			for (Reservation rsv : order.getReservations()) {
				ReservationDTO itemDto = new ReservationDTO(rsv);
				reservations.add(itemDto);
				total += itemDto.getPrice();
			}
			this.reservations = reservations;
		}
		this.total = total;
	}
}
