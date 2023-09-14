package com.dessertoasis.demo.controller.course;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseCmsTable;
import com.dessertoasis.demo.model.course.CourseDTO;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherCmsTable;
import com.dessertoasis.demo.model.course.TeacherDemo;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.member.MemberAccess;
import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.course.CourseService;
import com.dessertoasis.demo.service.course.TeacherService;
import com.dessertoasis.demo.service.member.MemberService;
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
	
	@Autowired
	private CourseService cService;

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
	@GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Integer id,HttpSession session) {
		
		// 判斷 user 存在且為 TEACHER
//				Member user = (Member) session.getAttribute("loggedInMember");
//				if (user == null || !user.getAccess().equals(MemberAccess.TEACHER)) {
//					return null;
//				}
		
		Teacher teacher = tService.getTeacherById(id);
		return ResponseEntity.ok(teacher);
	}
	
	//列出所有老師
	@GetMapping("/all")
	public ResponseEntity<List<Teacher>> findAllTeachers(){
		List<Teacher> teachers = tService.findAllTeachers();
		return ResponseEntity.ok(teachers);
	}

	// 依照老師編號列出該教師所有課程
	@GetMapping("/{id}/courses")
    public List<CourseDTO> getTeacherCourses(@PathVariable Integer id) {
		Teacher teacher = tService.getTeacherById(id);
		if (teacher != null) {
            return cService.getCoursesByTeacherId(id);
        }
        return Collections.emptyList();
    }
	
	//admin才可刪除老師
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer id,HttpSession session) {
		// 判斷 user 存在且為 ADMIN
//		Member user = (Member) session.getAttribute("loggedInMember");
//		if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//			return null;
//		}
		tService.deleteTeacherById(id);
        return ResponseEntity.ok("已成功刪除");
    }
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<Teacher> editTeacher(@PathVariable Integer id,@RequestBody Teacher teacher){
		Teacher existingTeacher = tService.getTeacherById(id);
		if(existingTeacher != null) {
			tService.update(teacher);
			return ResponseEntity.ok(teacher);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}

//	@GetMapping("/check-teacher")
//	public ResponseEntity<String> checkTeacher(HttpServletRequest request) {
//		// 從Cookie中獲得身份訊息
//		Cookie[] cookies = request.getCookies();
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if ("userType".equals(cookie.getName()) && "teacher".equals(cookie.getValue())) {
//					// 是教師身分
//					return ResponseEntity.ok("You are a teacher.");
//				}
//			}
//		}
//
//		// 不是教師身分
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
//	}

//	@GetMapping("/set-teacher-cookie")
//	public ResponseEntity<String> setTeacherCookie(HttpServletResponse response) {
//		// 創建一個名為"userType"的Cookie，並將值設置為"teacher"
//		Cookie userTypeCookie = new Cookie("userType", "teacher");
//
//		// 可以設置Cookie的其他屬性，如過期時間、域等
//		// userTypeCookie.setMaxAge(3600); // 過期时間設為1小時
//
//		// 添加Cookie到response
//		response.addCookie(userTypeCookie);
//
//		return ResponseEntity.ok("Teacher cookie set successfully.");
//	}
	
	// 課程分頁查詢
		@PostMapping("/pagenation")
		public List<TeacherCmsTable> getTeacherPage(@RequestBody SortCondition sortCon, HttpSession session) {
			System.out.println(sortCon);
			// 判斷 user 存在且為 ADMIN
//			Member user = (Member) session.getAttribute("loggedInMember");
//			if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
//				return null;
//			}
			// 送出查詢條件給service，若有結果則回傳list
			List<TeacherCmsTable> result = tService.getTeacherPagenation(sortCon);
			if (result != null) {
				System.out.println(result);
				return result;
			}
			return null;
		}

		@PostMapping("/pages")
		public Integer getPages(@RequestBody SortCondition sortCon, HttpSession session) {
			System.out.println(sortCon);
			// 判斷 user 存在且為 ADMIN
			Member user = (Member) session.getAttribute("loggedInMember");
			if (user == null || !user.getAccess().equals(MemberAccess.ADMIN)) {
				return null;
			}
			// 送出條件查詢總頁數
			Integer pages = tService.getPages(sortCon);
			return pages;
		}
}
