package com.dessertoasis.demo.model.course;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.order.CourseOrderItem;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	//教師ID FK(連結教師ID)
	@ManyToOne
	@JoinColumn(name = "teacherId")
	private Teacher teacher;
	
	//食譜ID FK(連接食譜ID)
	@ManyToOne
	@JoinColumn(name = "recipesId")
	private Recipes recipes;
	
	//分類ID FK(連接分類ID)
	@ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
	
	//課程名稱
	@Column(name="courseName",columnDefinition = "nvarchar(200)")
	private String courseName;
	
	//課程介紹
	@Column(name="courseIntroduction",columnDefinition = "nvarchar(max)")
	private String courseIntroduction;
	
	//課程特色
	@Column(name="courseFeature",columnDefinition = "nvarchar(max)")
	private String courseFeature;
	
	//課程目標
	@Column(name="courseDestination",columnDefinition = "nvarchar(max)")
	private String courseDestination;
	
	//課程目標
	@Column(name="serviceTarget",columnDefinition = "nvarchar(max)")
	private String serviceTarget;
	
	//報名截止日
	@DateTimeFormat(pattern = "yyyy-MM-dd")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.DATE)
	@Column(name="closeDate")
	private Date closeDate;
	
	//更新日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.DATE)
	@Column(name="updateDate")
	private Date updateDate;
	
	//上課地點
	@Column(name="coursePlace",columnDefinition = "nvarchar(150)")
	private String coursePlace;
	

	//開課狀態
	@Column(name="courseStatus",columnDefinition = "nvarchar(50)")
	private String courseStatus;

	
	//剩餘名額
	@Column(name="remainPlaces",columnDefinition = "int")
	private Integer remainPlaces;
	
	//報名價格
	@Column(name="coursePrice",columnDefinition = "int")
	private Integer coursePrice;
	
	@JsonIgnore
	@OneToMany(mappedBy="course",cascade = CascadeType.ALL)
	private List<CourseCtag> courseList;
	
//	@ManyToMany
//    @JoinTable(name="course_coursetag", joinColumns = {@JoinColumn(name="course_id")}, inverseJoinColumns = {@JoinColumn(name="coursetag_id")})
//    private List<CourseCtag> courseTags;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course")
	private List<CoursePicture> coursePictureList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course")
	private List<CourseOrderItem> courseOrderItems;
	
	public Course() {
	}

	public Course(Integer id, Teacher teacher, Recipes recipes, Category category, String courseName,
			String courseIntroduction, String courseFeature, String courseDestination, String serviceTarget,
			Date closeDate, Date updateDate, String coursePlace, String courseStatus, Integer remainPlaces,
			Integer coursePrice) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.recipes = recipes;
		this.category = category;
		this.courseName = courseName;
		this.courseIntroduction = courseIntroduction;
		this.courseFeature = courseFeature;
		this.courseDestination = courseDestination;
		this.serviceTarget = serviceTarget;
		this.closeDate = closeDate;
		this.updateDate = updateDate;
		this.coursePlace = coursePlace;
		this.courseStatus = courseStatus;
		this.remainPlaces = remainPlaces;
		this.coursePrice = coursePrice;
	}

}
