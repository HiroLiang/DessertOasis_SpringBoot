package com.dessertoasis.demo.model.order;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ord")
public class Order {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ordId;
	private Integer memberId;
	private LocalDateTime ordDate;
	private String ordState;
	private LocalDateTime stateDate;
	private Integer deliverId;
	private String recieverName;
	private String recieverPhone;
	private String recieverAddress;
	
}
