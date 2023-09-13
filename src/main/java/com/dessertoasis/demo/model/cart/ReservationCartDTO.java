package com.dessertoasis.demo.model.cart;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.dessertoasis.demo.model.classroom.Classroom;
import com.dessertoasis.demo.model.order.Reservation;

import lombok.Data;

@Data
public class ReservationCartDTO {
	
	private Integer cartId;
	private Integer reservationCartId;
	private Classroom classroom;
	private LocalDate reservationDate;
	private String reservationTime;
	private String detail;
	private Integer price;
	
	public ReservationCartDTO() {
		
	}
	
	public ReservationCartDTO(Cart cart, ReservationCart rc) {
		this.cartId = cart.getId();
		this.reservationCartId = rc.getId();
		this.classroom = rc.getClassroom();
		this.reservationDate = rc.getReservationDate();
		this.reservationTime = rc.getReservationTime();
		this.detail = rc.getDetail();
		
		Map<String, Integer> priceMap = new HashMap<>();
		priceMap.put("A", this.classroom.getMorningPrice());
		priceMap.put("B", this.classroom.getAfternoonPrice());
		priceMap.put("C", this.classroom.getNightPrice());
		
		this.price = priceMap.get(this.reservationTime);
	}
	
	public ReservationCartDTO(Reservation rsv) {
		this.reservationCartId = rsv.getId();
		this.classroom = rsv.getClassroom();
		this.reservationDate = rsv.getReservationDate();
		this.reservationTime = rsv.getReservationTime();
		this.detail = rsv.getDetail();
		
		Map<String, Integer> priceMap = new HashMap<>();
		priceMap.put("A", this.classroom.getMorningPrice());
		priceMap.put("B", this.classroom.getAfternoonPrice());
		priceMap.put("C", this.classroom.getNightPrice());
		
		this.price = priceMap.get(this.reservationTime);
	}
}
