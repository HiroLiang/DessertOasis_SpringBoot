package com.dessertoasis.demo.model.course;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.order.CourseOrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	//教師編號
	@Column(name = "teacherId",insertable=false, updatable=false)
	private Integer teacherId;
	
	//課程名稱
	@Column(name="courseName",columnDefinition = "nvarchar(200)")
	private String courseName;
	
	//開課狀態
	@Column(name="courseStatus",columnDefinition = "nvarchar(50)")
	private String courseStatus;
	
	//開課日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.DATE)
	@Column(name="courseDate")
	private Date courseDate;
	
	//報名截止日
	@DateTimeFormat(pattern = "yyyy-MM-dd")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.DATE)
	@Column(name="regDeadline")
	private Date regDeadline;
	
	//課程介紹
	@Column(name="courseDescription",columnDefinition = "nvarchar(max)")
	private String courseDescription;
	
	//上課地點
	@Column(name="courseLocation",columnDefinition = "nvarchar(150)")
	private String courseLocation;
	
//	//課程分類編號
//	@Column(name="courseCategoryId",nullable = false,columnDefinition = "int")
//	private Integer courseSortId;
	
	//分類Id  OneToMany
//	@OneToMany(mappedBy = "course")
//	private List<CourseCategory> courseCategories;
	
	//剩餘名額
	@Column(name="remainingPlaces",columnDefinition = "int")
	private Integer remainingPlaces;
	
	//報名價格
	@Column(name="coursePrice",columnDefinition = "int")
	private Integer coursePrice;
	
	//課程圖片路徑
	@Column(name="coursePictureUrl",columnDefinition = "varchar(max)")
	private String coursePictureUrl;
	
	//課程影片編號
	@Column(name="courseVideoId",columnDefinition = "int")
	private Integer courseVideoId;
	
	//食譜編號
	@Column(name="recipeId",columnDefinition = "int")
	private Integer recipeId;
	
	//標籤編號
	@Column(name="tagId",columnDefinition = "int")
	private Integer tagId;
	
	//多對一
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "teacherId", referencedColumnName = "teacherId", nullable = false)
//	private Teacher teacher;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<CourseOrderItem> CourseOrderItemList;
	
	public Course() {
	}

//	public Course(Integer teacherId, String courseName, String courseStatus, Date courseDate, Date regDeadline,
//			String courseDescription, String courseLocation, Integer courseSortId, Integer remainingPlaces,
//			Integer coursePrice, String coursePictureUrl, Integer courseVideoId, Integer recipeId, Integer tagId) {
//		super();
//		this.teacherId = teacherId;
//		this.courseName = courseName;
//		this.courseStatus = courseStatus;
//		this.courseDate = courseDate;
//		this.regDeadline = regDeadline;
//		this.courseDescription = courseDescription;
//		this.courseLocation = courseLocation;
//		this.courseSortId = courseSortId;
//		this.remainingPlaces = remainingPlaces;
//		this.coursePrice = coursePrice;
//		this.coursePictureUrl = coursePictureUrl;
//		this.courseVideoId = courseVideoId;
//		this.recipeId = recipeId;
//		this.tagId = tagId;
//	}

	
//	public Integer getCourseSortId() {
//		return courseSortId;
//	}
//
//	public void setCourseSortId(Integer courseSortId) {
//		this.courseSortId = courseSortId;
//	}

		
}
