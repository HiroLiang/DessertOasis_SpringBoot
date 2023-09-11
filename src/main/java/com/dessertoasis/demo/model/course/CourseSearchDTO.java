package com.dessertoasis.demo.model.course;

import java.math.BigDecimal;

public class CourseSearchDTO {
	
	private Integer Page;
    private Integer PageSize;
	private String sortBy;
    private String sortOrder;
    
    private String courseName;
    private String category;
    private String teacherName;
    
    private String courseStatus;
    
    private Integer minCoursePrice;
    private Integer maxCoursePrice;
	public Integer getPage() {
		return Page;
	}
	public void setPage(Integer page) {
		Page = page;
	}
	public Integer getPageSize() {
		return PageSize;
	}
	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}
	public Integer getMinCoursePrice() {
		return minCoursePrice;
	}
	public void setMinCoursePrice(Integer minCoursePrice) {
		this.minCoursePrice = minCoursePrice;
	}
	public Integer getMaxCoursePrice() {
		return maxCoursePrice;
	}
	public void setMaxCoursePrice(Integer maxCoursePrice) {
		this.maxCoursePrice = maxCoursePrice;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
    
    

	

}
