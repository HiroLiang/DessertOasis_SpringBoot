package com.dessertoasis.demo.model.record;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data
@Table(name = "record")
public class Record {
	
	@Id @Column(name = "ID", columnDefinition = "INT")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "CATEGORYID", columnDefinition = "INT")
	private Integer categoryId;
	@Column(name = "TARGETID", columnDefinition = "INT")
	private Integer targetId;
	@Column(name = "RECORDDATE", columnDefinition = "DATETIME2")
	private Date recordDate;
	public Record() {
		super();
	}
	public Record(Integer id, Integer categoryId, Integer targetId, Date recordDate) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.targetId = targetId;
		this.recordDate = recordDate;
	}
	public Record(Integer categoryId, Integer targetId, Date recordDate) {
		super();
		this.categoryId = categoryId;
		this.targetId = targetId;
		this.recordDate = recordDate;
	}
	

}
