package com.dessertoasis.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){

		return new ResponseEntity<>("Hello world", HttpStatus.CREATED);
	}

}
