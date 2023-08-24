package com.dessertoasis.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;
import com.dessertoasis.demo.service.CourseService;
import com.dessertoasis.demo.service.TeacherService;

@RestController
public class TeacherController {

	@Autowired
	private CourseService cService;
	
	@Autowired
	private TeacherRepository tRepo;
	
	//依照老師編號列出該教師所有課程
	@GetMapping("/teacher/{teacherId}/courses")
    public List<Course> getTeacherCourses(@PathVariable Integer teacherId) {
		Teacher teacher = tRepo.findByTeacherId(teacherId);
		if (teacher != null) {
            return cService.getCoursesByTeacher(teacher);
        }
        return Collections.emptyList();
    }
}
