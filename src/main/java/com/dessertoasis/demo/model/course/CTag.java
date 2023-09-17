package com.dessertoasis.demo.model.course;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="ctag")
public class CTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "nvarchar(max)")
	private String courseTagName;
	
	@Column(columnDefinition = "nvarchar(max)")
	private String courseTagDescription;
	
	@JsonIgnoreProperties({"ctag"})
	@OneToMany(mappedBy = "ctag",cascade = CascadeType.ALL)
	private List<CourseCtag> coursetagList;
	
	public CTag() {
	}

}
