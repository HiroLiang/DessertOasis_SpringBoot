package com.dessertoasis.demo.model.course;

import com.dessertoasis.demo.model.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@Column(name = "memberId", nullable = false, insertable = false, updatable = false)
	private Integer memberId;

	@Column(name = "teacherContract", columnDefinition = "nvarchar(max)")
	private String teacherContract;

	@Column(name = "teacherName", columnDefinition = "nvarchar(50)")
	private String teacherName;

	@Column(name = "teacherProfilePic", columnDefinition = "nvarchar(max)")
	private String teacherProfilePic;

	@Column(name = "teacherTel")
	private Integer teacherTel;

	@Column(name = "teacherMail")
	private String teacherMail;

	@Column(name = "teacherProfile", columnDefinition = "nvarchar(max)")
	private String teacherProfile;

	@Column(name = "teacherAccountStatus", columnDefinition = "nvarchar(100)")
	private String teacherAccountStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "memberId")
	private Member member;

	// 一個教師有多堂課(一對多)
//	@JsonIgnore
//	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
//	private List<Course> courseList;

	public Teacher() {
	}

	public Teacher(Integer id, Integer memberId, String teacherContract, String teacherName, String teacherProfilePic,
			Integer teacherTel, String teacherMail, String teacherProfile, String teacherAccountStatus, Member member) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.teacherContract = teacherContract;
		this.teacherName = teacherName;
		this.teacherProfilePic = teacherProfilePic;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.teacherProfile = teacherProfile;
		this.teacherAccountStatus = teacherAccountStatus;
		this.member = member;
	}

	public Teacher(Integer id,String fullName, Integer teacherTel, String teacherMail) {
		super();
		this.id = id;
		this.teacherTel = teacherTel;
		this.teacherMail = teacherMail;
		this.member.setFullName(fullName);
	}

}
