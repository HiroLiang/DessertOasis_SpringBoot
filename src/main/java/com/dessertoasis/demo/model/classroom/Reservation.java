package com.dessertoasis.demo.model.classroom;

import java.time.LocalDate;

import com.dessertoasis.demo.model.order.Order;

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
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "roomId", insertable=false, updatable=false)
	private Integer roomId;
	
	@Column(name = "ordId", insertable=false, updatable=false)
	private Integer ordId;
	
	@Column(name="reservationDate")
	private LocalDate reservationDate;
	
	@Column(name="reservationTime", columnDefinition = "varchar(5)")
	private String reservationTime;
	
	@Column(name="detail", columnDefinition = "nvarchar(100)")
	private String detail;
	
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Classroom classroom;
	
	@ManyToOne
	@JoinColumn(name = "ordId")
	private Order order;
	
	public Reservation() {
		
	}

}
