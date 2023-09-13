package com.dessertoasis.demo.model.product;
import java.io.File;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data	
@Entity 
@Table(name = "prodPicture")

public class ProductPicture {

	@Id 
	@Column(name = "id", columnDefinition = "int")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "id")
	private Product product;
	
	@Column(name = "pictureURL",columnDefinition = "nvarchar(max)")
	private String pictureURL;
	
	
	
//	public ProductPicture() {
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



//	public Integer getId() {
//		return id;
//	}
//
//
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//
//
//	public Product getProduct() {
//		return product;
//	}
//
//
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//
//
//
//	public String getPictureURL() {
//		return pictureURL;
//	}
//
//
//
//	public void setPictureURL(String pictureURL) {
//		this.pictureURL = pictureURL;
//	}


	


//	
}