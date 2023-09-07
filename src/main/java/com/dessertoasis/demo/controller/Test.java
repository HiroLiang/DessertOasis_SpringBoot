package com.dessertoasis.demo.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.ImageUploadUtil;

import jakarta.servlet.http.HttpSession;

@RestController
public class Test {
	
	@PostMapping("/test")
	public ResponseEntity<String> test(HttpSession session) throws IOException{
		session.setAttribute("name", "john");
		String name = (String) session.getAttribute("name");
		System.out.println(name);
		ImageUploadUtil util = new ImageUploadUtil();

		return new ResponseEntity<>("Hello world", HttpStatus.CREATED);
	}
	
}
