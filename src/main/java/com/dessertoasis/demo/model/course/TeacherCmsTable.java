package com.dessertoasis.demo.model.course;

import lombok.Data;

@Data
public class TeacherCmsTable {
	
	private Integer id;
	private String fullName;
	private String teacherName;
	private String teacherProfilePic;
	private Integer teacherTel;
	private String teacherMail;
	private String teacherProfile;
	

	public TeacherCmsTable() {
		// TODO Auto-generated constructor stub
	}


	public TeacherCmsTable(Integer id, String fullName, String teacherName, String teacherProfilePic,
			Integer teacherTel, String teacherMail, String teacherProfile) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.teacherName = teacherName;
		this.teacherProfilePic = teacherProfilePic;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.teacherProfile = teacherProfile;
	}


	
	
	

}
