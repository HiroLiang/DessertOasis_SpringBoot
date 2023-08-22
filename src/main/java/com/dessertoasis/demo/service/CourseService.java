package com.dessertoasis.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository cRepo;
	
	//利用 id 查詢課程
	public Course findById(Integer id) {
		Optional<Course> optional = cRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
}
