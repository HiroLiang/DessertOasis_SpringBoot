package com.dessertoasis.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberDetail;
import com.dessertoasis.demo.model.member.MemberDetailRepository;
import com.dessertoasis.demo.model.member.MemberRepository;
import com.dessertoasis.demo.model.member.MemberState;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;


@Component
public class AdminAccountInitializer {

    @Autowired
    private MemberRepository mRepo;
    
    @Autowired
    private MemberDetailRepository mdRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.initialization.enabled}") // 從設定檔中讀取開關
    private boolean adminInitializationEnabled;

    
    //spring boot的初始帳號
    // user: 一般使用者
    // admin: 管理者
    // teacher: 教師
    // inactive: 不活躍

    @Transactional
    @PostConstruct
    public void initializeAdminAccount() throws ParseException  {
       
    	try {
    		 Member existingUser = mRepo.findByAccount("user");
    		 Member existingAdmin = mRepo.findByAccount("admin");
    		 Member existingTeacher = mRepo.findByAccount("teacher");
    		 Member existingInactive = mRepo.findByAccount("inact");
    		 Member existingBanned = mRepo.findByAccount("banned");
    		 
	    		// 如果不存在，創建一個註銷帳號
	 		 	if(existingBanned==null) {
	 		 		Member banned = new Member();
	 		 		banned.setFullName("註銷帳號");
	 		 		banned.setAccount("banned");
	 		 		banned.setMemberStatus(MemberState.BANDED);
	 		 		banned.setAccess(MemberAccess.USER);
	 		 		String password = "banned";
	 		 		banned.setPasswords(passwordEncoder.encode(password));
	 		 		banned.setSignDate(new Date());
	 		 		mRepo.save(banned);
	 		 	}
    		 
    		 	// 如果不存在，創建一個不活耀帳戶帳號
    		 	if(existingInactive==null) {
	 		 		Member inactive = new Member();
	 		 		inactive.setFullName("未啟用帳號");
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
    		 		MemberDetail userDetail = new MemberDetail();
    		 		userDetail.setIdNumber("A123456789");
    		 		userDetail.setDeliveryAddress("台灣省");
    		 		
    		 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		 		Date date = dateFormat.parse("1989-06-04");
    		 		userDetail.setBirthday(date);
    		 		
    		 		
    		 		user.setFullName("一般使用者");
    		 		user.setAccount("user");
    		 		user.setMemberName("一般");
    		 		user.setEmail("123@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		
    		 		//保持one to one關係
    		 		user.setMemberDetail(userDetail);
    		 	    userDetail.setMember(user);
//    		 		
    		 		mRepo.save(user);
//    		 		mdRepo.save(userDetail);
    		 	}
    	       
    		 	// 如果不存在，創建一個管理者帳號
    	        if (existingAdmin == null) {
    	            Member admin = new Member();
    	            MemberDetail adminDetail = new MemberDetail();
    	            adminDetail.setIdNumber("B123132132");
    	            adminDetail.setDeliveryAddress("北京");
    		 		
    		 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		 		Date date = dateFormat.parse("1989-06-05");
    		 		adminDetail.setBirthday(date);
    		 		
    	            admin.setFullName("管理人員");
    	            admin.setAccount("admin");
    	            admin.setMemberName("管理");
    	            admin.setEmail("3345678@google.com");
    	            admin.setMemberStatus(MemberState.ACTIVE);
    	            admin.setAccess(MemberAccess.ADMIN);
    	            String password = "admin";
    	            admin.setPasswords(passwordEncoder.encode(password));
    	            admin.setSignDate(new Date());

    	            admin.setMemberDetail(adminDetail);
    	            adminDetail.setMember(admin);
//    		 		
    		 		mRepo.save(admin);

    	    
    	            
    	        }else {
    	        	         
    	        }
    	        adminInitializationEnabled = false;
    	}catch (LazyInitializationException e) {
    		
			
		}
    	       
    }
}
