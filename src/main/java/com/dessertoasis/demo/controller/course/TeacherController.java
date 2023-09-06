package com.dessertoasis.demo.controller.course;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherDemo;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.MemberService;
import com.dessertoasis.demo.service.course.TeacherService;
import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService tService;
	
	@Autowired
	private MemberService mService;
	
//	@Autowired
//	private TeacherRepository tRepo;

	@PostMapping("/getTeacherPage")
	public List<TeacherDemo> getTeacherDatas(@RequestBody SortCondition sortCod) {
		List<TeacherDemo> teacherPage = tService.getTeacherPage(sortCod);

		return teacherPage;
	}
	
	
	//新增教師
	@PostMapping("/becomeTeacher")
    public ResponseEntity<String> becomeTeacher(@RequestBody Teacher teacher, @RequestParam("memberId") Integer memberId) {
        try {
        	// 創建一個新的 Teacher 實例，設定 memberId 和 teacherAccountStatus
            Teacher newTeacher = tService.becomeTeacher(memberId);
            
         // 設定其他 Teacher 相關屬性
            newTeacher.setTeacherName(teacher.getTeacherName());
            newTeacher.setTeacherProfilePic(teacher.getTeacherProfilePic());
            newTeacher.setTeacherAccountStatus("已啟用");
            newTeacher.setTeacherContract("YES");
            newTeacher.setTeacherProfile(teacher.getTeacherProfile());
            newTeacher.setTeacherMail(teacher.getTeacherMail());
            newTeacher.setTeacherTel(teacher.getTeacherTel());
        	
        	 tService.addTeacher(newTeacher);

            return ResponseEntity.ok("教師資料已成功新增");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("新增教師資料時發生錯誤：" + e.getMessage());
        }
    }
	
	//查詢老師個人資料by id
	@GetMapping("/{teacherId}")
    public Teacher getTeacherById(@PathVariable Integer teacherId,HttpSession session) {
		Member member = (Member)session.getAttribute("loggedInMember");
		
//		if(member.getAccess())
		
		Teacher result = tService.getTeacherById(teacherId);
		return result;
	}

	// 依照老師編號列出該教師所有課程
//	@GetMapping("/teacher/{teacherId}/courses")
//    public List<Course> getTeacherCourses(@PathVariable Integer teacherId) {
//		Teacher teacher = tRepo.findById(teacherId);
//		if (teacher != null) {
//            return cService.getCoursesByTeacher(teacher);
//        }
//        return Collections.emptyList();
//    }

	@GetMapping("/check-teacher")
	public ResponseEntity<String> checkTeacher(HttpServletRequest request) {
		// 從Cookie中獲得身份訊息
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userType".equals(cookie.getName()) && "teacher".equals(cookie.getValue())) {
					// 是教師身分
					return ResponseEntity.ok("You are a teacher.");
				}
			}
		}

		// 不是教師身分
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
	}

	@GetMapping("/set-teacher-cookie")
	public ResponseEntity<String> setTeacherCookie(HttpServletResponse response) {
		// 創建一個名為"userType"的Cookie，並將值設置為"teacher"
		Cookie userTypeCookie = new Cookie("userType", "teacher");

		// 可以設置Cookie的其他屬性，如過期時間、域等
		// userTypeCookie.setMaxAge(3600); // 過期时間設為1小時

		// 添加Cookie到response
		response.addCookie(userTypeCookie);

		return ResponseEntity.ok("Teacher cookie set successfully.");
	}
}
