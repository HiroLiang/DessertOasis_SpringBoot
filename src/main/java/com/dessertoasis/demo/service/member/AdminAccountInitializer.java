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
    		 Member existingUser2 = mRepo.findByAccount("user2");
    		 Member existingUser3 = mRepo.findByAccount("user3");
    		 Member existingUser4 = mRepo.findByAccount("user4");
    		 Member existingUser5 = mRepo.findByAccount("user5");
    		 Member existingUser6 = mRepo.findByAccount("user6");
    		 Member existingAdmin = mRepo.findByAccount("admin");
    		 Member existingAdmin2 = mRepo.findByAccount("admin2");
    		 Member existingTeacher = mRepo.findByAccount("teacher");
    		 Member existingTeacher2 = mRepo.findByAccount("teacher2");
    		 Member existingTeacher3 = mRepo.findByAccount("teacher3");
    		 Member existingTeacher4 = mRepo.findByAccount("teacher4");
    		 Member existingTeacher5 = mRepo.findByAccount("teacher5");
    		 Member existingTeacher6 = mRepo.findByAccount("teacher6");
    		 Member existingTeacher7 = mRepo.findByAccount("teacher7");
    		 Member existingTeacher8 = mRepo.findByAccount("teacher8");
    		 
    		 Member existingInactive = mRepo.findByAccount("inact");
    		 Member existingBanned = mRepo.findByAccount("banned");
    		 
	    		// 如果不存在，創建一個註銷帳號
	 		 	if(existingBanned==null) {
	 		 		Member banned = new Member();
	 		 		banned.setFullName("Charlotte Harris");
	 		 		banned.setMemberName("Charlotte Harris");
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
	 		 		inactive.setFullName("Daniel Wilson");
	 		 		inactive.setMemberName("Daniel Wilson");
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
	 		 		teacher.setFullName("Emily Johnson");
	 		 		teacher.setMemberName("Emily Johnson");
	 		 		teacher.setAccount("teacher");
	 		 		teacher.setEmail("RluxrT0oeZPDuVPB@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher2==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("Benjamin Davis");
	 		 		teacher.setMemberName("Benjamin Davis");
	 		 		teacher.setAccount("teacher2");
	 		 		teacher.setEmail("5FkvCsORXS8V1z5V@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher2";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher3==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("Olivia Mitchell");
	 		 		teacher.setMemberName("Olivia Mitchell");
	 		 		teacher.setAccount("teacher3");
	 		 		teacher.setEmail("9U2xWePMSTjYzUbb@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher3";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher4==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("Alexander Walker");
	 		 		teacher.setMemberName("Alexander Walker");
	 		 		teacher.setAccount("teacher4");
	 		 		teacher.setEmail("OT7EX55uIlHVUzzc@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher4";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher5==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("Sophia Carter");
	 		 		teacher.setMemberName("Sophia Carter");
	 		 		teacher.setAccount("teacher5");
	 		 		teacher.setEmail("jL5MioeQdqdfMHsJ@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher5";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher6==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("William Roberts");
	 		 		teacher.setMemberName("William Roberts");
	 		 		teacher.setAccount("teacher6");
	 		 		teacher.setEmail("zLQF4oB9cdCT2txs@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher6";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher7==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("Ava Turner");
	 		 		teacher.setMemberName("Ava Turner");
	 		 		teacher.setAccount("teacher7");
	 		 		teacher.setEmail("5KvIBvSblky08Kz2@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher7";
	 		 		teacher.setPasswords(passwordEncoder.encode(password));
	 		 		teacher.setSignDate(new Date());
	 		 		mRepo.save(teacher);
	 		 	}
	    		if(existingTeacher8==null) {
	 		 		Member teacher = new Member();
	 		 		teacher.setFullName("James Anderson");
	 		 		teacher.setMemberName("James Anderson");
	 		 		teacher.setAccount("teacher8");
	 		 		teacher.setEmail("9MdHv3gZqm3UPbqB@google.com");
	 		 		teacher.setMemberStatus(MemberState.ACTIVE);
	 		 		teacher.setAccess(MemberAccess.TEACHER);
	 		 		String password = "teacher8";
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
    		 		
    		 		user.setFullName("Madison Taylor");
    		 		user.setAccount("user");
    		 		user.setMemberName("Madison Taylor");
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
    		 	if(existingUser2==null) {
    		 		Member user = new Member();
    		 		user.setFullName("Olivia Smith");
    		 		user.setAccount("user2");
    		 		user.setMemberName("Olivia Smith");
    		 		user.setEmail("Vu9ojNLgEBGYAR61@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user2";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		mRepo.save(user);

    		 	}
    		 	if(existingUser3==null) {
    		 		Member user = new Member();
    		 		user.setFullName("Benjamin Johnson");
    		 		user.setAccount("user3");
    		 		user.setMemberName("Benjamin Johnson");
    		 		user.setEmail("MMskR2l7j5gasuMg@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user3";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		mRepo.save(user);

    		 	}
    		 	if(existingUser4==null) {
    		 		Member user = new Member();
    		 		user.setFullName("Emma Davis");
    		 		user.setAccount("user4");
    		 		user.setMemberName("Emma Davis");
    		 		user.setEmail("EFbX6oL0CnZ36JJR@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user4";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		mRepo.save(user);

    		 	}
    		 	if(existingUser5==null) {
    		 		Member user = new Member();
    		 		user.setFullName("William Wilson");
    		 		user.setAccount("user5");
    		 		user.setMemberName("William Wilson");
    		 		user.setEmail("BPf4yyY7WCpRAljD@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user5";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		mRepo.save(user);

    		 	}
    		 	if(existingUser6==null) {
    		 		Member user = new Member();
    		 		user.setFullName("Sophia Anderson");
    		 		user.setAccount("user6");
    		 		user.setMemberName("Sophia Anderson");
    		 		user.setEmail("4TxGvcsrHDwv1j6i@google.com");
    		 		user.setMemberStatus(MemberState.ACTIVE);
    		 		user.setAccess(MemberAccess.USER);
    		 		String password = "user6";
    		 		user.setPasswords(passwordEncoder.encode(password));
    		 		user.setSignDate(new Date());
    		 		mRepo.save(user);

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
    		 		
    	            admin.setFullName("Ethan Parker");
    	            admin.setAccount("admin");
    	            admin.setMemberName("Ethan Parker");
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
    		 		
    	            admin.setFullName("Lily Thompson");
    	            admin.setAccount("admin2");
    	            admin.setMemberName("Lily Thompson");
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
