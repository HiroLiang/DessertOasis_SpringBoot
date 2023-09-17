package com.dessertoasis.demo.controller.member;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.RegisterDto;
import com.dessertoasis.demo.service.member.MemberService;


@RestController
public class RegisterController {
	
	
	
    @Autowired
    private MemberService mService;
    @Autowired
    private SecretKey secretKey;
    
    //註冊
    @PostMapping("/memberRegister")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
      return new ResponseEntity<>(mService.register(registerDto), HttpStatus.OK);
    }
    //手動送出驗證
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
      return new ResponseEntity<>(mService.regenerateOtp(email), HttpStatus.OK);
    }
    
   
//    @PostMapping("/memberRegister")
//    public String register(@RequestBody Member member){
//    	
//    	// 驗證註冊資訊，檢查是否有相同的帳號
//        if (mService.checkIfAccountExist(member.getAccount())) {
//            return "N";
//        }
//       
//        // 創建會員並加密密碼
//        Member newMember = new Member();
//        newMember.setAccount(member.getAccount());
//        newMember.setEmail(member.getEmail());
//        newMember.setPasswords(member.getPasswords());
//        
////        創立新帳，產生驗證token
//		try {
//			String verificationToken = TokenEncryptionUtil.setEncryptedToken(member.getAccount(), secretKey);
//			newMember.setOtp(verificationToken);
////        
//			//產生此會員的驗證信連結
//			String verificationLink ="http://localhost:5173/#/verify?token="+verificationToken;
//			mService.sendVerificationEmail(newMember.getEmail(),verificationLink);
//			System.out.println("我是verificationToken:"+verificationToken);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        
//        //創立新帳號為不活耀狀態
//        member.getMemberStatus();
//		newMember.setMemberStatus(MemberState.INACTIVE); 
//		
//		//創立新帳號為一般會員
//		member.getAccess();
//		newMember.setAccess(MemberAccess.USER);
//		
//		//創立帳號日期是系統當下時間
//        newMember.setSignDate(new Date());
//        
//        newMember = mService.addMember(newMember);
//        
//       
//        return "Y";
//    }
    
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyToken(@RequestBody String token) {
//    	try {
//			
//    		String decryptedToken = TokenEncryptionUtil.decryptToken(token, secretKey);
//    		String[] parts = decryptedToken.split("-"); // 假設用 "-" 分隔帳戶和電子郵件
//            if (parts.length != 2) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("令牌格式無效");
//            }
//            
//            String account = parts[0];
//            if(mService.isValidAccount(account)) {
//            	return ResponseEntity.ok("驗證成功");
//            }else {
//            	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("驗證失敗");
//            }
//            
//    	} catch (Exception e) {
//			e.printStackTrace();
//			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("連線失敗");
//    	}
//    	
//    }
      
}
