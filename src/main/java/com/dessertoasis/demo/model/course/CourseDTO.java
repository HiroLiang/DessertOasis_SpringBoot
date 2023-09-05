package com.dessertoasis.demo.model.course;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CourseDTO {
	
	private Integer courseId;
	
	private String teacherName;
	
	private String recipeIntroduction;
	
	private String categoryName;
	
	private String courseName;
	
	private String courseIntroduction;
	
	private String courseFeature;
	
	private String courseDestination;
	
	private String serviceTarget;
	
	private Date closeDate;
	
	private Date updateDate;
	
	private String coursePlace;
	
	private String courseStatus;
	
	private Integer remainPlaces;
	
	private Integer coursePrice;
	
	private List<CTag> tag;
	
	private List<CoursePicture> coursePictureList;
	
	public CourseDTO() {
		// TODO Auto-generated constructor stub
	}
	
	

}
