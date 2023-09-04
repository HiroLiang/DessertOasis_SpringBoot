package com.dessertoasis.demo.model.cart;

import com.dessertoasis.demo.model.member.Member;

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
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;
	
	@Column
	private Integer categoryId;
	
	@Column
	private Integer interestedId;
	
	@Column
	private Integer prodQuantity;

	public Cart() {
		
	}
	
}
