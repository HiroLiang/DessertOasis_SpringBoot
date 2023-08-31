package com.dessertoasis.demo.model.classroom;

import java.util.List;

import com.dessertoasis.demo.model.cart.ReservationCart;
import com.dessertoasis.demo.model.order.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="classroom")
public class Classroom {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "int")
	private Integer id;
	
	@Column(columnDefinition ="NVARCHAR(30)")
	private String roomName;
	
	@Column(columnDefinition = "NVARCHAR(100)")
	private String roomLocation;
	
	@Column
	private Integer maxContain;
	
	@Column
	private Integer morningPrice;
	
	@Column
	private Integer afternoonPrice;
	
	@Column
	private Integer nightPrice;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
	private List<Reservation> reservationList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
	private List<ReservationCart> reservationCartList;
	
	public Classroom() {
		
	}
}
