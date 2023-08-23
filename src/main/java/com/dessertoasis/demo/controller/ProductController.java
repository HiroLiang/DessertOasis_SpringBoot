package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService pService;
	
	@Autowired
	private ProductRepository pRepo;
	
	
	
	//查詢所有課程
	@GetMapping("/product/all")
	public List<Product> findAllCourse(){
		List<Product> product = 
		return product;
	} 
	
	
}
