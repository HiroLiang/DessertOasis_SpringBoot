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
	
	private Date courseDate;
	
	private String coursePlace;
	
	private String courseStatus;
	
	private Integer remainPlaces;
	
	private Integer coursePrice;
	
	private List<CTag> tag;
	
	private List<CoursePicture> coursePictureList;
	
	public CourseDTO() {
	}

	public CourseDTO(Course course) {
		super();
		this.courseId = course.getId();
		this.teacherName = course.getTeacher().getTeacherName();
		this.recipeIntroduction = course.getRecipes().getRecipeIntroduction();
		this.categoryName = course.getCategory().getCategoryName();
		this.courseName = course.getCourseName();
		this.courseIntroduction = course.getCourseIntroduction();
		this.courseFeature = course.getCourseFeature();
		this.courseDestination = course.getCourseDestination();
		this.serviceTarget = course.getServiceTarget();
		this.closeDate = course.getCloseDate();
		this.updateDate = course.getUpdateDate();
		this.courseDate = course.getCourseDate();
		this.coursePlace = course.getCoursePlace();
		this.courseStatus = course.getCourseStatus();
		this.remainPlaces = course.getRemainPlaces();
		this.coursePrice = course.getCoursePrice();
//		this.tag = course.getCourseList().get(0);
		this.coursePictureList = course.getCoursePictureList();
	}
	
	

}
