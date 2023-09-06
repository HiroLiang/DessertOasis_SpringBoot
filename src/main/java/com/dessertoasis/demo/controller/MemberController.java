package com.dessertoasis.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberState;
import com.dessertoasis.demo.model.product.Product;
import com.dessertoasis.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	
	//多筆
	@GetMapping("/all")
    public ResponseEntity<List<Member>> listProducts() {
        List<Member> products = mService.findAllMember();
        return ResponseEntity.ok(products);
    }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Member> getMemberById(@PathVariable Integer id) {
	    Member member = mService.findByMemberId(id);
	    if (member != null) {
	        return ResponseEntity.ok(member);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	@GetMapping("/{id}/access")
	public ResponseEntity<MemberAccess> getMemberaccessById(@PathVariable Integer id) {
	    Member member = mService.findByMemberId(id);
	    if (member != null) {
	    	MemberAccess access =member.getAccess();
	        return ResponseEntity.ok(access);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	
	 
	 
	 
	
	 @PutMapping("/changeStatus")
	    public ResponseEntity<String> changeMemberStatus(@RequestBody Map<String, String> requestData) {
	        try {
	            Integer memberId = Integer.parseInt(requestData.get("memberId"));
	            String newStatus = requestData.get("newStatus");

	            System.out.println("Received memberId: " + memberId);
	            System.out.println("Received newStatus: " + newStatus);
	            
	            Member member = mService.findByMemberId(memberId);
	            if (member != null) {
	                // 執行狀態更改
	                member.setMemberStatus(MemberState.valueOf(newStatus));
	                mService.updateMember(member); // 更新會員狀態

	                return ResponseEntity.ok("success");
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().body("Invalid memberId");
	        }
	    }
	}
	




