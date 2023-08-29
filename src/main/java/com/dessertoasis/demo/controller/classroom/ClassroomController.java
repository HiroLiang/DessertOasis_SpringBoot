package com.dessertoasis.demo.controller.classroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.classroom.Classroom;
import com.dessertoasis.demo.service.classroom.ClassroomService;

@RestController
public class ClassroomController {
	
	@Autowired
	private ClassroomService cService;
	
	@GetMapping("/classroom/findall")
	public List<Classroom> findAllCalssrooms() {
		return cService.findAll();
	}
}
