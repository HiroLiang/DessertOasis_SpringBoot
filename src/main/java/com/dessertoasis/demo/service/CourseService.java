package com.dessertoasis.demo.service;

import java.util.List;
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
	
	//查詢全部課程
	public List<Course> findAll(){
		List<Course> result = cRepo.findAll();
		return result; 
	}
	
	//新增單筆課程
	public void insert(Course course) {
		cRepo.save(course);
	}
	
	//修改單筆課程
	
	//刪除單筆課程
	public boolean deleteById(Integer id) {
		Optional<Course> optional = cRepo.findById(id);
		
		if(optional.isPresent()) {
			cRepo.deleteById(id);
			return true;
		}
		return false;
	}
}
