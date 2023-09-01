package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.TeacherService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class TeacherController {

	@Autowired
	private TeacherService tService;

	@PostMapping("/getTeacherPage")
	public String getTeacherDatas(@RequestBody SortCondition sortCod) {
		tService.getTeacherPage(sortCod);

		return null;
	}

	// 依照老師編號列出該教師所有課程
//	@GetMapping("/teacher/{teacherId}/courses")
//    public List<Course> getTeacherCourses(@PathVariable Integer teacherId) {
//		Teacher teacher = tRepo.findByTeacherId(teacherId);
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
