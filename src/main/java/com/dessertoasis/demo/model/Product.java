package com.dessertoasis.demo.model;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data	
@Entity 
@Table(name = "product")

public class Product {

	@Id 
	@Column(name = "prodID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prodID;
	
	@Column(name = "typeID")
	private int typeID;
	
	@Column(name = "prodName")
	private String prodName;
	
	@Column(name = "prodDescription")
	private String prodDescription;
	
	@Column(name = "prodStock")
	private int prodStock;
	
	@Column(name = "prodPrice")
	private int prodPrice;
	
	@Column(name = "prodPicURL")
	private String prodPicURL;
	
	@Column(name = "prodPurchase")
	private Integer prodPurchase;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prodAddtime")
	private Timestamp prodAddtime;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prodShelftime")
	private Timestamp prodShelftime;
	
	@Column(name = "prodRemark")
	private String prodRemark;

	
//	public Product() {
//	}
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
//
//	
//	
//
//
//
//	public int getProdID() {
//		return prodID;
//	}
//	public void setProdID(int prodID) {
//		this.prodID = prodID;
//	}
//	public int getTypeID() {
//		return typeID;
//	}
//	public void setTypeID(int typeID) {
//		this.typeID = typeID;
//	}
//	public String getProdName() {
//		return prodName;
//	}
//	public void setProdName(String prodName) {
//		this.prodName = prodName;
//	}
//	public String getProdDescription() {
//		return prodDescription;
//	}
//	public void setProdDescription(String prodDescription) {
//		this.prodDescription = prodDescription;
//	}
//	public int getProdStock() {
//		return prodStock;
//	}
//	public void setProdStock(int prodStock) {
//		this.prodStock = prodStock;
//	}
//	public int getProdPrice() {
//		return prodPrice;
//	}
//	public void setProdPrice(int prodPrice) {
//		this.prodPrice = prodPrice;
//	}
//	public String getProdPicURL() {
//		return prodPicURL;
//	}
//	public void setProdPicURL(String prodPicURL) {
//		this.prodPicURL = prodPicURL;
//	}
//	public Integer getProdPurchase() {
//		return prodPurchase;
//	}
//	public void setProdPurchase(Integer prodPurchase) {
//		this.prodPurchase = prodPurchase;
//	}
//	public Timestamp getProdAddtime() {
//		return prodAddtime;
//	}
//	public void setProdAddtime(Timestamp prodAddtime) {
//		this.prodAddtime = prodAddtime;
//	}
//	public Timestamp getProdShelftime() {
//		return prodShelftime;
//	}
//	public void setProdShelftime(Timestamp prodShelftime) {
//		this.prodShelftime = prodShelftime;
//	}
//	public String getProdRemark() {
//		return prodRemark;
//	}
//	public void setProdRemark(String prodRemark) {
//		this.prodRemark = prodRemark;
//	}
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//	
//	
}