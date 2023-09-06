package com.dessertoasis.demo.model.cart;

import java.time.LocalDate;

import com.dessertoasis.demo.model.classroom.Classroom;

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
@Table(name = "reservationCart")
public class ReservationCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Classroom classroom;
	
	@Column
	private LocalDate reservationDate;
	
	@Column(columnDefinition = "varchar(5)")
	private String reservationTime;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String detail;
	
	public ReservationCart() {
		
	}
	
	public ReservationCart(CartDTO cartDTO, Classroom room) {
		this.classroom = room;
		this.reservationDate = cartDTO.getReservationDate();
		this.reservationTime = cartDTO.getReservationTime();
		this.detail = cartDTO.getDetail();
	}

}
