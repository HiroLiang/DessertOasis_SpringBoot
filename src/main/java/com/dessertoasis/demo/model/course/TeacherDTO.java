package com.dessertoasis.demo.model.course;

import java.util.List;

import lombok.Data;

@Data
public class TeacherDTO {

	private String teacherName;
	
	private String teacherContract;
	
	private Integer teacherTel;
	
	private String teacherMail;
	
	private String teacherProfile;
	
	private List<TeacherPicture> pictures;
	
	public TeacherDTO() {
		// TODO Auto-generated constructor stub
	}

	public TeacherDTO(String teacherName, String teacherContract, Integer teacherTel, String teacherMail,
			String teacherProfile, List<TeacherPicture> pictures) {
		super();
		this.teacherName = teacherName;
		this.teacherContract = teacherContract;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.teacherProfile = teacherProfile;
		this.pictures = pictures;
	}
	
	

}
