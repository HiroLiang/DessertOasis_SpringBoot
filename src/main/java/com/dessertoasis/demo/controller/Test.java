package com.dessertoasis.demo.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class Test {
	
	@PostMapping("/test")
	public ResponseEntity<String> test(HttpSession session) throws IOException{
		
		Date date = new Date();
		long time = date.getTime();
		
		

		return new ResponseEntity<>(time+"", HttpStatus.CREATED);
	}
	
}
