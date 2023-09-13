package com.dessertoasis.demo.controller.member;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberState;
import com.dessertoasis.demo.service.member.MemberService;
import com.dessertoasis.demo.service.member.TokenEncryptionUtil;


@RestController
public class RegisterController {
	
	
	
    @Autowired
    private MemberService mService;

    @PostMapping("/memberRegister")
    public String register(@RequestBody Member member){
    	
    	// 驗證註冊資訊，檢查是否有相同的帳號
        if (mService.checkIfAccountExist(member.getAccount())) {
            return "N";
        }
       
        // 創建會員並加密密碼
        Member newMember = new Member();
        newMember.setAccount(member.getAccount());
        newMember.setEmail(member.getEmail());
        newMember.setPasswords(member.getPasswords());
        
////        創立新帳，產生驗證token
//        String verificationToken = TokenEncryptionUtil.setEncryptedToken(member.getAccount(), member.getEmail(), secretKey);
//        newMember.setVerificationToken(verificationToken);
////        
////        //產生此會員的驗證信連結
//        String verificationLink ="http://localhost:5173/#/"+verificationToken;
//        mService.sendVerificationEmail(newMember.getEmail(),verificationLink);
//        
        
        //創立新帳號為不活耀狀態
        member.getMemberStatus();
		newMember.setMemberStatus(MemberState.INACTIVE); 
		
		//創立新帳號為一般會員
		member.getAccess();
		newMember.setAccess(MemberAccess.USER);
		
		//創立帳號日期是系統當下時間
        newMember.setSignDate(new Date());
        
        newMember = mService.addMember(newMember);
        
       
        return "Y";
    }
}
