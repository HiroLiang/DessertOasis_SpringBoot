package com.dessertoasis.demo.model.course;

import java.util.List;

import com.dessertoasis.demo.model.category.Category;

public class CourseCreateDTO {

	private Course course;
	
	private List<Category> categories;
	
	
	
	public CourseCreateDTO(Course course, List<Category> categories) {
		super();
		this.course = course;
		this.categories = categories;
	}



	public Course getCourse() {
		return course;
	}



	public void setCourse(Course course) {
		this.course = course;
	}



	public List<Category> getCategories() {
		return categories;
	}



	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}



	public CourseCreateDTO() {
		// TODO Auto-generated constructor stub
	}

}
