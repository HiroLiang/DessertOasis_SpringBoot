package com.dessertoasis.demo.model.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProdSearchDTO {
	private Integer id;
	private String categoryName;
	private Integer prodPrice;
	private Integer prodPurchase;
	private String prodRemark;
	private Integer prodStock;
	private String productStatus;
	private Integer saleAfterUpdate;
	private Timestamp updateTime;
	private String prodName;
	
	public ProdSearchDTO(Integer id, String categoryName, Integer prodPrice, Integer prodPurchase, String prodRemark,
			Integer prodStock, String productStatus, Integer saleAfterUpdate, Timestamp updateTime, String prodName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.prodPrice = prodPrice;
		this.prodPurchase = prodPurchase;
		this.prodRemark = prodRemark;
		this.prodStock = prodStock;
		this.productStatus = productStatus;
		this.saleAfterUpdate = saleAfterUpdate;
		this.updateTime = updateTime;
		this.prodName = prodName;
	}
	public ProdSearchDTO() {
		super();
	}


}
