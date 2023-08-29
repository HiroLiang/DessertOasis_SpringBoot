package com.dessertoasis.demo.model.product;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.member.MemberState;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

//@Data	
@Entity 
@Table(name = "product")

public class Product {

	@Id 
	@Column(name = "prodID", columnDefinition = "int")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prodID;
	
	@Column(name = "prodTypeID")
	private Integer prodTypeID;
	
	@Column(name = "prodName",columnDefinition = "nvarchar(100)")
	private String prodName;
	
	@Column(name = "prodDescription",columnDefinition = "nvarchar(max)")
	private String prodDescription;
	
	@Column(name = "prodStock")
	private Integer prodStock;
	
	@Column(name = "prodPrice", columnDefinition = "DECIMAL(10, 2)")
	private BigDecimal prodPrice;
	
	@Column(name = "prodPicURL",columnDefinition = "nvarchar(max)")
	private String prodPicURL;
	
	@Column(name = "prodPurchase")
	private Integer prodPurchase;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // 在資料進 Java 環境時，做格式化
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prodAddtime")
	private Timestamp prodAddtime;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")  // 在資料進 Java 環境時，做格式化
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prodShelftime")
	private Timestamp prodShelftime;
	
	
	@Column(name = "prodRemark",columnDefinition = "nvarchar(500)")
	private String prodRemark;

	
	public Product() {
	}
//	
//	
//
//	public Product(int prodID, int typeID, String prodName, String prodDescription, int prodStock, int prodPrice,
//			String prodPicURL, Integer prodPurchase, Timestamp prodAddtime, Timestamp prodShelftime,
//			String prodRemark) {
//		super();
//		this.prodID = prodID;
//		this.typeID = typeID;
//		this.prodName = prodName;
//		this.prodDescription = prodDescription;
//		this.prodStock = prodStock;
//		this.prodPrice = prodPrice;
//		this.prodPicURL = prodPicURL;
//		this.prodPurchase = prodPurchase;
//		this.prodAddtime = prodAddtime;
//		this.prodShelftime = prodShelftime;
//		this.prodRemark = prodRemark;
//	}
//
//
//
//	public Product(int typeID, String prodName, String prodDescription, int prodStock, int prodPrice,
//			String prodPicURL,String prodRemark) {
//		super();
//		this.typeID = typeID;
//		this.prodName = prodName;
//		this.prodDescription = prodDescription;
//		this.prodStock = prodStock;
//		this.prodPrice = prodPrice;
//		this.prodPicURL = prodPicURL;
//		//this.prodAddtime = prodAddtime;
//		//this.prodShelftime = prodShelftime;
//		this.prodRemark = prodRemark;
//	}
//	
//	public Product(int typeID, String prodName, String prodDescription, int prodStock, int prodPrice,
//			String prodRemark) {
//		super();
//		this.typeID = typeID;
//		this.prodName = prodName;
//		this.prodDescription = prodDescription;
//		this.prodStock = prodStock;
//		this.prodPrice = prodPrice;
//		//this.prodPicURL = prodPicURL;
//		//this.prodAddtime = prodAddtime;
//		//this.prodShelftime = prodShelftime;
//		this.prodRemark = prodRemark;
//	}
//	
//	
//	public Product(
//			String prodPicURL) {
//		super();
//		
//		this.prodPicURL = prodPicURL;
//	
//	}




	public Integer getProdID() {
		return prodID;
	}




	public void setProdID(Integer prodID) {
		this.prodID = prodID;
	}




	public Integer getProdTypeID() {
		return prodTypeID;
	}




	public void setProdTypeID(Integer prodTypeID) {
		this.prodTypeID = prodTypeID;
	}




	public String getProdName() {
		return prodName;
	}




	public void setProdName(String prodName) {
		this.prodName = prodName;
	}




	public String getProdDescription() {
		return prodDescription;
	}




	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}




	public Integer getProdStock() {
		return prodStock;
	}




	public void setProdStock(Integer prodStock) {
		this.prodStock = prodStock;
	}




	public BigDecimal getProdPrice() {
		return prodPrice;
	}




	public void setProdPrice(BigDecimal prodPrice) {
		this.prodPrice = prodPrice;
	}




	public String getProdPicURL() {
		return prodPicURL;
	}




	public void setProdPicURL(String prodPicURL) {
		this.prodPicURL = prodPicURL;
	}




	public Integer getProdPurchase() {
		return prodPurchase;
	}




	public void setProdPurchase(Integer prodPurchase) {
		this.prodPurchase = prodPurchase;
	}




	public Timestamp getProdAddtime() {
		return prodAddtime;
	}




	public void setProdAddtime(Timestamp prodAddtime) {
		this.prodAddtime = prodAddtime;
	}




	public Timestamp getProdShelftime() {
		return prodShelftime;
	}




	public void setProdShelftime(Timestamp prodShelftime) {
		this.prodShelftime = prodShelftime;
	}




	public String getProdRemark() {
		return prodRemark;
	}




	public void setProdRemark(String prodRemark) {
		this.prodRemark = prodRemark;
	}

//	
}