package com.dessertoasis.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository mRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//新增 更改成加密後的版本
//	public void insert(Member member) {
//		mRepo.save(member);
//	}
	
	 public Member findByAccount(String account) {
	        return mRepo.findByAccount(account);
	    }
	
	//查詢單筆
	public Member findByMemberId(Integer id) {
		Optional<Member> optional = mRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	
	//查詢全部
		public List<Member> findAllMember() {
			List<Member> result = mRepo.findAll();
			return result;
		}
		
		
	 // 刪除
    public void deleteByMemberId(Integer id) {
        mRepo.deleteById(id);
    }
		
    // 修改
    public Member updateMember(Member member) {
        return mRepo.save(member);
    }
    

    
    //密碼加密
    public Member addMember(Member member) {
    	System.out.println("Member object: " + member);
    	    
    	String rawPassword = member.getPasswords();
    	System.out.println("Raw password: " + rawPassword);
    	member.setPasswords(passwordEncoder.encode(member.getPasswords()));
		return mRepo.save(member);
	}
    
    //驗證是否重複帳號
    public boolean checkIfAccountExist(String account) {
		Member dbmem = mRepo.findByAccount(account);
		
		if(dbmem != null) {
			return true;
		}else {
			return false;
		}
	}
   
  //透過加密密碼，驗證登入使用者
    public Member memberLogin(String account , String password, HttpSession session) {
        Member loginAccount = mRepo.findByAccount(account);
        if (loginAccount != null) {
            if (passwordEncoder.matches(password, loginAccount.getPasswords())) {
                
            	// 登入成功，將會員物件存入 Session
                session.setAttribute("loggedInMember", loginAccount);
                return loginAccount;
            }
        }
        return null;
    }


	
	
	
	
}
