package com.dessertoasis.demo.model.course;

import java.util.List;

import lombok.Data;

@Data
public class TeacherFrontDTO {
	
	private Integer id;
	
	private String teacherName;
	
//	private String courseName;

	public TeacherFrontDTO() {
		// TODO Auto-generated constructor stub
	}

	public TeacherFrontDTO(Integer id, String teacherName) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		
	}

	
}
