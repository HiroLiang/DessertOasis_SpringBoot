package com.dessertoasis.demo.model.course;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

//老師和老師專長的中介表，因為老師和老師專長是多對多關係
@Data
@Table(name="TeacherExpertise")
@Entity
public class TeacherExpertise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//多個專長屬於一個老師
	@ManyToOne
	@JoinColumn(name="teacherId") //FK
	private Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name="expertiseId") //FK
	private Expertise expertise;

	public TeacherExpertise() {
	}

}
