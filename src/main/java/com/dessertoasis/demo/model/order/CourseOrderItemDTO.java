package com.dessertoasis.demo.model.order;

import java.util.Date;
import java.util.List;

import com.dessertoasis.demo.model.course.CoursePicture;

import lombok.Data;

@Data
public class CourseOrderItemDTO {
	private Integer courseOrderItemId;
	private Integer courseId;
	private String courseName;
	private Integer coursePrice;
	private Integer remainPlaces;
	private Date closeDate;
	private Date courseDate;
	private String coursePlace;
	private	List<CoursePicture> pictures;
	
	public CourseOrderItemDTO() {
		
	}
	
	public CourseOrderItemDTO(CourseOrderItem courseOrderItem) {
		this.courseOrderItemId = courseOrderItem.getId();
		this.courseId = courseOrderItem.getCourse().getId();
		this.courseName = courseOrderItem.getCourse().getCourseName();
		this.coursePrice = courseOrderItem.getCourse().getCoursePrice();
		this.remainPlaces = courseOrderItem.getCourse().getRemainPlaces();
		this.closeDate = courseOrderItem.getCourse().getCloseDate();
		this.courseDate = courseOrderItem.getCourse().getCourseDate();
		this.coursePlace = courseOrderItem.getCourse().getCoursePlace();
		this.pictures = courseOrderItem.getCourse().getCoursePictureList();
	}
}
