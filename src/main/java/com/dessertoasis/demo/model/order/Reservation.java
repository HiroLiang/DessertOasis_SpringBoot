package com.dessertoasis.demo.model.order;

import java.time.LocalDate;

import com.dessertoasis.demo.model.cart.ReservationCartDTO;
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
	
	public Reservation(ReservationCartDTO rsvDTO, Order order) {
		this.reservationDate = rsvDTO.getReservationDate();
		this.reservationTime = rsvDTO.getReservationTime();
		this.detail = rsvDTO.getDetail();
		this.price = rsvDTO.getPrice();
		this.classroom = rsvDTO.getClassroom();
		this.order = order;
	}
}
