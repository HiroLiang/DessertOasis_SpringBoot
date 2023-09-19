package com.dessertoasis.demo.model.course;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dessertoasis.demo.model.category.Category;
import com.dessertoasis.demo.model.order.CourseOrderItem;
import com.dessertoasis.demo.model.recipe.Recipes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	//教師ID FK(連結教師ID)
	@ManyToOne
	@JoinColumn(name = "teacherId")
	@JsonIgnoreProperties({"courseList","pictures","expertiseList","member"})
	private Teacher teacher;
	
	//食譜ID FK(連接食譜ID)
	@ManyToOne 
	@JsonIgnoreProperties({"recipeCategories","recipeSteps","ingredientList","course","recipeAuthor"})
	@JoinColumn(name = "recipesId")
	private Recipes recipes;
	
	//分類ID FK(連接分類ID)
	@ManyToOne 
	@JsonIgnoreProperties({"parent","children","recipeCategories","products","courses"})
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
	
	//課程對象
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
	
	//開課日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")  // 在資料進 Java 環境時，做格式化
	@Temporal(TemporalType.DATE)
	@Column(name="courseDate")
	private Date courseDate;
	
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
	
	@JsonIgnoreProperties({"course"})
	@OneToMany(mappedBy="course",cascade = CascadeType.ALL)
	private List<CourseCtag> courseList;
	
	@OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
	@JsonIgnoreProperties("course")
	private List<CoursePicture> coursePictureList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL)
	private List<CourseOrderItem> courseOrderItems;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Recipes getRecipes() {
		return recipes;
	}

	public void setRecipes(Recipes recipes) {
		this.recipes = recipes;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseIntroduction() {
		return courseIntroduction;
	}

	public void setCourseIntroduction(String courseIntroduction) {
		this.courseIntroduction = courseIntroduction;
	}

	public String getCourseFeature() {
		return courseFeature;
	}

	public void setCourseFeature(String courseFeature) {
		this.courseFeature = courseFeature;
	}

	public String getCourseDestination() {
		return courseDestination;
	}

	public void setCourseDestination(String courseDestination) {
		this.courseDestination = courseDestination;
	}

	public String getServiceTarget() {
		return serviceTarget;
	}

	public void setServiceTarget(String serviceTarget) {
		this.serviceTarget = serviceTarget;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public String getCoursePlace() {
		return coursePlace;
	}

	public void setCoursePlace(String coursePlace) {
		this.coursePlace = coursePlace;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public Integer getRemainPlaces() {
		return remainPlaces;
	}

	public void setRemainPlaces(Integer remainPlaces) {
		this.remainPlaces = remainPlaces;
	}

	public Integer getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(Integer coursePrice) {
		this.coursePrice = coursePrice;
	}

	public List<CourseCtag> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseCtag> courseList) {
		this.courseList = courseList;
	}

	public List<CoursePicture> getCoursePictureList() {
		return coursePictureList;
	}

	public void setCoursePictureList(List<CoursePicture> coursePictureList) {
		this.coursePictureList = coursePictureList;
	}

	public List<CourseOrderItem> getCourseOrderItems() {
		return courseOrderItems;
	}

	public void setCourseOrderItems(List<CourseOrderItem> courseOrderItems) {
		this.courseOrderItems = courseOrderItems;
	}

	public Course() {
	}

	public Course(Integer id, Teacher teacher, Recipes recipes, Category category, String courseName,
			String courseIntroduction, String courseFeature, String courseDestination, String serviceTarget,
			Date closeDate, Date updateDate,Date courseDate, String coursePlace, String courseStatus, Integer remainPlaces,
			Integer coursePrice,List<CoursePicture> coursePictureList) {
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
		this.courseDate = courseDate;
		this.coursePlace = coursePlace;
		this.courseStatus = courseStatus;
		this.remainPlaces = remainPlaces;
		this.coursePrice = coursePrice;
		this.coursePictureList = coursePictureList;
	}

	public Course(Integer id, Teacher teacher, Recipes recipes, Category category, String courseName,
			String courseIntroduction, String courseFeature, String courseDestination, String serviceTarget,
			Date closeDate, Date updateDate, Date courseDate, String coursePlace, String courseStatus,
			Integer remainPlaces, Integer coursePrice, List<CourseCtag> courseList,
			List<CoursePicture> coursePictureList, List<CourseOrderItem> courseOrderItems) {
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
		this.courseDate = courseDate;
		this.coursePlace = coursePlace;
		this.courseStatus = courseStatus;
		this.remainPlaces = remainPlaces;
		this.coursePrice = coursePrice;
		this.courseList = courseList;
		this.coursePictureList = coursePictureList;
		this.courseOrderItems = courseOrderItems;
	}

}
