package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService pService;	
	
	//查詢所有課程
	@GetMapping("/product/all")
	public List<Product> findAllCourse(){
		List<Product> product = pService.findAllProduct();
		return product;
	} 
	
	
}
