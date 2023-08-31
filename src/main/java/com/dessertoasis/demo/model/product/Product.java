package com.dessertoasis.demo.model.product;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.dessertoasis.demo.model.order.ProdOrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//@Data	
@Entity 
@Table(name = "product")

public class Product {

	@Id 
	@Column(name = "id", columnDefinition = "int")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "categoryId")
	private Integer categoryId;
	
	@Column(name = "prodName",columnDefinition = "nvarchar(100)")
	private String prodName;
	
	@Column(name = "prodDescription",columnDefinition = "nvarchar(max)")
	private String prodDescription;
	
	@Column(name = "prodStock")
	private Integer prodStock;
	
	@Column(name = "prodPrice", columnDefinition = "DECIMAL(10, 2)")
	private BigDecimal prodPrice;
	
	
	@Column(name = "prodPurchase")
	private Integer prodPurchase;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // 在資料進 Java 環境時，做格式化
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	private Timestamp updateTime;
	
	@Column(name = "saleAfterUpdate")
	private Integer saleAfterUpdate;
	
	
	
	@Column(name = "productStatus",columnDefinition = "nvarchar(100)")
	private String productStatus;
	
	@Column(name = "prodRemark",columnDefinition = "nvarchar(500)")
	private String prodRemark;
	
	
	 @OneToMany(mappedBy = "product")
	    private List<ProductPicture> pictures;
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "prodOrderItem")
	 private List<ProdOrderItem> prodOrderItemsList;
	
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


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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


	public Integer getProdPurchase() {
		return prodPurchase;
	}


	public void setProdPurchase(Integer prodPurchase) {
		this.prodPurchase = prodPurchase;
	}


	public Timestamp getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}


	public Integer getSaleAfterUpdate() {
		return saleAfterUpdate;
	}


	public void setSaleAfterUpdate(Integer saleAfterUpdate) {
		this.saleAfterUpdate = saleAfterUpdate;
	}


	public String getProductStatus() {
		return productStatus;
	}


	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}


	public String getProdRemark() {
		return prodRemark;
	}


	public void setProdRemark(String prodRemark) {
		this.prodRemark = prodRemark;
	}




//	
}