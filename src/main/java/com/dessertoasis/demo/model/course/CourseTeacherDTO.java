package com.dessertoasis.demo.model.course;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class CourseTeacherDTO {
	private Integer teacherId;
//	private Integer memberId;
	
	private String courseName;
	
//	private String teacherName;
	
	private String courseStatus;
    private Date courseDate;
    private Date closeDate;
    private Date updateDate;
    private String courseIntroduction;
    private String coursePlace;
private String courseFeature;
	
	private String courseDestination;
	
	private String serviceTarget;
    private Integer remainPlaces;
    private Integer coursePrice;
//    private String coursePictureUrl;
    private Integer courseVideoId;
    private String recipeIntroduction;
//    private Integer recipeId;
    private Integer tagId;
    private String categoryName;
    private List<CTag> tag;
    private List<CoursePicture> coursePictureList;

	public CourseTeacherDTO() {
	}

	public CourseTeacherDTO(Course course,Teacher teacher) {
		super();
		this.teacherId = teacher.getId();
		this.courseName = course.getCourseName();
		this.courseStatus = course.getCourseStatus();
		this.courseDate = course.getCourseDate();
		this.closeDate = course.getCloseDate();
		this.updateDate = course.getUpdateDate();
		this.courseIntroduction = course.getCourseIntroduction();
		this.coursePlace = course.getCoursePlace();
		this.courseFeature = course.getCourseFeature();
		this.courseDestination = course.getCourseDestination();
		this.serviceTarget = course.getServiceTarget();
		this.remainPlaces = course.getRemainPlaces();
		this.coursePrice = course.getCoursePrice();
//		this.courseVideoId = courseVideoId;
		this.recipeIntroduction = course.getRecipes().getRecipeIntroduction();
//		this.tagId = tagId;
//		this.categoryName = categoryName;
//		this.tag = tag;
//		this.coursePictureList = coursePictureList;
	}
	
//	public CourseTeacherDTO(String courseName, String teacherName) {
//		this.courseName = courseName;
//		this.teacherName = teacherName;
//	}

	

	
	
}
