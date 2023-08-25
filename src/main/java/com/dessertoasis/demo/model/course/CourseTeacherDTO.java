package com.dessertoasis.demo.model.course;

import java.util.Date;

public class CourseTeacherDTO {
	private Integer courseId;
	
	private String courseName;
	
	private String teacherName;
	
	private String courseStatus;
    private Date courseDate;
    private Date regDeadline;
    private String courseDescription;
    private String courseLocation;
    private Integer courseSortId;
    private Integer remainingPlaces;
    private Integer coursePrice;
    private String coursePictureUrl;
    private Integer courseVideoId;
    private Integer recipeId;
    private Integer tagId;

	public CourseTeacherDTO() {
	}
	
	public CourseTeacherDTO(String courseName, String teacherName) {
		this.courseName = courseName;
		this.teacherName = teacherName;
	}

	
	
	public CourseTeacherDTO(Integer courseId,String courseName, String teacherName, String courseStatus, Date courseDate,
			Date regDeadline, String courseDescription, String courseLocation, Integer courseSortId,
			Integer remainingPlaces, Integer coursePrice, String coursePictureUrl, Integer courseVideoId,
			Integer recipeId, Integer tagId) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.courseStatus = courseStatus;
		this.courseDate = courseDate;
		this.regDeadline = regDeadline;
		this.courseDescription = courseDescription;
		this.courseLocation = courseLocation;
		this.courseSortId = courseSortId;
		this.remainingPlaces = remainingPlaces;
		this.coursePrice = coursePrice;
		this.coursePictureUrl = coursePictureUrl;
		this.courseVideoId = courseVideoId;
		this.recipeId = recipeId;
		this.tagId = tagId;
	}
	
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public Date getRegDeadline() {
		return regDeadline;
	}

	public void setRegDeadline(Date regDeadline) {
		this.regDeadline = regDeadline;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	public Integer getCourseSortId() {
		return courseSortId;
	}

	public void setCourseSortId(Integer courseSortId) {
		this.courseSortId = courseSortId;
	}

	public Integer getRemainingPlaces() {
		return remainingPlaces;
	}

	public void setRemainingPlaces(Integer remainingPlaces) {
		this.remainingPlaces = remainingPlaces;
	}

	public Integer getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(Integer coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getCoursePictureUrl() {
		return coursePictureUrl;
	}

	public void setCoursePictureUrl(String coursePictureUrl) {
		this.coursePictureUrl = coursePictureUrl;
	}

	public Integer getCourseVideoId() {
		return courseVideoId;
	}

	public void setCourseVideoId(Integer courseVideoId) {
		this.courseVideoId = courseVideoId;
	}

	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}


	
}
