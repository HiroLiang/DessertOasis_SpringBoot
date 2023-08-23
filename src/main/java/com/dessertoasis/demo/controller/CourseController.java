package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.service.CourseService;

import jakarta.transaction.Transactional;

@RestController
public class CourseController {

	@Autowired
	private CourseService cService;
	
	@Autowired
	private CourseRepository cRepo;
	
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
}
