package com.dessertoasis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.service.CourseService;

@RestController
public class CourseController {

	@Autowired
	private CourseService cService;
	
	@Autowired
	private CourseRepository cRepo;
	
	@GetMapping("/course/{id}")
	public Course findCourseById(@PathVariable Integer id) {
		Course course = cService.findById(id);
		return course;
	}
}
