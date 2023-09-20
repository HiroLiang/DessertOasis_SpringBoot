package com.dessertoasis.demo.service.member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dessertoasis.demo.model.member.Bank;
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
    		 Member existingAdmin2 = mRepo.findByAccount("admin2");
    		 Member existingTeacher = mRepo.findByAccount("teacher");
    		 Member existingInactive = mRepo.findByAccount("inact");
    		 Member existingBanned = mRepo.findByAccount("banned");
    		 
	    		// 如果不存在，創建一個註銷帳號
	 		 	if(existingBanned==null) {
	 		 		Member banned = new Member();
	 		 		banned.setFullName("註銷帳號");
	 		 		banned.setMemberName("註銷帳號");
	 		 		banned.setAccount("banned");
	 		 		banned.setEmail("WrHsjNJPOyBxh9eZ@google.com");
	 		 		banned.setMemberStatus(MemberState.BANNED);
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
	 		 		inactive.setMemberName("未啟用");
	 		 		inactive.setAccount("inact");
	 		 		inactive.setEmail("wa8bbWaqfqzGoGcs@google.com");
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
	 		 		teacher.setMemberName("教師");
	 		 		teacher.setAccount("teacher");
	 		 		teacher.setEmail("RluxrT0oeZPDuVPB@google.com");
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
    		 		userDetail.setIdNumber("E151940389");
    		 		userDetail.setDeliveryAddress("高雄市中正四路211號8樓之1");
    		 		
    		 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		 		Date date = dateFormat.parse("1989-06-04");
    		 		userDetail.setBirthday(date);
    		 		
    		 		Bank bank = new Bank();
    		 		bank.setBankAccount("0021057123456127");
    		 		bank.setBankCodeName("001");
    		 		bank.setBranchName("高雄分行");
    		 		
    		 		user.setFullName("一般使用者");
    		 		user.setAccount("user");
    		 		user.setMemberName("一般");
    		 		user.setEmail("1pjeLnzZ7G5wQ2gR@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		
    		 		//保持one to one關係
    		 		user.setBank(bank);
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
    	            adminDetail.setIdNumber("K254845440");
    	            adminDetail.setDeliveryAddress("高雄市中正四路211號8樓之1");
    		 		
    		 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		 		Date date = dateFormat.parse("1989-06-05");
    		 		adminDetail.setBirthday(date);
    		 		
    		 		Bank bank = new Bank();
    		 		bank.setBankAccount("0036457113456327");
    		 		bank.setBankCodeName("002");
    		 		bank.setBranchName("台中分行");
    		 		
    	            admin.setFullName("管理人員");
    	            admin.setAccount("admin");
    	            admin.setMemberName("管理");
    	            admin.setEmail("9m2XljDiDvjA75cO@google.com");
    	            admin.setMemberStatus(MemberState.ACTIVE);
    	            admin.setAccess(MemberAccess.ADMIN);
    	            String password = "admin";
    	            admin.setPasswords(passwordEncoder.encode(password));
    	            admin.setSignDate(new Date());
    	           
    	            admin.setBank(bank);
    	            admin.setMemberDetail(adminDetail);
    	            adminDetail.setMember(admin);
//    		 		
    		 		mRepo.save(admin);

    	    
    	            
    	        }if (existingAdmin2 == null) {
    	            Member admin = new Member();
    	            MemberDetail adminDetail = new MemberDetail();
    	            adminDetail.setIdNumber("N187277747");
    	            adminDetail.setDeliveryAddress("高雄市中正四路211號8樓之1");
    		 		
    		 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		 		Date date = dateFormat.parse("1988-06-05");
    		 		adminDetail.setBirthday(date);
    		 		
    	            admin.setFullName("管理人員2");
    	            admin.setAccount("admin2");
    	            admin.setMemberName("管理2");
    	            admin.setEmail("a4oIdq7nrHUZaI8K@google.com");
    	            admin.setMemberStatus(MemberState.ACTIVE);
    	            admin.setAccess(MemberAccess.ADMIN);
    	            String password = "admin2";
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
