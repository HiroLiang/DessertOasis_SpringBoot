package com.dessertoasis.demo.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class Test {
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	
	@PostMapping("/test")
	public ResponseEntity<String> test(@RequestParam String password, HttpSession session) throws IOException{
		
		String encode = passwordEncoder.encode(password);
		

		return new ResponseEntity<>(encode, HttpStatus.CREATED);
	}
	
}
