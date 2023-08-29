package com.dessertoasis.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
