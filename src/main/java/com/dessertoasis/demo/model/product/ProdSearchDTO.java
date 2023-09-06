package com.dessertoasis.demo.model.product;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ProdSearchDTO {
	/*
	 * {
	 * 		prodName:"",
	 * 		category
	 * 
	 * }
	 * 
	 * */
	    private String prodName;
	    private String category;
	    private BigDecimal prodPrice;
	    private Integer prodPurchase;
	    private Integer prodStock;
	    private String productStatus;
	    private Timestamp updateTime;
	    private Integer saleAfterUpdate;
	    private String prodRemark;
	    private BigDecimal minprodPrice;
	    private BigDecimal maxprodPrice;
	    private Integer minprodStock;
	    private Integer maxprodStock;
		public String getProdName() {
			return prodName;
		}
		public void setProdName(String prodName) {
			this.prodName = prodName;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
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
		public Integer getProdStock() {
			return prodStock;
		}
		public void setProdStock(Integer prodStock) {
			this.prodStock = prodStock;
		}
		public String getProductStatus() {
			return productStatus;
		}
		public void setProductStatus(String productStatus) {
			this.productStatus = productStatus;
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
		public String getProdRemark() {
			return prodRemark;
		}
		public void setProdRemark(String prodRemark) {
			this.prodRemark = prodRemark;
		}
		public BigDecimal getMinprodPrice() {
			return minprodPrice;
		}
		public void setMinprodPrice(BigDecimal minprodPrice) {
			this.minprodPrice = minprodPrice;
		}
		public BigDecimal getMaxprodPrice() {
			return maxprodPrice;
		}
		public void setMaxprodPrice(BigDecimal maxprodPrice) {
			this.maxprodPrice = maxprodPrice;
		}
		public Integer getMinprodStock() {
			return minprodStock;
		}
		public void setMinprodStock(Integer minprodStock) {
			this.minprodStock = minprodStock;
		}
		public Integer getMaxprodStock() {
			return maxprodStock;
		}
		public void setMaxprodStock(Integer maxprodStock) {
			this.maxprodStock = maxprodStock;
		}

	    

	  
	}



