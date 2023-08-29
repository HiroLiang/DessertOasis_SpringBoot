package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.service.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService cService;

	@GetMapping("/category/find")
	public Category findCategory(@RequestParam("categoryId") Integer categoryId) {
		return cService.findCategory(categoryId);
	}
}
