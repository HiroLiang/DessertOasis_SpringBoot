package com.dessertoasis.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.membership.Membership;

import jakarta.servlet.http.HttpSession;

@RestController
public class MembershipController {
	
	@GetMapping("/membership/checklogin")
	public ResponseEntity<String> checkLogin(HttpSession session){
		if(session.getAttribute("loginMember") != null) {
			 Membership loginMember = (Membership) session.getAttribute("loginMember");
			 return new ResponseEntity<>(loginMember.getNickName(), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

}
