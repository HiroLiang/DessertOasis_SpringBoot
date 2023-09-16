package com.dessertoasis.demo.model.course;

import java.util.List;

import lombok.Data;

@Data
public class TeacherCmsTable {
	
	private Integer id;
	private String fullName;
	private String teacherName;
//	private String teacherProfilePic;
	private Integer teacherTel;
	private String teacherMail;
	private String teacherProfile;
//	private List<TeacherPicture> pictures;
	

	public TeacherCmsTable() {
		// TODO Auto-generated constructor stub
	}


	public TeacherCmsTable(Integer id, String fullName, String teacherName, Integer teacherTel, String teacherMail,
			String teacherProfile) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.teacherName = teacherName;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.teacherProfile = teacherProfile;
//		this.pictures = pictures;
	}


//	public TeacherCmsTable(Integer id, String fullName, String teacherName, String teacherProfilePic,
//			Integer teacherTel, String teacherMail, String teacherProfile) {
//		super();
//		this.id = id;
//		this.fullName = fullName;
//		this.teacherName = teacherName;
//		this.teacherProfilePic = teacherProfilePic;
//		this.teacherTel = teacherTel;
//		this.teacherMail = teacherMail;
//		this.teacherProfile = teacherProfile;
//	}


	
	
	

}
