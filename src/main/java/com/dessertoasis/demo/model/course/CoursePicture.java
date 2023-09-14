package com.dessertoasis.demo.model.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="coursePicture")
public class CoursePicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="courseId", referencedColumnName = "id")
	@JsonIgnoreProperties("coursePictureList")
	private Course course;
	
	@Column(columnDefinition = "nvarchar(max)")
	private String courseImgURL;

	public CoursePicture() {
		// TODO Auto-generated constructor stub
	}

}
