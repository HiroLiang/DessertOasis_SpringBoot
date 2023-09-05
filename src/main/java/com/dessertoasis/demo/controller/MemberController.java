package com.dessertoasis.demo.controller;

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
import com.dessertoasis.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	@GetMapping("/details/{id}")
	public ResponseEntity<Member> getMemberById(@PathVariable Integer id) {
	    Member member = mService.findByMemberId(id);
	    if (member != null) {
	        return ResponseEntity.ok(member);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	@GetMapping("/details/{id}/access")
	public ResponseEntity<MemberAccess> getMemberaccessById(@PathVariable Integer id) {
	    Member member = mService.findByMemberId(id);
	    if (member != null) {
	    	MemberAccess access =member.getAccess();
	        return ResponseEntity.ok(access);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	 @GetMapping("/memberInfo")
	    public ResponseEntity<Member> getMemberInfo(HttpServletRequest request) {
	        // 假設你已經在登入時將會員資訊存儲在Session中，你可以通過Session獲取會員資訊
	        HttpSession session = request.getSession();
	        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

	        if (loggedInMember != null) {
	            // 如果成功獲取到會員資訊，返回該會員的JSON數據
	            return new ResponseEntity<>(loggedInMember, HttpStatus.OK);
	        } else {
	            // 如果未獲取到會員資訊，返回404 Not Found或其他適當的錯誤狀態碼
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
	




