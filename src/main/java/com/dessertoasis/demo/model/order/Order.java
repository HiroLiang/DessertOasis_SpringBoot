package com.dessertoasis.demo.model.order;

import java.time.LocalDateTime;
import java.util.List;

import com.dessertoasis.demo.model.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ord")
public class Order {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;
	
	@Column(columnDefinition = "DateTime2")
	private LocalDateTime ordDate;
	
	@Column(columnDefinition = "nvarchar(20)")
	private String ordStatus;
	
	@Column(columnDefinition = "DateTime2")
	private LocalDateTime updateDate;
	
	@Column(columnDefinition = "nvarchar(max)")
	private String prodOrderAddress;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<ProdOrderItem> prodOrderItems;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<CourseOrderItem> courseOrderItems;
}
