package com.dessertoasis.demo.model.product;
import java.math.BigDecimal;

public class ProdSearchDTO {
	
	    private String prodName;
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

	    public BigDecimal getMinprodPrice() {
	        return minprodPrice;
	    }

	    public void setMinPrice(BigDecimal minprodPrice) {
	        this.minprodPrice = minprodPrice;
	    }

	    public BigDecimal getMaxprodPrice() {
	        return maxprodPrice;
	    }

	    public void setMaxPrice(BigDecimal maxprodPrice) {
	        this.maxprodPrice = maxprodPrice;
	    }

	    public Integer getMinprodStock() {
	        return minprodStock;
	    }

	    public void setMinStock(Integer minprodStock) {
	        this.minprodStock = minprodStock;
	    }

	    public Integer getMaxprodStock() {
	        return maxprodStock;
	    }

	    public void setMaxStock(Integer maxprodStock) {
	        this.maxprodStock = maxprodStock;
	    }
	}



