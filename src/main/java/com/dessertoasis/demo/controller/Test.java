package com.dessertoasis.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<>("Hello world", headers, HttpStatus.CREATED);
	}

}
