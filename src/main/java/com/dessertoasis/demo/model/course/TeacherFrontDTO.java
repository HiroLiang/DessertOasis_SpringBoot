package com.dessertoasis.demo.model.course;

import java.util.List;

import lombok.Data;

@Data
public class TeacherFrontDTO {
	
	private Integer id;
	
	private String teacherName;
	
	private String pictureURL;
	
	private String courseName;
	
	public TeacherFrontDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public TeacherFrontDTO(Integer id, String teacherName,String pictureURL,String courseName) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.pictureURL = pictureURL;
		this.courseName = courseName;
	}

	
}
