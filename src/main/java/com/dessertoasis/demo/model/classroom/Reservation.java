package com.dessertoasis.demo.model.classroom;

import java.time.LocalDate;

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
	private Integer reservationId;
	
	@Column(name = "roomId", insertable=false, updatable=false)
	private Integer roomId;
	private Integer teacherId;
	private LocalDate reservationDate;
	private String reservationTime;
	private String detail;
	
	@ManyToOne()
	@JoinColumn(name = "roomID")
	private Classroom classroom;
	
	public Reservation() {
		
	}

}
