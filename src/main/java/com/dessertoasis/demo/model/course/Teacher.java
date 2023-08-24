package com.dessertoasis.demo.model.course;

import java.util.List;

import com.dessertoasis.demo.model.classroom.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="teacher")
public class Teacher {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="teacherId")
	private Integer teacherId;
	
	@Column(name="memberId", nullable = false)
	private Integer memberId;
	
	@Column(name="teacherContract")
	private String teacherContract;
	
	@Column(name="teacherName")
	private String teacherName;
	
	@Column(name="teacherPicURL")
	private String teacherPicURL;
	
	@Column(name="teacherTel")
	private Integer teacherTel;
	
	@Column(name="teacherMail")
	private String teacherMail;
	
	@Column(name="teacherProfile")
	private String teacherProfile;
	
	@Column(name="accountStatus")
	private String accountStatus;
	
	//一個教師有多堂課(一對多)
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	private List<Course> courseList;
	
	public Teacher() {
	}

}
