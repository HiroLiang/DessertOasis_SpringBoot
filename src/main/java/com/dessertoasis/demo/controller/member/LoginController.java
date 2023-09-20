package com.dessertoasis.demo.controller.member;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberState;
import com.dessertoasis.demo.model.record.Record;
import com.dessertoasis.demo.service.member.CookieEncryptionUtil;
import com.dessertoasis.demo.service.member.MemberService;
import com.dessertoasis.demo.service.record.RecordService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	
	@Autowired
	private RecordService rService;
	
	@Autowired
    private MemberService mService;
	@Autowired
	private SecretKey secretKey;
	
		//拿到密鑰
		@GetMapping("/getSecretKey")
	    public ResponseEntity<String> getSecretKey() {
	        // 密鑰轉為字串
	        String keyBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
	        return ResponseEntity.ok(keyBase64);
	    }
		
		@GetMapping("/member/loggedInUserId")
		public Integer getLoggedInUserId(HttpSession session) {
			Member user = (Member) session.getAttribute("loggedInMember");
			if(user != null)
				return user.getId();
			return 0;
			
		}
	
	
		@PostMapping("/memberLogin")
		public String  MemberLogin(@RequestBody Member member,HttpSession session,HttpServletResponse response) throws Exception {

		
		
			//輸入資料匹配資料庫=>登入成功回傳 Y; 登入失敗回傳 N
		    Member memberLogin = mService.memberLogin(member.getAccount(), member.getPasswords(), session);

		    if (memberLogin != null) {
		    	
		    	session.setAttribute("loggedInMember", memberLogin);
		    	MemberState state = memberLogin.getMemberStatus();
		    	MemberAccess access = memberLogin.getAccess();
		    	if(state ==  MemberState.BANNED) {
		    		return "BANNED";
		    	}
		    	if(state ==  MemberState.INACTIVE) {
		    		return "INACTIVE";
		    	}
		    	else if (access == MemberAccess.ADMIN ) {//登入後判斷會員權限設定不同cookies，
		    		
		    		 //透過setEncryptedCookie來做加密
		    		 String cookieName = "isLogin";
		    		 String cookieValue = "admin";
		    		 CookieEncryptionUtil.setEncryptedCookie(response, cookieName, cookieValue, secretKey);
		    		 
//		             Cookie cookie = new Cookie("isLogin", "10");
//		             cookie.setMaxAge(3600); //cookie存1小時
//		             cookie.setPath("/");
//		             response.addCookie(cookie);
		             
		             
		         } else if (access == MemberAccess.USER) {
		        	 String cookieName = "isLogin";
		    		 String cookieValue = "user";
		    		 CookieEncryptionUtil.setEncryptedCookie(response, cookieName, cookieValue, secretKey);
		         } else if (access == MemberAccess.TEACHER) {
		        	 String cookieName = "isLogin";
		    		 String cookieValue = "teacher";
		    		 CookieEncryptionUtil.setEncryptedCookie(response, cookieName, cookieValue, secretKey);
		         } 
		    	 
		        return "Y";
		    }
		    	return "N";

	}
		
		 //取得權限
		 @GetMapping("/checkUserPermission")
		    public String checkUserPermission(HttpServletRequest request,HttpSession session) {
			 if(session.getAttribute("visitRecord")==null) {
				 Record record = new Record(0,0,new Date());
				 Record setRecord = rService.setRecord(record);
				 session.setAttribute("visitRecord", setRecord);
			 }
			 
		        // 從請求中獲取Cookie
		        Cookie cookie = WebUtils.getCookie(request, "isLogin");

		        if (cookie != null) {
		            // 使用解密方法解密Cookie值
		            String decryptedCookieValue = null;
		            try {
		                decryptedCookieValue = CookieEncryptionUtil.getDecryptedCookieValue(cookie, secretKey);
		            } catch (Exception e) {
		                
		            }

		            // 執行條件判斷
		            if ("admin".equals(decryptedCookieValue)) {
		                // 用戶是管理員
		                return "User is an admin";
		            } else if ("user".equals(decryptedCookieValue)) {
		                // 用戶是一般用戶
		                return "User is a regular user";
		            } else if ("teacher".equals(decryptedCookieValue)) {
		                // 用戶是老師
		                return "User is a teacher";
		            }
		        }

		        // 如果Cookie不存在或解密失敗，可以返回默認值或執行其他適當的處理邏輯
		        return "User's permission is unknown";
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
	
	//從session 拿出member資料
	  @PostMapping("/memberSession")
	    public Member getSessionMember(HttpServletRequest request) {
	        
	        HttpSession session = request.getSession();
	        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
	        
	        return loggedInMember;
	    }
	  
	  //忘記密碼
	  @PutMapping("/forgotpassword")
	  public ResponseEntity<String> forgotPassword(@RequestParam String email) {
	    return new ResponseEntity<>(mService.forgotPassword(email), HttpStatus.OK);
	  }
	  
	  //重設密碼
	  @PutMapping("/setpassword")
	  public ResponseEntity<String> setpassword(@RequestParam String email,@RequestHeader String newpassword) {
		    return new ResponseEntity<>(mService.setPassword(email,newpassword), HttpStatus.OK);
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

