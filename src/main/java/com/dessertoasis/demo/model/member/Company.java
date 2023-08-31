//package com.dessertoasis.demo.model.member;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//@Data
//@Entity @Table(name="company")
//public class Company {
//	
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="companyId", columnDefinition = "int")
//	private int companyId;
//	
//	@Column(name="companyName", columnDefinition = "nvarchar(50)")
//	private String  companyName;
//
//	@Column(name="companyPic", columnDefinition = "varchar(max)")
//	private String companyPic;
//
//	@Column(name="taxId", columnDefinition = "int")
//	private int taxId;
//	
//	@Column(name="companyIntro", columnDefinition = "nvarchar(max)")
//	private String companyIntro;
//	
//	public Company() {
//	}
//	
//	 @OneToOne(cascade = CascadeType.ALL)
//	 @JoinColumn(name = "memberId")
//	 private Member member;
//	
//	
//	
//
//}
