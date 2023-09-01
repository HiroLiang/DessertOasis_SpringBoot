package com.dessertoasis.demo.model.course;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="expertise")
public class Expertise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String expertiseName;
	
	private String expertiseDescription;
	
	@OneToMany(mappedBy = "expertise")
	private List<TeacherExpertise> teacherList;

	public Expertise() {
	}

}
