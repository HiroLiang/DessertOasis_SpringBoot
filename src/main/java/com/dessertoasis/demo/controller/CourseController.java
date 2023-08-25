package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseTeacherDTO;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.TeacherService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
public class CourseController {

	@Autowired
	private CourseService cService;
	
	@Autowired
	private TeacherService tService;
	
	//查詢單筆課程(用課程id)
	@GetMapping("/course/{id}")
	public Course findCourseById(@PathVariable Integer id) {
		Course course = cService.findById(id);
		return course;
	}
	
	//查詢所有課程
	@GetMapping("/course/all")
	public List<Course> findAllCourse(){
		List<Course> courses = cService.findAll();
		return courses;
	} 
	
	//新增單筆課程
	@PostMapping("/course/add")
	public String addCourse(@RequestBody Course course) {
		cService.insert(course);
		return "新增課程成功";
	}
	
	//修改單筆課程
	@Transactional
	@PutMapping("/course/update/{id}")
	public String updateCourse(@PathVariable Integer id, @RequestBody Course course) {
		 Course result = cService.updateById(id, course);
		 if(result == null) {
			 return "修改課程失敗";
		 }
		return "修改課程成功";
	}
	
	//刪除單筆課程
	@DeleteMapping("/course/{id}")
	public String deleteCourseById(@PathVariable Integer id) {
		boolean isDeleted = cService.deleteById(id);
		
		if(isDeleted) {
			return "刪除成功";
		}
		return "刪除失敗";
	}
	
	@PostMapping("/checkTeacher")
    @ResponseBody
	public String checkTeacher(HttpServletRequest request,@RequestBody Course course) {
		// 獲取請求中的所有Cookie
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	System.out.println(cookie.getName());
            	if(cookie.getName().equals("login1")&& cookie.getValue().equals("true")) {
            		System.out.println(course.getTeacherId());
            		if(course.getTeacherId()== 2) {
//            			System.out.println(course.getTeacherId());
            			return "這是 Teacher id";
            		}
            		return "這不是 Teacher id";
            	}
//                // 检查Cookie的名称是否为"teacher"，这里假设教师信息存储在名为"teacher"的Cookie中
//                if ("teacherId".equals(cookie.getName())) {
//                    // 如果找到名为"teacher"的Cookie，編寫逻辑判断教师信息是否存在于Cookie中
//                    // 比如，檢查Cookie的值是否符合教師的條件
//                    String teacherInfo = cookie.getValue();
//                    if (isTeacherId(teacherInfo)) {
//                        return "这是一个教师Cookie";
//                    }
//                }
            }
        }return "这不是一个教师Cookie";
	}
	
//	private boolean isTeacherId(String cookieValue) {
//        // 编写逻辑来判断Cookie中的值是否表示教师
//        // 您可以根据您的需求定义教师信息的格式和判断条件
//        // 这里只是一个示例，您可能需要根据实际情况进行修改
//        return cookieValue != null && cookieValue.contains("teacherId");
//    }
	
//	@GetMapping("/course/withTrName")
//	public List<CourseTeacherDTO> getAllCoursesWithTeacherNames() {
//        return cService.getAllCoursesWithTeacherNames();
//    }
	
	@GetMapping("/course/withTName")
	public List<CourseTeacherDTO> getAllCoursesWithTeacherInfo() {
        return cService.getAllCoursesWithTeacherInfo();
    }
}
