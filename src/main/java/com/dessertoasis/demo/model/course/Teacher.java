package com.dessertoasis.demo.model.course;

import java.util.List;

import com.dessertoasis.demo.model.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(name = "teacherProfilePic", columnDefinition = "nvarchar(max)")
	private String teacherProfilePic;

	@Column(name = "teacherContract", columnDefinition = "nvarchar(max)")
	private String teacherContract;

	@Column(name = "teacherTel",columnDefinition = "int")
	private Integer teacherTel;

	@Column(name = "teacherEmail",columnDefinition = "nvarchar(100)")
	private String teacherEmail;

	@Column(name = "teacherProfile", columnDefinition = "nvarchar(max)")
	private String teacherProfile;

	@Column(name = "teacherAccountStatus", columnDefinition = "nvarchar(100)")
	private String teacherAccountStatus;

	//會員ID (FK)，要有對方的物件當屬性
//	@Column(name = "memberId", nullable = false, insertable = false, updatable = false)
//	private Integer memberId;
	
	//一個老師有一個會員id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "memberId")
	private Member member;

	// 一個教師有多堂課(一對多)
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	private List<Course> courseList;
	
	//一個老師有多個專長
	@OneToMany(mappedBy="teacher", cascade = CascadeType.ALL)
	private List<TeacherExpertise> expertiseList;

	public Teacher() {
	}

//	public List<Course> getCourses(){
//		return courseList;
//	}
}
