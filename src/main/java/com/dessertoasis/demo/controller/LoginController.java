package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	
	@Autowired
    private MemberService mService;
	
	@PostMapping("/memberLogin")
	public String MemberLogin(@RequestBody Member member,HttpSession session) {
	
		Member memberLogin = mService.memberLogin(member.getAccount(), member.getPasswords(), session);
		
		
		
		if(memberLogin!=null) {
			session.setAttribute("loggedInMember", memberLogin);
			return "登入成功";	
		}
		
		
		return "登入失敗";	
	}

}
