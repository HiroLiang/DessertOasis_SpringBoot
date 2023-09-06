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
import com.dessertoasis.demo.model.member.MemberState;
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
		    	MemberState state = memberLogin.getMemberStatus();
		    	MemberAccess access = memberLogin.getAccess();
		    	
		    		    	
				    	//登入後判斷會員權限設定不同cookies，
				    	// isLogin=1，管理員
				    	// isLogin=2，一般使用者
				    	// isLogin=3，老師
		    	 if (access == MemberAccess.ADMIN ) {
		             Cookie cookie = new Cookie("isLogin", "10");
		             cookie.setMaxAge(3600); //cookie存1小時
		             cookie.setPath("/");
		             response.addCookie(cookie);
		             
		             
		         } else if (access == MemberAccess.USER && state ==MemberState.ACTIVE) {
		             Cookie cookie = new Cookie("isLogin", "20");
		             cookie.setMaxAge(3600); 
		             cookie.setPath("/");
		             response.addCookie(cookie);
		             
		             if(state == MemberState.INACTIVE) {
		            	 Cookie cookieInact = new Cookie("isLogin", "21");
		            	 cookieInact.setMaxAge(3600); 
		            	 cookieInact.setPath("/");
			             response.addCookie(cookieInact);
			             
		             }else if(state == MemberState.BANDED) {
		            	 Cookie cookieBan = new Cookie("isLogin", "22");
		            	 cookieBan.setMaxAge(3600); 
		            	 cookieBan.setPath("/");
			             response.addCookie(cookieBan);
		             }
		             
		         } else if (access == MemberAccess.TEACHER) {
		        	 Cookie cookie = new Cookie("isLogin", "30");
		             cookie.setMaxAge(3600); 
		             cookie.setPath("/");
		             response.addCookie(cookie);
		             
		         } 
		    	 
		        return "Y";
		    }
		    	return "N";

	}
	
	//登出
	@RequestMapping("/memberLogout")
	public String logout(HttpSession session, HttpServletResponse response) {
		
		Cookie cookie = new Cookie("isLogin", null); //刪除cookies
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
	    session.invalidate(); // 刪除session
	    
	    return "redirect:/"; // 
	}

//	@GetMapping("/getcookie")
//	public String getCookieValue(@CookieValue(name = "adminLogin", defaultValue = "") String adminLoginCookie) {
//		if("1".equals(adminLoginCookie)) {
//			return "已登入管理員";
//			
//		}else {
//			return "未登入管理員";
//		}
		
		
		
	}
	
	
	
	




//↓↓↓↓↓↓↓↓↓↓↓↓↓跳轉頁面使用session取出member資料↓↓↓↓↓↓↓↓↓↓↓↓↓
//Member loggedInMember = (Member) session.getAttribute("loggedInMember");

