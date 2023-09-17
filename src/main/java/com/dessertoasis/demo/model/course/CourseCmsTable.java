package com.dessertoasis.demo.model.course;

import java.util.Date;

import lombok.Data;

@Data
public class CourseCmsTable {
	private Integer id;
	private String teacherName;
	private String courseImgURL;
	private String courseName;
	private Date courseDate;
	private Date closeDate;
	private String coursePlace;
	private Integer remainPlaces;
	private Integer coursePrice;
	private String courseStatus;
	private String courseIntroduction;
	private Integer categoryId;
	

	public CourseCmsTable() {
		// TODO Auto-generated constructor stub
	}


	public CourseCmsTable(Integer id, String teacherName, String courseName, Date courseDate, Date closeDate,
			String coursePlace, Integer remainPlaces, Integer coursePrice, String courseStatus,
			String courseIntroduction) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.courseName = courseName;
		this.courseDate = courseDate;
		this.closeDate = closeDate;
		this.coursePlace = coursePlace;
		this.remainPlaces = remainPlaces;
		this.coursePrice = coursePrice;
//		this.categoryName = categoryName;
		this.courseStatus = courseStatus;
		this.courseIntroduction = courseIntroduction;
	}


	public CourseCmsTable(Integer id, String teacherName, String courseImgURL, String courseName, Date courseDate,
			Date closeDate, String coursePlace, Integer remainPlaces, Integer coursePrice, String courseStatus,
			String courseIntroduction, Integer categoryId) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.courseImgURL = courseImgURL;
		this.courseName = courseName;
		this.courseDate = courseDate;
		this.closeDate = closeDate;
		this.coursePlace = coursePlace;
		this.remainPlaces = remainPlaces;
		this.coursePrice = coursePrice;
		this.courseStatus = courseStatus;
		this.courseIntroduction = courseIntroduction;
		this.categoryId = categoryId;
	}


	public CourseCmsTable(Integer id, String teacherName, String courseName, Date courseDate, Date closeDate,
			String coursePlace, Integer remainPlaces, Integer coursePrice, String courseStatus,
			String courseIntroduction, Integer categoryId) {
		super();
		this.id = id;
		this.teacherName = teacherName;
		this.courseName = courseName;
		this.courseDate = courseDate;
		this.closeDate = closeDate;
		this.coursePlace = coursePlace;
		this.remainPlaces = remainPlaces;
		this.coursePrice = coursePrice;
		this.courseStatus = courseStatus;
		this.courseIntroduction = courseIntroduction;
		this.categoryId = categoryId;
	}


//	public CourseCmsTable(Integer id, String teacherName, String courseName, Date courseDate, Date closeDate,
//			String coursePlace, Integer remainPlaces, Integer coursePrice, String courseStatus) {
//		super();
//		this.id = id;
//		this.teacherName = teacherName;
//		this.courseName = courseName;
//		this.courseDate = courseDate;
//		this.closeDate = closeDate;
//		this.coursePlace = coursePlace;
//		this.remainPlaces = remainPlaces;
//		this.coursePrice = coursePrice;
//		this.courseStatus = courseStatus;
//	}


//	public CourseCmsTable(Integer id, String teacherName, String courseName, Date courseDate, Date closeDate,
//			String coursePlace, Integer remainPlaces, Integer coursePrice, String categoryName, String courseStatus) {
//		super();
//		this.id = id;
//		this.teacherName = teacherName;
//		this.courseName = courseName;
//		this.courseDate = courseDate;
//		this.closeDate = closeDate;
//		this.coursePlace = coursePlace;
//		this.remainPlaces = remainPlaces;
//		this.coursePrice = coursePrice;
//		this.categoryName = categoryName;
//		this.courseStatus = courseStatus;
//	}

	

}
