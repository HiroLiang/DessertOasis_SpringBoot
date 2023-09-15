package com.dessertoasis.demo.model.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

//課程和課程標籤的中介表，因為課程和標籤是多對多關係
@Data
@Entity
@Table(name="courseCtag")
public class CourseCtag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JsonIgnoreProperties({"coursetagList"})
	@JoinColumn(name="courseId")//FK
	private Course course;
	
	@ManyToOne
	@JsonIgnoreProperties({"coursetagList"})
	@JoinColumn(name="cTagId")//FK
	private CTag ctag;
	
//	@ManyToMany(mappedBy = "courseTags")
//	private List<Course> courses;

	public CourseCtag() {
	}

}
