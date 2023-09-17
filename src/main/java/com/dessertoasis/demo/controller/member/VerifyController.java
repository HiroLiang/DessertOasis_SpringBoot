package com.dessertoasis.demo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dessertoasis.demo.service.member.MemberService;

@Controller
public class VerifyController {
	@Autowired
    private MemberService mService;
	//驗證
    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
        @RequestParam String otp) {
      return new ResponseEntity<>(mService.verifyAccount(email, otp), HttpStatus.OK);
    }
    
    
    @GetMapping("/verify-account")
    public String showVerificationPage(@RequestParam String email,
                                       @RequestParam String otp,
                                       Model model) {
        String verificationMessage = mService.verifyAccount(email, otp);
        model.addAttribute("verificationMessage", verificationMessage);
        
        System.out.println("Verification Message: " + verificationMessage);
        
        return "verification"; 
    }
}
