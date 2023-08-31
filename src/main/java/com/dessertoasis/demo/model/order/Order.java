package com.dessertoasis.demo.model.order;

import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "ord")
public class Order {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Integer memberId;
	
	@Column(columnDefinition = "DateTime2")
	private LocalDateTime ordDate;
	
	@Column(columnDefinition = "nvarcahr(20)")
	private String ordStatus;
	
	@Column(columnDefinition = "DateTime2")
	private LocalDateTime updateDate;
	
	@OneToMany(mappedBy = "ord", cascade = CascadeType.ALL)
	private List<Reservation> reservationList;
	
	@OneToMany(mappedBy = "ord", cascade = CascadeType.ALL)
	private List<ProdOrderItem> prodOrderItemList;
}
