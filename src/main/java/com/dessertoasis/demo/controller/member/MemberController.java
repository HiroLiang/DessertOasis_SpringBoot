package com.dessertoasis.demo.controller.member;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
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

import com.dessertoasis.demo.ImageUploadUtil;
import com.dessertoasis.demo.model.member.Bank;
import com.dessertoasis.demo.model.member.BankRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.member.MemberCmsTable;
import com.dessertoasis.demo.model.member.MemberDetail;
import com.dessertoasis.demo.model.member.MemberDetailRepository;
import com.dessertoasis.demo.model.order.OrderCmsTable;
import com.dessertoasis.demo.model.recipe.PicturesDTO;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.member.BankService;
import com.dessertoasis.demo.service.member.MemberDetailService;
import com.dessertoasis.demo.service.member.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService mService;
	
	@Autowired
	private MemberDetailRepository mRepo;
	
	@Autowired
	private MemberDetailService mdService;
	
	@Autowired
	private ImageUploadUtil imgUtil;
	@Autowired
	private BankService bService;


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
	
	@GetMapping("/{id}/bank")
	public Bank getMemberBank(@PathVariable Integer id) {
		
		Bank bank = bService.getMemberBankByMemberId(id);

		if (bank != null) {
			return bank; 
		} else {
			
			return new Bank(); 
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

	// 更新詳細資料
	@PutMapping("/update/detail")
	public ResponseEntity<MemberDetail> updateMemberDetail(@RequestBody MemberDetail updatedMemberDetail,
			HttpSession session) {
		try {
			// 獲取已登入的 Member
			Member member = (Member) session.getAttribute("loggedInMember");

			// 確認用戶已登入
			if (member != null) {
				// 設置更新後的屬性

				member.getMemberDetail().setIdNumber(updatedMemberDetail.getIdNumber());
				member.getMemberDetail().setBirthday(updatedMemberDetail.getBirthday());
				member.getMemberDetail().setDeliveryAddress(updatedMemberDetail.getDeliveryAddress());
				
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
	//使用者更新
	@PutMapping("/update")
	public ResponseEntity<Member> updateMember(@RequestBody Member updatedMember,
	        HttpSession session) {
	    try {
	        // 獲取已登入的 Member
	        Member member = (Member) session.getAttribute("loggedInMember");

	        // 確認用戶已登入
	        if (member != null) {
	            // 設置更新後的屬性
	            Integer memberId = member.getId();
	            System.out.println("我ID是" + memberId);

	            // 使用成员对象的ID从数据库中获取原始成员信息
	            Member existingMember = mService.findByMemberId(memberId);
	            
	            if (existingMember != null) {
	                // 更新原始成员信息
	                existingMember.setEmail(updatedMember.getEmail());
	                existingMember.setFullName(updatedMember.getFullName());
	                existingMember.setMemberName(updatedMember.getMemberName());
	                if (updatedMember.getAccess() != null) {
	                    existingMember.setAccess(updatedMember.getAccess());
	                }
	                if (updatedMember.getMemberStatus() != null) {
	                    existingMember.setMemberStatus(updatedMember.getMemberStatus());
	                }

	                // 调用更新方法来执行更新操作
	                mService.updateMember(existingMember);

	                // 返回更新後的 MemberDetail
	                return ResponseEntity.ok(updatedMember);
	            } else {
	                // 未找到原始成员，返回 404 Not Found
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	            }
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
	
	//處理圖片路近並儲存圖片路徑
	@PostMapping("/uploadMemberImg")
	public String memberPic(@RequestBody List<PicturesDTO> pictures, HttpSession session) {
	    Member member = (Member) session.getAttribute("loggedInMember");
	    if (member != null) {
	        final String uploadPath = "C:/dessertoasis-vue/public/";
	        if (!pictures.isEmpty()) {
	            try {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	                String timestamp = LocalDateTime.now().format(formatter);

	                Integer memId = member.getId();

	                String sqlPath = "images/member" + "/" + memId + "/";
	                String userFolder = uploadPath + sqlPath;

	                File folder = new File(userFolder);
	                if (!folder.exists()) {
	                    folder.mkdirs();
	                }

	                if (!pictures.get(0).getBase64Content().isEmpty()) {
	                    /*----------取得前端傳來的圖檔字串----------*/
	                    String mainPic = pictures.get(0).getBase64Content();
	                    String mainPicName = pictures.get(0).getFileName();
	                    /*----------取得前端傳來的圖檔字串----------*/
//	                    System.out.println("mainPicName: " + mainPicName);
	                    /*----------寫入檔案及儲存位置串接字串----------*/
	                    String mainFileName = mainPicName.substring(0, mainPicName.lastIndexOf("."));// 將副檔名與檔名拆開 取得檔名
	                    String mainExtension = mainPicName.substring(mainPicName.lastIndexOf("."));// 將副檔名與檔名拆開 取得副檔名
	                    String mainUniqueName = mainFileName + "_" + timestamp + mainExtension;// 將時間串入檔名

//	                    System.out.println("mainFileName: " + mainFileName);
//	                    System.out.println("mainExtension: " + mainExtension);
//	                    System.out.println("timestamp: " + timestamp);
//	                    
	                    byte[] mainDecode = Base64.getDecoder().decode(mainPic);
	                    File mainfile = new File(userFolder + mainUniqueName);

	                    try (FileOutputStream mainfileOutputStream = new FileOutputStream(mainfile)) {
	                        mainfileOutputStream.write(mainDecode);
	                        mainfileOutputStream.flush();
	                        String imgUrl = sqlPath+mainPicName;
	                        
	                        MemberDetail memberDetail = member.getMemberDetail();
	                        if (memberDetail != null) {
	                           //儲存圖片路徑
	                            memberDetail.setPic(imgUrl);

	                          
	                            mdService.updateMemberDetail(memId, memberDetail);
	                        }
	                        return imgUrl;

	                    } catch (IOException e) {
	                        e.printStackTrace();
	                        System.out.println(e);
	                        return "寫入失敗";
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                System.out.println(e);
	            }
	        }
	    }

	    return "未成功";
	}
	
	//獲取圖片URL
	@GetMapping("/getImageURL")
    public ResponseEntity<String> getImageURL(HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member != null) {
            MemberDetail memberDetail = member.getMemberDetail();
            if (memberDetail != null) {
                String imageURL = memberDetail.getPic();
                if (imageURL != null && !imageURL.isEmpty()) {
                    return ResponseEntity.ok().body(imageURL);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
	
	// 訂單分頁查詢
	 @PostMapping("/pagenation")
	 public List<MemberCmsTable> getMemberPage(@RequestBody SortCondition sortCon, HttpSession session){
		 System.out.println(sortCon);
		// 判斷 user 存在且為 ADMIN
//		Member member = (Member) session.getAttribute("loggedInMember");
//		if (member == null || !member.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		// 送出查詢條件給service，若有結果則回傳list
		List<MemberCmsTable> result = mService.getMemberPagenation(sortCon);
		if (result != null) {
			System.out.println(result);
			return result;
		}
		return null;
	}
	 
	 //總頁數
	 @PostMapping("/pages")
	 public Integer getMemberPages(@RequestBody SortCondition sortCon, HttpSession session) {
		 System.out.println(sortCon);
			// 判斷 user 存在且為 ADMIN
			Member user = (Member) session.getAttribute("loggedInMember");
			if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
				return null;
			}
			// 送出條件查詢總頁數
			Integer pages = mService.getMemberPages(sortCon);
			return pages;
	 }
	 
	 @PutMapping("/update/{id}")
	 public ResponseEntity<Member> updateMember(@PathVariable Integer id, @RequestBody Member updatedMember) {
	     try {
	         // 使用ID从数据库中获取原始成员信息
	         Member existingMember = mService.findByMemberId(id);

	         if (existingMember != null) {
	             // 更新原始成员信息
	             existingMember.setEmail(updatedMember.getEmail());
	             existingMember.setFullName(updatedMember.getFullName());
	             existingMember.setMemberName(updatedMember.getMemberName());
	             if (updatedMember.getAccess() != null) {
	                 existingMember.setAccess(updatedMember.getAccess());
	             }
	             if (updatedMember.getMemberStatus() != null) {
	                 existingMember.setMemberStatus(updatedMember.getMemberStatus());
	             }

	             // 调用更新方法来执行更新操作
	             mService.updateMember(existingMember);

	             // 返回更新后的 MemberDetail
	             return ResponseEntity.ok(existingMember);
	         } else {
	             // 未找到原始成员，返回 404 Not Found
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	         }
	     } catch (ResourceNotFoundException e) {
	         // MemberDetail 未找到，返回 404 Not Found
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	     } catch (Exception e) {
	         // 其他错误处理，返回 500 Internal Server Error 或其他错误消息
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	     }
	 }


}
