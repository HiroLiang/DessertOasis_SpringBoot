package com.dessertoasis.demo.model.order;

import java.util.List;

import com.dessertoasis.demo.model.product.ProductPicture;

import lombok.Data;

@Data
public class ProdOrderItemDTO {
	private Integer prodOrderItemId;
	private String prodName;
	private Integer prodQuantity;
	private Integer prodPrice;
	private List<ProductPicture> pictures;

	public ProdOrderItemDTO() {
		
	}
	
	public ProdOrderItemDTO(ProdOrderItem prodOrderItem) {
		this.prodOrderItemId = prodOrderItem.getId();
		this.prodName = prodOrderItem.getProduct().getProdName();
		this.prodQuantity = prodOrderItem.getQuantity();
		this.prodPrice = prodOrderItem.getPrice();
		this.pictures = prodOrderItem.getProduct().getPictures();
	}
}
