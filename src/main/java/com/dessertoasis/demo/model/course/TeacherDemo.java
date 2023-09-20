package com.dessertoasis.demo.model.course;

public class TeacherDemo {

	private Integer id;
	private String fullName;
	private String teacherName;
	private Integer teacherTel;
	private String teacherMail;
	private String teacherProfile;
	public TeacherDemo() {
		super();
	}
	
public TeacherDemo(Integer id, String fullName, String teacherName, Integer teacherTel, String teacherMail,
			String teacherProfile) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.teacherName = teacherName;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.teacherProfile = teacherProfile;
	}

//	public TeacherDemo(Integer id, String fullName, Integer teacherTel, String teacherMail) {
//		super();
//		this.id = id;
//		this.fullName = fullName;
//		this.teacherTel = teacherTel;
//		this.teacherMail = teacherMail;
//	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getTeacherTel() {
		return teacherTel;
	}
	public void setTeacherTel(Integer teacherTel) {
		this.teacherTel = teacherTel;
	}
	public String getTeacherMail() {
		return teacherMail;
	}
	public void setTeacherMail(String teacherMail) {
		this.teacherMail = teacherMail;
	}
	
}
