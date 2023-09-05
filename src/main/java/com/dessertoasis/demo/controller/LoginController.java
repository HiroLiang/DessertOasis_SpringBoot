package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	
	@Autowired
    private MemberService mService;
	
	@PostMapping("/memberLogin")
	public String  MemberLogin(@RequestBody Member member,HttpSession session,HttpServletResponse response) {

		
		//輸入資料匹配資料庫=>登入成功回傳 Y; 登入失敗回傳 N
		    Member memberLogin = mService.memberLogin(member.getAccount(), member.getPasswords(), session);

		    if (memberLogin != null) {
		    	session.setAttribute("loggedInMember", memberLogin);
		    	MemberAccess access = memberLogin.getAccess();
		    	
		    	
		    	 if (access == MemberAccess.ADMI) {
		             Cookie cookie = new Cookie("adminLogin", "1");
		             cookie.setMaxAge(36000); // 
		             cookie.setPath("/"); 
		             cookie.setHttpOnly(false);
		             cookie.setSecure(false);
		             cookie.setDomain("localhost");
		             response.addCookie(cookie);
		             
		         } else if (access == MemberAccess.NORMAL) {
		             Cookie cookie = new Cookie("userLogin", "1");
		             cookie.setMaxAge(36000); 
		             cookie.setPath("/"); 
		             cookie.setHttpOnly(false);
		             cookie.setSecure(false);
		             cookie.setDomain("localhost");
		             response.addCookie(cookie);
		         }
		    	 
		        return "Y";
		    }
		    	return "N";

	}
	
	//登出
	@RequestMapping("/memberLogout")
	public String logout(HttpSession session) {
	    session.invalidate(); // 
	    return "redirect:/"; // 
	}

	@GetMapping("/getcookie")
	public String getCookieValue(@CookieValue(name = "adminLogin", defaultValue = "") String adminLoginCookie) {
		if("1".equals(adminLoginCookie)) {
			return "已登入管理員";
			
		}else {
			return "未登入管理員";
		}
		
		
		
	}
	
	
	
	
}



//↓↓↓↓↓↓↓↓↓↓↓↓↓跳轉頁面使用session取出member資料↓↓↓↓↓↓↓↓↓↓↓↓↓
//Member loggedInMember = (Member) session.getAttribute("loggedInMember");

