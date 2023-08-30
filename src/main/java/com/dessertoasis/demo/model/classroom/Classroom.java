package com.dessertoasis.demo.model.classroom;

import java.util.List;

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
	@Column(name="id", columnDefinition = "int")
	private Integer id;
	
	@Column(name="roomName", columnDefinition ="NVARCHAR(30)")
	private String roomName;
	
	@Column(name="roomLocation", columnDefinition = "NVARCHAR(100)")
	private String roomLocation;
	
	@Column(name="maxContain", columnDefinition = "int")
	private Integer maxContain;
	
	@Column(name="morningPrice", columnDefinition="int")
	private Integer morningPrice;
	
	@Column(name="afternoonPrice", columnDefinition="int")
	private Integer afternoonPrice;
	
	@Column(name="nightPrice", columnDefinition="int")
	private Integer nightPrice;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
	private List<Reservation> reservationList;
	
	public Classroom() {
		
	}
}
