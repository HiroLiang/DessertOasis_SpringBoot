package com.dessertoasis.demo.model.order;

import com.dessertoasis.demo.model.product.Product;
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
@Table(name = "prodOrderItem")
public class ProdOrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonIgnoreProperties({"reservationList", "prodOrderItemList", "courseOrderItemList"})
	@ManyToOne
	@JoinColumn(name = "ordId")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "prodId")
	private Product product;
	
	@Column
	private Integer price;
	
	@Column
	private Integer quantity; 
}
