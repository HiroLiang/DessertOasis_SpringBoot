package com.dessertoasis.demo.service;

import java.util.Date;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.member.MemberState;

import jakarta.annotation.PostConstruct;


@Component
public class AdminAccountInitializer {

    @Autowired
    private MemberRepository mRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.initialization.enabled}") // 從設定檔中讀取開關
    private boolean adminInitializationEnabled;

    
    //spring boot的初始帳號
    // user: 一般使用者
    // admin: 管理者
    // teacher: 教師
    // inactive: 不活躍帳戶
    
    @PostConstruct
    public void initializeAdminAccount()  {
       
    	try {
    		 Member existingUser = mRepo.findByAccount("user");
    		 Member existingAdmin = mRepo.findByAccount("admin");
    		 Member existingTeacher = mRepo.findByAccount("teacher");
    		 Member existingInactive = mRepo.findByAccount("inact");
    		 
    		 	// 如果不存在，創建一個不活耀帳戶帳號
    		 	if(existingInactive==null) {
	 		 		Member inactive = new Member();
	 		 		inactive.setFullName("不活耀帳戶");
	 		 		inactive.setAccount("inact");
	 		 		inactive.setMemberStatus(MemberState.INACTIVE);
	 		 		inactive.setAccess(MemberAccess.USER);
	 		 		String password = "inact";
	 		 		inactive.setPasswords(passwordEncoder.encode(password));
	 		 		inactive.setSignDate(new Date());
	 		 		mRepo.save(inactive);
	 		 	}
    		 
    		 	// 如果不存在，創建一個教師帳號
	    		if(existingTeacher==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("教師");
	 		 		teacher.setAccount("teacher");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
    		 
	    		// 如果不存在，創建一個一般使用者帳號
    		 	if(existingUser==null) {
    		 		Member user = new Member();
    		 		user.setFullName("一般使用者");
    		 		user.setAccount("user");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		mRepo.save(user);
    		 	}
    	       
    		 	// 如果不存在，創建一個管理者帳號
    	        if (existingAdmin == null) {
    	            Member admin = new Member();
    	            admin.setFullName("管理人員");
    	            admin.setAccount("admin");
    	            admin.setMemberStatus(MemberState.ACTIVE);
    	            admin.setAccess(MemberAccess.ADMIN);
    	            String password = "admin";
    	            admin.setPasswords(passwordEncoder.encode(password));
    	            admin.setSignDate(new Date());

    	            mRepo.save(admin);

    	    
    	            adminInitializationEnabled = false;
    	            
    	        }else {
    	        	         
    	        }
    	}catch (LazyInitializationException e) {
    		
			
		}
    	       
    }
}
