package com.dessertoasis.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.dessertoasis.demo.model.course.Teacher;
import com.dessertoasis.demo.model.course.TeacherRepository;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository tRepo;
	
	@GetMapping("/teacher/{id}/course")
	public Optional<Teacher> getCourseByTeacherId(Integer id) {
        Optional<Teacher> data = tRepo.findById(id);
        return data;
    }
}
