package com.dessertoasis.demo.model.order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.classroom.Classroom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private LocalDate reservationDate;
	
	@Column(columnDefinition = "varchar(5)")
	private String reservationTime;
	
	@Column(columnDefinition = "nvarchar(100)")
	private String detail;
	
	@Column
	private Integer price; 
	
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Classroom classroom;
	
	@JsonIgnoreProperties({"reservations", "prodOrderItems", "courseOrderItems"})
	@ManyToOne
	@JoinColumn(name = "ordId")
	private Order order;
	
	public Reservation() {
		
	}
	
	public Reservation(ReservationCart rsv, Order order) {
		this.reservationDate = rsv.getReservationDate();
		this.reservationTime = rsv.getReservationTime();
		this.detail = rsv.getDetail();
		this.classroom = rsv.getClassroom();
		this.order = order;
		
		Map<String, Integer> priceMap = new HashMap<>();
		priceMap.put("A", this.classroom.getMorningPrice());
		priceMap.put("B", this.classroom.getAfternoonPrice());
		priceMap.put("C", this.classroom.getNightPrice());
		
		this.price = priceMap.get(this.reservationTime);
	}
}
