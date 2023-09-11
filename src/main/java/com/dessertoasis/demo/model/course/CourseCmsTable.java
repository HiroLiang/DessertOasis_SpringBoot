package com.dessertoasis.demo.model.course;

import java.util.Date;

import lombok.Data;

@Data
public class CourseCmsTable {
	private Integer id;
	private String courseName;
	private String teacherName;
	private String courseStatus;
	private Date courseDate;
	private Integer remainPlaces;

	public CourseCmsTable() {
		// TODO Auto-generated constructor stub
	}

	public CourseCmsTable(Integer id, String courseName, String teacherName, String courseStatus, Date courseDate,
			Integer remainPlaces) {
		super();
		this.id = id;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.courseStatus = courseStatus;
		this.courseDate = courseDate;
		this.remainPlaces = remainPlaces;
	}
	

}
