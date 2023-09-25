package com.dessertoasis.demo.model.order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.dessertoasis.demo.model.classroom.Classroom;

import lombok.Data;

@Data
public class ReservationDTO {
	private Integer reservationId;
	private Classroom classroom;
	private LocalDate reservationDate;
	private String reservationTime;
	private String detail;
	private Integer price;
	
	public ReservationDTO() {
		
	}
	
	public ReservationDTO(Reservation rsv) {
		this.reservationId = rsv.getId();
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
