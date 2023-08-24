package com.dessertoasis.demo.model.course;

public class CourseTeacherDTO {
	
	private String courseName;
	
	private String teacherName;

	public CourseTeacherDTO() {
	}
	
	public CourseTeacherDTO(String courseName, String teacherName) {
		this.courseName = courseName;
		this.teacherName = teacherName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}


}
