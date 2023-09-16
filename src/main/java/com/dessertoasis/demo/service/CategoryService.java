package com.dessertoasis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository cRepo;

	public Category findCategory(@RequestParam("categoryId") Integer categoryId){
		return cRepo.findById(categoryId).get();
	}

	public Category findById(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
}