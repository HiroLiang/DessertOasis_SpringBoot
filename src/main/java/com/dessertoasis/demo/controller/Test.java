package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.category.CategoryRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class Test {
	
	@PostMapping("/test")
	public ResponseEntity<String> test(HttpSession session){
		session.setAttribute("name", "john");
		String name = (String) session.getAttribute("name");
		System.out.println(name);
		return new ResponseEntity<>("Hello world", HttpStatus.CREATED);
	}
}
