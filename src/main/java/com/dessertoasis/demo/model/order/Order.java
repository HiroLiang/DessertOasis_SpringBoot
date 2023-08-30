package com.dessertoasis.demo.model.order;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
	@Column(name="id")
	private Integer id;
	
	@Column(name="memberId")
	private Integer memberId;
	
	@Column(name="ordDate", columnDefinition = "DateTime2")
	private LocalDateTime ordDate;
	
	@Column(name="ordStatus")
	private String ordStatus;
	
	@Column(name="updateDate", columnDefinition = "DateTime2")
	private LocalDateTime updateDate;
}
