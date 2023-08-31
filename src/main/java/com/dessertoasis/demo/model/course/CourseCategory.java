package com.dessertoasis.demo.model.course;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="courseCategory")
public class CourseCategory {

	public CourseCategory() {
		
	}

}
