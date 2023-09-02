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
	public String  MemberLogin(@RequestBody Member member,HttpSession session) {
	
//		 System.out.println("MemberLogin - Start");
//		    System.out.println("Received Account: " + member.getAccount());
//		    System.out.println("Received Password: " + member.getPasswords());

		    Member memberLogin = mService.memberLogin(member.getAccount(), member.getPasswords(), session);

		    if (memberLogin != null) {
		        session.setAttribute("loggedInMember", memberLogin);
//		        System.out.println("Login successful");
		        
		        return "Y";
		    }

//		    System.out.println("Login failed");
		    return "N";

	}
	

}

//↓↓↓↓↓↓↓↓↓↓↓↓↓跳轉頁面使用session取出member資料↓↓↓↓↓↓↓↓↓↓↓↓↓
//Member loggedInMember = (Member) session.getAttribute("loggedInMember");

