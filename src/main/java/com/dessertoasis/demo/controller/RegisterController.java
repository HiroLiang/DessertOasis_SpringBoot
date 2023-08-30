package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberState;
import com.dessertoasis.demo.service.MemberService;

@RestController
public class RegisterController {

    @Autowired
    private MemberService mService;

    @PostMapping("/memberRegister")
    public String register(@RequestBody Member member) {
    	
    	// 驗證註冊資訊，檢查是否有相同的帳號
        if (mService.checkIfAccountExist(member.getAccount())) {
            return "帳號已存在";
        }
        
        // 創建會員並加密密碼
        Member newMember = new Member();
        newMember.setAccount(member.getAccount());
        newMember.setEmail(member.getEmail());
        newMember.setPasswords(member.getPasswords());
        
        member.getMemberStatus();//創立新帳號為不活耀狀態
		newMember.setMemberStatus(MemberState.INACTIVE); 
		
        newMember = mService.addMember(newMember);
        
       
        return "註冊成功";
    }
}
