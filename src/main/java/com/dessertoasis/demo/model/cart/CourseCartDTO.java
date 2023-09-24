package com.dessertoasis.demo.model.cart;

import java.util.Date;
import java.util.List;

import com.dessertoasis.demo.model.course.Course;
import com.dessertoasis.demo.model.course.CoursePicture;

import lombok.Data;

@Data
public class CourseCartDTO {
	private Integer cartId;
	private Integer courseId;
	private String courseName;
	private Integer coursePrice;
	private Integer remainPlaces;
	private Date closeDate;
	private Date courseDate;
	private String coursePlace;
	private	List<CoursePicture> pictures;
	
	public CourseCartDTO() {
		
	}
	
	public CourseCartDTO(Cart cart, Course course) {
		this.cartId = cart.getId();
		this.courseId = course.getId();
		this.courseName = course.getCourseName();
		this.coursePrice = course.getCoursePrice();
		this.remainPlaces = course.getRemainPlaces();
		this.closeDate = course.getCloseDate();
		this.courseDate = course.getCourseDate();
		this.coursePlace = course.getCoursePlace();
		this.pictures = course.getCoursePictureList();
	}
	
}
