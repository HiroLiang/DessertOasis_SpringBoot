package com.dessertoasis.demo.model.course;

import java.util.List;

import com.dessertoasis.demo.model.member.Member;
import com.dessertoasis.demo.model.product.ProductPicture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "teacherName", columnDefinition = "nvarchar(50)")
	private String teacherName;

//	@Column(name = "teacherProfilePic", columnDefinition = "nvarchar(max)")
//	private String teacherProfilePic;

	@Column(name = "teacherContract", columnDefinition = "nvarchar(max) ")
	private String teacherContract;

	@Column(name = "teacherTel",columnDefinition = "int")
	private Integer teacherTel;

	@Column(name = "teacherMail",columnDefinition = "nvarchar(100)")
	private String teacherMail;

	@Column(name = "teacherProfile", columnDefinition = "nvarchar(max)")
	private String teacherProfile;

	@Column(name = "teacherAccountStatus", columnDefinition = "nvarchar(100)")

	private String teacherAccountStatus;

	//會員ID (FK)，要有對方的物件當屬性
//	@Column(name = "memberId", nullable = false, insertable = false, updatable = false)
//	private Integer memberId;
	
	//一個老師有一個會員id，用JsonIgnore避免無窮迴圈
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "memberId")
	@JsonIgnoreProperties({"teacher","emailForCode","code","passwords","memberDetail"})
	private Member member;

	// 一個教師有多堂課(一對多)
	@JsonIgnoreProperties({"teacher"})
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	private List<Course> courseList;
	
	@JsonIgnoreProperties("teacher")
	 @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
	   private List<TeacherPicture> pictures;
	
	//一個老師有多個專長
	@JsonIgnore
	@OneToMany(mappedBy="teacher", cascade = CascadeType.ALL)
	private List<TeacherExpertise> expertiseList;
	
//	@OneToMany(mappedBy="teacher")
//	private List<TeacherPicture> teacherPicture;

	public Teacher() {
	}
	
	

//	public Teacher(Integer id, Integer memberId, String teacherContract, String teacherName, String teacherProfilePic,
//			Integer teacherTel, String teacherMail, String teacherProfile, String teacherAccountStatus, Member member) {
//		super();
//		this.id = id;
//		this.memberId = memberId;
//		this.teacherContract = teacherContract;
//		this.teacherName = teacherName;
//		this.teacherProfilePic = teacherProfilePic;
//		this.teacherTel = teacherTel;
//		this.teacherMail = teacherMail;
//		this.teacherProfile = teacherProfile;
//		this.teacherAccountStatus = teacherAccountStatus;
//		this.member = member;
//	}

	public Teacher(Integer id,String fullName, Integer teacherTel, String teacherMail) {
		super();
		this.id = id;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.member.setFullName(fullName);
	}



public Teacher(Integer id, String teacherName, String teacherContract, Integer teacherTel, String teacherMail,
		String teacherProfile, String teacherAccountStatus, Member member, List<Course> courseList,
		List<TeacherPicture> pictures, List<TeacherExpertise> expertiseList) {
	super();
	this.id = id;
	this.teacherName = teacherName;
	this.teacherContract = teacherContract;
	this.teacherTel = teacherTel;
	this.teacherMail = teacherMail;
	this.teacherProfile = teacherProfile;
	this.teacherAccountStatus = teacherAccountStatus;
	this.member = member;
	this.courseList = courseList;
	this.pictures = pictures;
	this.expertiseList = expertiseList;
}



//public Teacher(Integer id, String teacherName, String teacherProfilePic, String teacherContract, Integer teacherTel,
//		String teacherMail, String teacherProfile, String teacherAccountStatus, Member member, List<Course> courseList,
//		List<TeacherExpertise> expertiseList) {
//	super();
//	this.id = id;
//	this.teacherName = teacherName;
//	this.teacherProfilePic = teacherProfilePic;
//	this.teacherContract = teacherContract;
//	this.teacherTel = teacherTel;
//	this.teacherMail = teacherMail;
//	this.teacherProfile = teacherProfile;
//	this.teacherAccountStatus = teacherAccountStatus;
//	this.member = member;
//	this.courseList = courseList;
//	this.expertiseList = expertiseList;
//}
	

}
