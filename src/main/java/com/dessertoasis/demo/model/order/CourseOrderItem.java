package com.dessertoasis.demo.model.order;

import com.dessertoasis.demo.model.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "courseOrderItem")
public class CourseOrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonIgnoreProperties({"reservations", "prodOrderItems", "courseOrderItems"})
	@ManyToOne
	@JoinColumn(name = "ordId")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "courseId")
	private Course course;
	
	@Column
	private Integer price;
	
	public CourseOrderItem() {
		
	}
	
	public CourseOrderItem(Course course, Order order) {
		this.course = course;
		this.price = course.getCoursePrice();
		this.order = order;
	}
}
