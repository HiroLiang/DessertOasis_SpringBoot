package com.dessertoasis.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){

		return new ResponseEntity<>("Hello world", HttpStatus.CREATED);
	}

}
