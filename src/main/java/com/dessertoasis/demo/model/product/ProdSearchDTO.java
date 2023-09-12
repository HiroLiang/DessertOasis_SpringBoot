package com.dessertoasis.demo.model.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProdSearchDTO {
	private Integer id;
	private String prodName;
	private String categoryName;
	private Integer prodPrice;
	private Integer prodPurchase;
	private Integer prodStock;
	private String productStatus;
	private Timestamp updateTime;
	private Integer saleAfterUpdate;
	private String prodRemark;

	public ProdSearchDTO(Integer id, String prodName, String categoryName, Integer prodPrice, Integer prodPurchase,
			Integer prodStock, String productStatus, Timestamp updateTime, Integer saleAfterUpdate, String prodRemark) {
		super();
		this.id = id;
		this.prodName = prodName;
		this.categoryName = categoryName;
		this.prodPrice = prodPrice;
		this.prodPurchase = prodPurchase;
		this.prodStock = prodStock;
		this.productStatus = productStatus;
		this.updateTime = updateTime;
		this.saleAfterUpdate = saleAfterUpdate;
		this.prodRemark = prodRemark;
	}

	public ProdSearchDTO() {
		super();
	}

}
