package com.dessertoasis.demo.model.product;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ProdSearchDTO {
	private Integer Page;
    private Integer PageSize;
	private String sortBy;
    private String sortOrder;
	    private String prodName;
	    private String category;
	    //private BigDecimal prodPrice;
	    //private Integer prodPurchase;
	    //private Integer prodStock;
	    private String productStatus;
	    //private Timestamp updateTime;
	    //private Integer saleAfterUpdate;
	    private String prodRemark;
	    private BigDecimal minprodPrice;
	    private BigDecimal maxprodPrice;
	    private Integer minprodPurchase;
	    private Integer maxprodPurchase;
	    private Integer minprodStock;
	    private Integer maxprodStock;
	    private Integer minsaleAfterUpdate;
	    private Integer maxsaleAfterUpdate;
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
		public Integer getMinprodPurchase() {
			return minprodPurchase;
		}
		public void setMinprodPurchase(Integer minprodPurchase) {
			this.minprodPurchase = minprodPurchase;
		}
		public Integer getMaxprodPurchase() {
			return maxprodPurchase;
		}
		public void setMaxprodPurchase(Integer maxprodPurchase) {
			this.maxprodPurchase = maxprodPurchase;
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
		public Integer getMinsaleAfterUpdate() {
			return minsaleAfterUpdate;
		}
		public void setMinsaleAfterUpdate(Integer minsaleAfterUpdate) {
			this.minsaleAfterUpdate = minsaleAfterUpdate;
		}
		public Integer getMaxsaleAfterUpdate() {
			return maxsaleAfterUpdate;
		}
		public void setMaxsaleAfterUpdate(Integer maxsaleAfterUpdate) {
			this.maxsaleAfterUpdate = maxsaleAfterUpdate;
		}
		
		public Integer getPage() {
			return Page;
		}
		public void setPage(Integer page) {
			Page = page;
		}
		public Integer getPageSize() {
			return PageSize;
		}
		public void setPageSize(Integer pageSize) {
			PageSize = pageSize;
		}
		public String getSortBy() {
			return sortBy;
		}
		public void setSortBy(String sortBy) {
			this.sortBy = sortBy;
		}
		public String getSortOrder() {
			return sortOrder;
		}
		public void setSortOrder(String sortOrder) {
			this.sortOrder = sortOrder;
		}
		
//		public BigDecimal getProdPrice() {
//			return prodPrice;
//		}
//		public void setProdPrice(BigDecimal prodPrice) {
//			this.prodPrice = prodPrice;
//		}
//		public Integer getProdPurchase() {
//			return prodPurchase;
//		}
//		public void setProdPurchase(Integer prodPurchase) {
//			this.prodPurchase = prodPurchase;
//		}
//		public Integer getProdStock() {
//			return prodStock;
//		}
//		public void setProdStock(Integer prodStock) {
//			this.prodStock = prodStock;
//		}
		
//		public Timestamp getUpdateTime() {
//			return updateTime;
//		}
//		public void setUpdateTime(Timestamp updateTime) {
//			this.updateTime = updateTime;
//		}
//		public Integer getSaleAfterUpdate() {
//			return saleAfterUpdate;
//		}
//		public void setSaleAfterUpdate(Integer saleAfterUpdate) {
//			this.saleAfterUpdate = saleAfterUpdate;
//		}
		

	    

	  
	}



