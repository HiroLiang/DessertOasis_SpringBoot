package com.dessertoasis.demo.model.course;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="courseSchedule")
public class CourseSchedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date courseDate;
	
	private String timeZone;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
//	@Temporal(TemporalType.DATE)
	private LocalTime courseStartTime;

	public CourseSchedule() {
		// TODO Auto-generated constructor stub
	}

}
