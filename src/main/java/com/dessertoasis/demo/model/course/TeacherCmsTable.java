package com.dessertoasis.demo.model.course;

import java.util.List;

import lombok.Data;

@Data
public class TeacherCmsTable {
	
	private Integer id;
	
	private String teacherName;
//	private String teacherProfilePic;
	private Integer teacherTel;
	private String teacherMail;
	private String teacherProfile;
	
	private String teacherContract;
	private String teacherAccountStatus;
	private String account;
//	private String courseName;
//	private List<TeacherPicture> pictures;
	

	public TeacherCmsTable() {
		// TODO Auto-generated constructor stub
	}


	public TeacherCmsTable(Integer id, String teacherName, Integer teacherTel, String teacherMail,
			String teacherProfile, String teacherContract, String teacherAccountStatus, String account) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.teacherProfile = teacherProfile;
		this.teacherContract = teacherContract;
		this.teacherAccountStatus = teacherAccountStatus;
		this.account = account;
	}
	
	


	

//	public TeacherCmsTable(Integer id, String fullName, String teacherName, Integer teacherTel, String teacherMail,
//			String teacherProfile) {
//		super();
//		this.id = id;
//		this.fullName = fullName;
//		this.teacherName = teacherName;
//		this.teacherTel = teacherTel;
//		this.teacherMail = teacherMail;
//		this.teacherProfile = teacherProfile;
////		this.pictures = pictures;
//	}


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
