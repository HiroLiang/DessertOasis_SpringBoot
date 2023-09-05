//package com.dessertoasis.demo.service;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.dessertoasis.demo.model.member.Member;
//import com.dessertoasis.demo.model.member.MemberAccess;
//import com.dessertoasis.demo.model.member.MemberRepository;
//import com.dessertoasis.demo.model.member.MemberState;
//
//import jakarta.annotation.PostConstruct;
//
//
//@Component
//public class AdminAccountInitializer {
//
//    @Autowired
//    private MemberRepository mRepo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//   
//
//    @Value("${admin.initialization.enabled}") // 從設定檔中讀取開關
//    private boolean adminInitializationEnabled;
//
//    @PostConstruct
//    public void initializeAdminAccount() {
//        // 檢查是否已存在管理者帳號
//        Member existingAdmin = mRepo.findByAccount("admin");
//
//        if (existingAdmin == null) {
//            // 如果不存在，創建一個管理者帳號
//            Member admin = new Member();
//            admin.setFullName("管理人員");
//            admin.setAccount("test");
//            admin.setMemberStatus(MemberState.ACTIVE);
//            admin.setAccess(MemberAccess.ADMI);
//            String plainPassword = "test";
//            admin.setPasswords(passwordEncoder.encode(plainPassword));
//            admin.setSignDate(new Date());
//
//            mRepo.save(admin);
//
//    
//            adminInitializationEnabled = false;
//        }
//    }
//}
