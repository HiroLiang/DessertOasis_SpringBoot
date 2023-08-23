package com.dessertoasis.demo.model.classroom;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
	private Integer roomId;
	private String roomName;
	private String roomLocation;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
	private List<Reservation> reservationList;
	
	public Classroom() {
		
	}
}
