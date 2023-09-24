package com.dessertoasis.demo.model.cart;

import java.util.List;

import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductPicture;

import lombok.Data;

@Data
public class ProductCartDTO {
	
	private Integer cartId;
	private Integer productId;
	private String prodName;
	private Integer prodStock;
	private Integer prodPrice;
	private List<ProductPicture> pictures;
	private Integer prodQuantity;
	
	public ProductCartDTO() {
		
	}

	public ProductCartDTO(Integer cartId, Integer productId, String prodName, Integer prodStock, Integer prodPrice,
			List<ProductPicture> pictures, Integer prodQuantity) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.prodName = prodName;
		this.prodStock = prodStock;
		this.prodPrice = prodPrice;
		this.pictures = pictures;
		this.prodQuantity = prodQuantity;
	}
	
	public ProductCartDTO(Cart cart,Product product) {
		this.cartId = cart.getId();
		this.productId = product.getId();
		this.prodName = product.getProdName();
		this.prodStock = product.getProdStock();
		this.prodPrice = product.getProdPrice();
		this.pictures = product.getPictures();
		this.prodQuantity = cart.getProdQuantity();
	}
}
