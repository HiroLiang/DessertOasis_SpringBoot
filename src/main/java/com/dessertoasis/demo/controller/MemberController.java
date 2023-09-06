package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberDetail;
import com.dessertoasis.demo.service.MemberDetailService;
import com.dessertoasis.demo.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	@Autowired
	private MemberDetailService mdService;
	
	//多筆
	@GetMapping("/all")
	 public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = mService.findAllMember();
        if (members.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(members);
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
	
	@GetMapping("/{id}/details")
	public MemberDetail getMemberDetail(@PathVariable Integer id) {
	    // 根據 memberId 查詢相關聯的 MemberDetail
	    MemberDetail memberDetail = mdService.getMemberDetailByMemberId(id);

	    if (memberDetail != null) {
	        return memberDetail; // 找到會員詳細資訊，返回該資訊
	    } else {
	        // 如果找不到會員詳細資訊，可以返回一個特殊的 "未找到" 或空的 MemberDetail 物件
	        return new MemberDetail(); // 或者返回 null，視情況而定
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
	
	
	
	 
	 
	 
	
	 
	}
	




