package com.dessertoasis.demo.model.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="teacherPicture")
public class TeacherPicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "teacherId")
	@JsonIgnoreProperties({"teacherPicture"})
	private Teacher teacher;
	
	@Column(name = "pictureURL",columnDefinition = "nvarchar(max)")
	private String pictureURL;

	public TeacherPicture() {
		// TODO Auto-generated constructor stub
	}

	public TeacherPicture(Integer id, Teacher teacher, String pictureURL) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.pictureURL = pictureURL;
	}
	
	

}
