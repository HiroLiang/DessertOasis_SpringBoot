package com.dessertoasis.demo.model.category;

import org.springframework.stereotype.Component;

@Component
public class RootCategory {
	
	private static Integer productCategoryId = 1;
	private static Integer courseCategoryId = 2;
	private static Integer recipeCategoryId = 3;
	private static Integer reservationCategoryId = 4;
	
	public Integer getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public Integer getCourseCategoryId() {
		return courseCategoryId;
	}
	public void setCourseCategoryId(Integer courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}
	public Integer getRecipeCategoryId() {
		return recipeCategoryId;
	}
	public void setRecipeCategoryId(Integer recipeCategoryId) {
		this.recipeCategoryId = recipeCategoryId;
	}
	public Integer getReservationCategoryId() {
		return reservationCategoryId;
	}
	public void setReservationCategoryId(Integer reservationCategoryId) {
		this.reservationCategoryId = reservationCategoryId;
	}
	
}
