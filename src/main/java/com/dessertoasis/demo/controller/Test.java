package com.dessertoasis.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
	
	@PostMapping("/test")
	public ResponseEntity<String> test(@RequestBody Map<String, Object> reqbody){
		String name = (String) reqbody.get("name");
		System.out.println(name);
		return new ResponseEntity<>("Hello world", HttpStatus.CREATED);
	}

}
