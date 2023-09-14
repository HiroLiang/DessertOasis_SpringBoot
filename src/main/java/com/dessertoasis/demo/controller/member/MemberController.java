package com.dessertoasis.demo.controller.member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberDetail;
import com.dessertoasis.demo.service.member.MemberDetailService;
import com.dessertoasis.demo.service.member.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService mService;

	@Autowired
	private MemberDetailService mdService;

	// 多筆
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
			MemberAccess access = member.getAccess();
			return ResponseEntity.ok(access);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//更新詳細資料
	@PutMapping("/update")
	public ResponseEntity<MemberDetail> updateMemberDetail(@RequestBody MemberDetail updatedMemberDetail, HttpSession session) {
	    try {
	        // 從會話中獲取已登入的 Member
	        Member member = (Member) session.getAttribute("loggedInMember");
	        
	        // 確認用戶已登入
	        if (member != null) {
	            // 設置更新後的屬性
	        	member.getMemberDetail().setIdNumber(updatedMemberDetail.getIdNumber());
	        	member.getMemberDetail().setBirthday(updatedMemberDetail.getBirthday());
	        	member.getMemberDetail().setDeliveryAddress(updatedMemberDetail.getDeliveryAddress());
	        	member.getMemberDetail().setFolderURL(updatedMemberDetail.getFolderURL());
	        	member.getMemberDetail().setPic(updatedMemberDetail.getPic());

	            // 調用服務層方法更新 MemberDetail
	            MemberDetail updatedDetail = mdService.updateMemberDetail(member.getId(), member.getMemberDetail());
	            
	            // 返回更新後的 MemberDetail
	            return ResponseEntity.ok(updatedDetail);
	        } else {
	            // 未登入，返回未授權狀態碼
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	        }
	    } catch (ResourceNotFoundException e) {
	        // MemberDetail 未找到，返回 404 Not Found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    } catch (Exception e) {
	        // 其他錯誤處理，返回 500 Internal Server Error 或其他錯誤訊息
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	// 更新密碼
	@PostMapping("/changepassword")
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody, HttpSession session) {
		String oldPassword = requestBody.get("oldPassword");
		String newPassword = requestBody.get("newPassword");

		Member member = (Member) session.getAttribute("loggedInMember");
		if (member != null) {
			try {

				mService.updateMemberPassword(member.getId(), oldPassword, newPassword);
				return ResponseEntity.ok("密碼已更改");
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			} catch (RuntimeException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("密碼更新失敗。");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未授权访问，请登录后再试。");
		}
	}

}
