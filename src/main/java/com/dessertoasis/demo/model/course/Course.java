package com.dessertoasis.demo.model.course;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="courseId")
	private Integer courseId;
	
	//教師編號
	@Column(name = "teacherId")
	private Integer teacherId;
	
	//課程名稱
	@Column(name="courseName")
	private String courseName;
	
	//開課狀態
	@Column(name="courseStatus")
	private String courseStatus;
	
	//開課日期
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="courseDate")
	private Date courseDate;
	
	//報名截止日
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="regDeadline")
	private Date regDeadline;
	
	//課程介紹
	@Column(name="courseDescription")
	private String courseDescription;
	
	//上課地點
	@Column(name="courseLocation")
	private String courseLocation;
	
	//課程分類編號
	@Column(name="courseSortId")
	private Integer courseSortId;
	
	//剩餘名額
	@Column(name="remainingPlaces")
	private Integer remainingPlaces;
	
	//報名價格
	@Column(name="coursePrice")
	private Integer coursePrice;
	
	//課程圖片路徑
	@Column(name="coursePictureUrl")
	private String coursePictureUrl;
	
	//課程影片編號
	@Column(name="courseVideoId")
	private Integer courseVideoId;
	
	//食譜編號
	@Column(name="recipeId")
	private Integer recipeId;
	
	//標籤編號
	@Column(name="tagId")
	private Integer tagId;
	
	public Course() {
	}

	public Course(Integer teacherId, String courseName, String courseStatus, Date courseDate, Date regDeadline,
			String courseDescription, String courseLocation, Integer courseSortId, Integer remainingPlaces,
			Integer coursePrice, String coursePictureUrl, Integer courseVideoId, Integer recipeId, Integer tagId) {
		super();
		this.teacherId = teacherId;
		this.courseName = courseName;
		this.courseStatus = courseStatus;
		this.courseDate = courseDate;
		this.regDeadline = regDeadline;
		this.courseDescription = courseDescription;
		this.courseLocation = courseLocation;
		this.courseSortId = courseSortId;
		this.remainingPlaces = remainingPlaces;
		this.coursePrice = coursePrice;
		this.coursePictureUrl = coursePictureUrl;
		this.courseVideoId = courseVideoId;
		this.recipeId = recipeId;
		this.tagId = tagId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public Date getRegDeadline() {
		return regDeadline;
	}

	public void setRegDeadline(Date regDeadline) {
		this.regDeadline = regDeadline;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	public Integer getCourseSortId() {
		return courseSortId;
	}

	public void setCourseSortId(Integer courseSortId) {
		this.courseSortId = courseSortId;
	}

	public Integer getRemainingPlaces() {
		return remainingPlaces;
	}

	public void setRemainingPlaces(Integer remainingPlaces) {
		this.remainingPlaces = remainingPlaces;
	}

	public Integer getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(Integer coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getCoursePictureUrl() {
		return coursePictureUrl;
	}

	public void setCoursePictureUrl(String coursePictureUrl) {
		this.coursePictureUrl = coursePictureUrl;
	}

	public Integer getCourseVideoId() {
		return courseVideoId;
	}

	public void setCourseVideoId(Integer courseVideoId) {
		this.courseVideoId = courseVideoId;
	}

	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	
}
