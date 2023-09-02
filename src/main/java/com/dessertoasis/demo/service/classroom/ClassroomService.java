package com.dessertoasis.demo.service.classroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.classroom.Classroom;
import com.dessertoasis.demo.model.classroom.ClassroomRepository;

@Service
public class ClassroomService {
	
	@Autowired
	private ClassroomRepository cRepo;
	
	public List<Classroom> findAll() {
		return cRepo.findAll();
	}
	
	public Classroom findById(Integer id) {
		return cRepo.findById(id).orElse(null);
	}
}
